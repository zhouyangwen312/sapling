package org.jeecg.modules.quartz.job;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.poi.ss.formula.functions.T;
import org.jeecg.common.util.DateUtils;


import org.jeecg.modules.demo.jeecg_erp.entity.ErpShop;
import org.jeecg.modules.demo.jeecg_erp.entity.ErpType;

import org.jeecg.modules.demo.jeecg_erp.service.DingShi;

import org.jeecg.modules.demo.jeecg_erp.service.DingShiShopService;
import org.jeecg.modules.demo.jeecg_erp.service.IErpShopService;
import org.jeecg.modules.demo.jeecg_erp.service.IErpTypeService;
import org.omg.PortableInterceptor.INACTIVE;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;


/**
 * 示例不带参定时任务
 *
 * @Author Scott
 */
@Slf4j
public class SampleJob implements Job {
    @Autowired
    private DingShi dingShi;
    @Autowired
    private IErpTypeService iErpShopService;
    @Autowired
    private DingShiShopService dingShiShopService;
    @Autowired
    private IErpShopService erpShopService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        erpTypeDing();
        erpShopDing();
        log.info(String.format(" Jeecg-Boot 普通定时任务 SampleJob !  时间:" + DateUtils.getTimestamp()));
    }

    /**
     * 分类表定时任务
     */
    public void erpTypeDing() {
        List<ErpType> listType = dingShi.list();
        List<String> listId = new ArrayList<>();
        for (ErpType e : listType) {
            listId.add(e.getId());
        }
        dingShi.removeByIds(listId);

        List<ErpType> list = iErpShopService.list();
        dingShi.saveBatch(list);
    }

    /**
     * 商品表定时任务
     */
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
