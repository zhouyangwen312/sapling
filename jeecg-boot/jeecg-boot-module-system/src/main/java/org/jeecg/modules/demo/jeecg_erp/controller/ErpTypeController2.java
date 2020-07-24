package org.jeecg.modules.demo.jeecg_erp.controller;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.demo.jeecg_erp.entity.ErpType;
import org.jeecg.modules.demo.jeecg_erp.service.IErpTypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
* @Description: 分类
* @Author: jeecg-boot
* @Date:   2020-07-12
* @Version: V1.0
*/
@Api(tags="分类添加删除修改")
@RestController
@RequestMapping("/jeecg_erp/erpType")
@Slf4j
public class ErpTypeController2 extends JeecgController<ErpType, IErpTypeService> {
   @Autowired
   private IErpTypeService erpTypeService;

   /**
    * 分页列表查询
    *
    * @param erpType
    * @param pageNo
    * @param pageSize
    * @param req
    * @return
    */
   @DS("multi-datasource1")
   @AutoLog(value = "分类-分查页列表询")
   @ApiOperation(value="分类-分页列表查询", notes="分类-分页列表查询")
   @GetMapping(value = "/list")
   public Result<?> queryPageList(ErpType erpType,
                                  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                  HttpServletRequest req) {
       QueryWrapper<ErpType> queryWrapper = QueryGenerator.initQueryWrapper(erpType, req.getParameterMap());
       Page<ErpType> page = new Page<ErpType>(pageNo, pageSize);
       IPage<ErpType> pageList = erpTypeService.page(page, queryWrapper);
       return Result.ok(pageList);
   }

   /**
    *   添加
    *
    * @param erpType
    * @return
    */

   @AutoLog(value = "分类-添加")
   @ApiOperation(value="分类-添加", notes="分类-添加")
   @PostMapping(value = "/add")
   public Result<?> add(@RequestBody ErpType erpType) {
       erpTypeService.save(erpType);
       return Result.ok("添加成功！");
   }

   /**
    *  编辑
    *
    * @param erpType
    * @return
    */
   @AutoLog(value = "分类-编辑")
   @ApiOperation(value="分类-编辑", notes="分类-编辑")
   @PutMapping(value = "/edit")
   public Result<?> edit(@RequestBody ErpType erpType) {
       erpTypeService.updateById(erpType);
       return Result.ok("编辑成功!");
   }

   /**
    *   通过id删除
    *
    * @param id
    * @return
    */
   @AutoLog(value = "分类-通过id删除")
   @ApiOperation(value="分类-通过id删除", notes="分类-通过id删除")
   @DeleteMapping(value = "/delete")
   public Result<?> delete(@RequestParam(name="id",required=true) String id) {
       erpTypeService.removeById(id);
       return Result.ok("删除成功!");
   }

   /**
    *  批量删除
    *
    * @param ids
    * @return
    */
	@AutoLog(value = "分类-批量删除")
   @ApiOperation(value="分类-批量删除", notes="分类-批量删除")
   @DeleteMapping(value = "/deleteBatch")
   public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
       this.erpTypeService.removeByIds(Arrays.asList(ids.split(",")));
       return Result.ok("批量删除成功!");
   }

   /**
    * 通过id查询
    *
    * @param id
    * @return
    */
   @DS("multi-datasource1")
   @AutoLog(value = "分类-通过id查询")
   @ApiOperation(value="分类-通过id查询", notes="分类-通过id查询")
   @GetMapping(value = "/queryById")
   public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
       ErpType erpType = erpTypeService.getById(id);
       if(erpType==null) {
           return Result.error("未找到对应数据");
       }
       return Result.ok(erpType);
   }

   /**
   * 导出excel
   *
   * @param request
   * @param erpType
   */
  @RequestMapping(value = "/exportXls")
   public ModelAndView exportXls(HttpServletRequest request, ErpType erpType) {
       return super.exportXls(request, erpType, ErpType.class, "分类");
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
       return super.importExcel(request, response, ErpType.class);
   }

}
