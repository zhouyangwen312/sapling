package org.jeecg.modules.demo.jeecg_erp.controller;

import java.util.*;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.jeecg_erp.entity.ErpShop;
import org.jeecg.modules.demo.jeecg_erp.service.DingShiShopService;
import org.jeecg.modules.demo.jeecg_erp.service.IErpShopService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.quartz.job.SampleJob;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

/**
 * @Description: 商品
 * @Author: jeecg-boot
 * @Date: 2020-07-12
 * @Version: V1.0
 */
@Api(tags = "商品")
@RestController
@RequestMapping("/jeecg_erp/erpShop")
@Slf4j
public class ErpShopController extends JeecgController<ErpShop, IErpShopService> {
    @Autowired
    private IErpShopService erpShopService;
    @Autowired
    private DingShiShopService dingShiShopService;


    /**
     * 分页列表查询
     *
     * @param erpShop
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @DS("multi-datasource1")
    @AutoLog(value = "商品-分页列表查询")
    @ApiOperation(value = "商品-分页列表查询", notes = "商品-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(ErpShop erpShop,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        QueryWrapper<ErpShop> queryWrapper = QueryGenerator.initQueryWrapper(erpShop, req.getParameterMap());

        Page<ErpShop> page = new Page<ErpShop>(pageNo, pageSize);
        List<ErpShop> list = erpShopService.list();

        IPage<ErpShop> pageList = erpShopService.page(page, queryWrapper);
        return Result.ok(pageList);
    }

    /**
     * 添加
     *
     * @param erpShop
     * @return
     */
    @AutoLog(value = "商品-添加")
    @ApiOperation(value = "商品-添加", notes = "商品-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody ErpShop erpShop) {
        if(erpShop.getShopName().trim().length()==0){
            return Result.error("添加失败,商品为空" );
        }else {
            List<ErpShop> listAll = erpShopService.list();
            int conut = 0;//重复标识 大于0重复
            for (int k = 0; k < listAll.size(); k++) {
                if (listAll.get(k).getShopName().equals(erpShop.getShopName())) {
                    conut++;
                }
            }
            if (conut > 0) {
                return Result.error("添加失败,商品重复");
            } else {
                erpShopService.save(erpShop);
                erpShopDing();
                return Result.ok("添加成功！");
            }
        }
    }

    /**
     * 编辑
     *
     * @param erpShop
     * @return
     */
    @AutoLog(value = "商品-编辑")
    @ApiOperation(value = "商品-编辑", notes = "商品-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody ErpShop erpShop) {
        boolean i = erpShopService.updateById(erpShop);
        if (i) {
            erpShopDing();
            return Result.ok("编辑成功!");
        }
        return Result.error("编辑失败");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "商品-通过id删除")
    @ApiOperation(value = "商品-通过id删除", notes = "商品-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        boolean i = erpShopService.removeById(id);
        if (i) {
            erpShopDing();
            return Result.ok("删除成功!");
        }
        return Result.error("删除失败");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "商品-批量删除")
    @ApiOperation(value = "商品-批量删除", notes = "商品-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        boolean i = this.erpShopService.removeByIds(Arrays.asList(ids.split(",")));
        if (i) {
            erpShopDing();
            return Result.ok("批量删除成功!");
        }
        return Result.error("批量删除失败");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @DS("multi-datasource1")
    @AutoLog(value = "商品-通过id查询")
    @ApiOperation(value = "商品-通过id查询", notes = "商品-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        ErpShop erpShop = erpShopService.getById(id);
        if (erpShop == null) {
            return Result.error("未找到对应数据");
        }
        return Result.ok(erpShop);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param erpShop
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ErpShop erpShop) {
        return super.exportXls(request, erpShop, ErpShop.class, "商品");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, ErpShop.class);
    }


    public void erpShopDing() {
        List<ErpShop> listShop = dingShiShopService.list();
        List<String> listId_shop = new ArrayList<>();
        for (ErpShop e : listShop) {
            listId_shop.add(e.getId());
        }
        dingShiShopService.removeByIds(listId_shop);
        List<ErpShop> list2 = erpShopService.list();
        dingShiShopService.saveBatch(list2);
    }
}
