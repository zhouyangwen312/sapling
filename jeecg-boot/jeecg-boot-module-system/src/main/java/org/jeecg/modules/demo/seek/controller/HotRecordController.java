package org.jeecg.modules.demo.seek.controller;

import java.util.ArrayList;
import java.util.Arrays;
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
import org.jeecg.modules.demo.seek.entity.HotRecord;
import org.jeecg.modules.demo.seek.service.IHotRecordService;

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
import org.springframework.data.querydsl.QuerydslUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

/**
 * @Description: 热门记录
 * @Author: jeecg-boot
 * @Date: 2020-07-23
 * @Version: V1.0
 */
@Api(tags = "热门记录")
@RestController
@RequestMapping("/seek/hotRecord")
@Slf4j
public class HotRecordController extends JeecgController<HotRecord, IHotRecordService> {
    @Autowired
    private IHotRecordService hotRecordService;

    /**
     * 分页列表查询
     *
     * @param hotRecord
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "热门记录-分页列表查询")
    @ApiOperation(value = "热门记录-分页列表查询", notes = "热门记录-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(HotRecord hotRecord,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        QueryWrapper<HotRecord> queryWrapper = QueryGenerator.initQueryWrapper(hotRecord, req.getParameterMap());
        Page<HotRecord> page = new Page<HotRecord>(pageNo, pageSize);
        IPage<HotRecord> pageList = hotRecordService.page(page, queryWrapper);
        return Result.ok(pageList);
    }

    /**
     * 添加
     *
     * @param hotRecord
     * @return
     */
    @AutoLog(value = "热门记录-添加")
    @ApiOperation(value = "热门记录-添加", notes = "热门记录-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody HotRecord hotRecord) {
        hotRecordService.save(hotRecord);
        return Result.ok("添加成功！");
    }

    /**
     * 编辑
     *
     * @param hotRecord
     * @return
     */
    @AutoLog(value = "热门记录-编辑")
    @ApiOperation(value = "热门记录-编辑", notes = "热门记录-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody HotRecord hotRecord) {
        hotRecordService.updateById(hotRecord);
        return Result.ok("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "热门记录-通过id删除")
    @ApiOperation(value = "热门记录-通过id删除", notes = "热门记录-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        hotRecordService.removeById(id);
        return Result.ok("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "热门记录-批量删除")
    @ApiOperation(value = "热门记录-批量删除", notes = "热门记录-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.hotRecordService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.ok("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "热门记录-通过id查询")
    @ApiOperation(value = "热门记录-通过id查询", notes = "热门记录-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        HotRecord hotRecord = hotRecordService.getById(id);
        if (hotRecord == null) {
            return Result.error("未找到对应数据");
        }
        return Result.ok(hotRecord);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param hotRecord
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, HotRecord hotRecord) {
        return super.exportXls(request, hotRecord, HotRecord.class, "热门记录");
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
        return super.importExcel(request, response, HotRecord.class);
    }

    @AutoLog(value = "记录查询并添加")
    @ApiOperation(value = "热门记录-记录查询并添加", notes = "热门记录-记录查询并添加")
    @GetMapping(value = "/querylist")
    public Result<?> showHotRecorf(String name) {
        QueryWrapper<HotRecord> queryWrapper = null;
        if (name == null || name.trim().equals("")) {
            queryWrapper = new QueryWrapper<>();
            queryWrapper.orderByDesc("record_sum").last("limit 3");
            return Result.ok(hotRecordService.list(queryWrapper));
        }
        List<HotRecord> list = new ArrayList<>();
        queryWrapper = new QueryWrapper<>();
        HotRecord hotRecord = new HotRecord();
        queryWrapper.eq("record_name", name);
        HotRecord hot = hotRecordService.getOne(queryWrapper);
        if (hot == null) {
            hotRecord.setRecordName(name);
            hotRecord.setRecordSum(1);
            hotRecordService.save(hotRecord);
            queryWrapper = new QueryWrapper<>();
            queryWrapper.orderByDesc("record_sum").last("limit 3");
            return Result.ok(hotRecordService.list(queryWrapper));
        } else {
            queryWrapper = new QueryWrapper<>();
            queryWrapper.orderByDesc("record_sum").last("limit 3");
            list = hotRecordService.list(queryWrapper);
            hotRecord.setRecordSum(hot.getRecordSum() + 1);
            hotRecordService.update(hotRecord, queryWrapper);
            return Result.ok(list);

        }

    }
}

