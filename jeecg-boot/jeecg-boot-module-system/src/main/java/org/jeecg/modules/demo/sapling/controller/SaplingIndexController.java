package org.jeecg.modules.demo.sapling.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.sapling.entity.SaplingIndex;
import org.jeecg.modules.demo.sapling.service.ISaplingIndexService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

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
 * @Description: 树苗
 * @Author: jeecg-boot
 * @Date:   2020-07-23
 * @Version: V1.0
 */
@Api(tags="树苗")
@RestController
@RequestMapping("/sapling/saplingIndex")
@Slf4j
public class SaplingIndexController extends JeecgController<SaplingIndex, ISaplingIndexService> {
	@Autowired
	private ISaplingIndexService saplingIndexService;
	
	/**
	 * 分页列表查询
	 *
	 * @param saplingIndex
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "树苗-分页列表查询")
	@ApiOperation(value="树苗-分页列表查询", notes="树苗-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(SaplingIndex saplingIndex,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<SaplingIndex> queryWrapper = QueryGenerator.initQueryWrapper(saplingIndex, req.getParameterMap());

		Page<SaplingIndex> page = new Page<SaplingIndex>(pageNo, pageSize);
		IPage<SaplingIndex> pageList = saplingIndexService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param saplingIndex
	 * @return
	 */
	@AutoLog(value = "树苗-添加")
	@ApiOperation(value="树苗-添加", notes="树苗-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody SaplingIndex saplingIndex) {
		saplingIndexService.save(saplingIndex);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param saplingIndex
	 * @return
	 */
	@AutoLog(value = "树苗-编辑")
	@ApiOperation(value="树苗-编辑", notes="树苗-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody SaplingIndex saplingIndex) {
		saplingIndexService.updateById(saplingIndex);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "树苗-通过id删除")
	@ApiOperation(value="树苗-通过id删除", notes="树苗-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		saplingIndexService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "树苗-批量删除")
	@ApiOperation(value="树苗-批量删除", notes="树苗-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.saplingIndexService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "树苗-通过id查询")
	@ApiOperation(value="树苗-通过id查询", notes="树苗-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		SaplingIndex saplingIndex = saplingIndexService.getById(id);
		if(saplingIndex==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(saplingIndex);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param saplingIndex
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, SaplingIndex saplingIndex) {
        return super.exportXls(request, saplingIndex, SaplingIndex.class, "树苗");
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
        return super.importExcel(request, response, SaplingIndex.class);
    }

     @AutoLog(value = "树苗-分类查询")
     @ApiOperation(value="树苗-分类查询", notes="树苗-分类查询")
     @DeleteMapping(value = "/seleMap")
    public List<SaplingIndex> seleMap(){
        QueryWrapper<SaplingIndex> queryWrapper = new QueryWrapper<>();
        //queryWrapper.like("sapling_type","落叶乔木");
         queryWrapper.eq("sapling_type","落叶乔木");
       // queryWrapper.lambda().eq(SaplingIndex::getSaplingType,"落叶乔木");
        //queryWrapper.lambda().eq(SaplingIndex::getSaplingType,"落叶乔木");
        List<SaplingIndex> list = saplingIndexService.list(queryWrapper);
        return list;
    }
}
