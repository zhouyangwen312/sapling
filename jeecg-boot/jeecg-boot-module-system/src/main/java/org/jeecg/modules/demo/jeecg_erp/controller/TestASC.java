package org.jeecg.modules.demo.jeecg_erp.controller;

import cn.hutool.core.comparator.PinyinComparator;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pinyin4j.PinyinHelper;
import org.apache.commons.lang.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.demo.jeecg_erp.config.PinyinUtils;
import org.jeecg.modules.demo.jeecg_erp.entity.ErpShop;
import org.jeecg.modules.demo.jeecg_erp.service.DingShiShopService;
import org.jeecg.modules.demo.jeecg_erp.service.IErpShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.service.ApiListing;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.text.Collator;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * @Description: 商品
 * @Author: jeecg-boot
 * @Date: 2020-07-12
 * @Version: V1.0
 */
@Api(tags = "商品排序")
@RestController
@RequestMapping("/jeecg_erp/erpShopASC")
@Slf4j
public class TestASC extends JeecgController<ErpShop, IErpShopService> {
    @Autowired
    private IErpShopService erpShopService;
    @Autowired
    private DingShiShopService dingShiShopService;


    @DS("multi-datasource1")
    @AutoLog(value = "商品-按数量排序")
    @ApiOperation(value = "商品-按数量排序", notes = "商品-按数量排序")
    @GetMapping(value = "/list")
    public Result<?> seleAsc() {
        List<ErpShop> listErpshop = erpShopService.list();
        for (int i = 0; i < listErpshop.size() - 1; i++) {
            for (int j = 0; j < listErpshop.size() - 1 - i; j++) {
                if (listErpshop.get(j).getShopAmount() > listErpshop.get(j + 1).getShopAmount()) {
                    ErpShop erpShop = listErpshop.get(j);
                    listErpshop.set(j, listErpshop.get(j + 1));
                    listErpshop.set(j + 1, erpShop);
                }
            }
        }

        return Result.ok(listErpshop);

    }


    @DS("multi-datasource1")
    @AutoLog(value = "商品-按数量排序")
    @ApiOperation(value = "商品-按时间排序", notes = "商品-按时间排序")
    @GetMapping(value = "/list2")
    public Result<?> seleDate() {

        List<ErpShop> list = erpShopService.list();
        Collections.sort(list, new Comparator<ErpShop>() {
            @Override
            public int compare(ErpShop o1, ErpShop o2) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Date date1 = o1.getCreateTime();
                    Date date2 = o2.getCreateTime();
                    if (date1.getTime() > date2.getTime()) {
                        return 1;
                    } else if (date1.getTime() < date2.getTime()) {
                        return -1;
                    } else {
                        return 0;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });

        return Result.ok(list);
    }


    @DS("multi-datasource1")
    @AutoLog(value = "商品-按名称排序")
    @ApiOperation(value = "商品-按名称排序", notes = "商品-按名称排序")
    @GetMapping(value = "/list3")
    public Result<?> seleName() {
        List<ErpShop> list = erpShopService.list();
        List<ErpShop> chinese = new ArrayList<>();
        List<ErpShop> nochinese = new ArrayList<>();
        for (ErpShop e : list) {
            if (StringUtils.isNotEmpty(e.getShopName()) && String.valueOf(e.getShopName().charAt(0)).matches("[\u4e00-\u9fa5]")) {
                chinese.add(e);
            } else {
                nochinese.add(e);
            }
        }
        PinyinUtils pinyinUtils = new PinyinUtils();
        Comparator<Object> com = Collator.getInstance(Locale.CHINA);
        nochinese.sort((o1, o2) -> ((Collator) com).compare(o1.getShopName(), o2.getShopName()));
        chinese.sort((o1, o2) -> ((Collator) com).compare(pinyinUtils.getStringPinYin(o1.getShopName()), pinyinUtils.getStringPinYin(o2.getShopName())));
        chinese.addAll(nochinese);
        return Result.ok(chinese);
    }


}
