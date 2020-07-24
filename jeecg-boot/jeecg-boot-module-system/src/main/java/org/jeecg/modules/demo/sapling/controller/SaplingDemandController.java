package org.jeecg.modules.demo.sapling.controller;

import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.vo.LoginUser;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.sapling.entity.DemandSpecification;
import org.jeecg.modules.demo.sapling.entity.SaplingDemand;
import org.jeecg.modules.demo.sapling.vo.SaplingDemandPage;
import org.jeecg.modules.demo.sapling.service.ISaplingDemandService;
import org.jeecg.modules.demo.sapling.service.IDemandSpecificationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 需求
 * @Author: jeecg-boot
 * @Date:   2020-07-23
 * @Version: V1.0
 */
@Api(tags="需求")
@RestController
@RequestMapping("/sapling/saplingDemand")
@Slf4j
public class SaplingDemandController {
	@Autowired
	private ISaplingDemandService saplingDemandService;
	@Autowired
	private IDemandSpecificationService demandSpecificationService;
	
	/**
	 * 分页列表查询
	 *
	 * @param saplingDemand
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "需求-分页列表查询")
	@ApiOperation(value="需求-分页列表查询", notes="需求-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(SaplingDemand saplingDemand,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<SaplingDemand> queryWrapper = QueryGenerator.initQueryWrapper(saplingDemand, req.getParameterMap());
		Page<SaplingDemand> page = new Page<SaplingDemand>(pageNo, pageSize);
		IPage<SaplingDemand> pageList = saplingDemandService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param saplingDemandPage
	 * @return
	 */
	@AutoLog(value = "需求-添加")
	@ApiOperation(value="需求-添加", notes="需求-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody SaplingDemandPage saplingDemandPage) {
		SaplingDemand saplingDemand = new SaplingDemand();
		BeanUtils.copyProperties(saplingDemandPage, saplingDemand);
		saplingDemandService.saveMain(saplingDemand, saplingDemandPage.getDemandSpecificationList());
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param saplingDemandPage
	 * @return
	 */
	@AutoLog(value = "需求-编辑")
	@ApiOperation(value="需求-编辑", notes="需求-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody SaplingDemandPage saplingDemandPage) {
		SaplingDemand saplingDemand = new SaplingDemand();
		BeanUtils.copyProperties(saplingDemandPage, saplingDemand);
		SaplingDemand saplingDemandEntity = saplingDemandService.getById(saplingDemand.getId());
		if(saplingDemandEntity==null) {
			return Result.error("未找到对应数据");
		}
		saplingDemandService.updateMain(saplingDemand, saplingDemandPage.getDemandSpecificationList());
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "需求-通过id删除")
	@ApiOperation(value="需求-通过id删除", notes="需求-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		saplingDemandService.delMain(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "需求-批量删除")
	@ApiOperation(value="需求-批量删除", notes="需求-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.saplingDemandService.delBatchMain(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "需求-通过id查询")
	@ApiOperation(value="需求-通过id查询", notes="需求-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		SaplingDemand saplingDemand = saplingDemandService.getById(id);
		if(saplingDemand==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(saplingDemand);

	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "树苗规格通过主表ID查询")
	@ApiOperation(value="树苗规格主表ID查询", notes="树苗规格-通主表ID查询")
	@GetMapping(value = "/queryDemandSpecificationByMainId")
	public Result<?> queryDemandSpecificationListByMainId(@RequestParam(name="id",required=true) String id) {
		List<DemandSpecification> demandSpecificationList = demandSpecificationService.selectByMainId(id);
		return Result.ok(demandSpecificationList);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param saplingDemand
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, SaplingDemand saplingDemand) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<SaplingDemand> queryWrapper = QueryGenerator.initQueryWrapper(saplingDemand, request.getParameterMap());
      LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

      //Step.2 获取导出数据
      List<SaplingDemand> queryList = saplingDemandService.list(queryWrapper);
      // 过滤选中数据
      String selections = request.getParameter("selections");
      List<SaplingDemand> saplingDemandList = new ArrayList<SaplingDemand>();
      if(oConvertUtils.isEmpty(selections)) {
          saplingDemandList = queryList;
      }else {
          List<String> selectionList = Arrays.asList(selections.split(","));
          saplingDemandList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
      }

      // Step.3 组装pageList
      List<SaplingDemandPage> pageList = new ArrayList<SaplingDemandPage>();
      for (SaplingDemand main : saplingDemandList) {
          SaplingDemandPage vo = new SaplingDemandPage();
          BeanUtils.copyProperties(main, vo);
          List<DemandSpecification> demandSpecificationList = demandSpecificationService.selectByMainId(main.getId());
          vo.setDemandSpecificationList(demandSpecificationList);
          pageList.add(vo);
      }

      // Step.4 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      mv.addObject(NormalExcelConstants.FILE_NAME, "需求列表");
      mv.addObject(NormalExcelConstants.CLASS, SaplingDemandPage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("需求数据", "导出人:"+sysUser.getRealname(), "需求"));
      mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
      return mv;
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
      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
      Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
      for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
          MultipartFile file = entity.getValue();// 获取上传文件对象
          ImportParams params = new ImportParams();
          params.setTitleRows(2);
          params.setHeadRows(1);
          params.setNeedSave(true);
          try {
              List<SaplingDemandPage> list = ExcelImportUtil.importExcel(file.getInputStream(), SaplingDemandPage.class, params);
              for (SaplingDemandPage page : list) {
                  SaplingDemand po = new SaplingDemand();
                  BeanUtils.copyProperties(page, po);
                  saplingDemandService.saveMain(po, page.getDemandSpecificationList());
              }
              return Result.ok("文件导入成功！数据行数:" + list.size());
          } catch (Exception e) {
              log.error(e.getMessage(),e);
              return Result.error("文件导入失败:"+e.getMessage());
          } finally {
              try {
                  file.getInputStream().close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }
      return Result.ok("文件导入失败！");
    }

     @AutoLog(value = "需求模糊查询")
     @ApiOperation(value="需求模糊查询", notes="需求模糊查询")
     @GetMapping(value = "/linkDemand")
    public List<SaplingDemand> linkDemand(String name){
        QueryWrapper<SaplingDemand> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("demand_name","demand_price","demand_amount").like("demand_name","紫");
        List<SaplingDemand> list = saplingDemandService.list(queryWrapper);
        return list;
    }


     @AutoLog(value = "测试")
     @ApiOperation(value="测试", notes="测试")
     @GetMapping(value = "/showlist")
    public SaplingDemand showlist(){
        return saplingDemandService.showlist();
    }
}
