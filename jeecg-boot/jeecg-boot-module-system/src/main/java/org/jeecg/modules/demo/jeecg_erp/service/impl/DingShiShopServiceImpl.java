package org.jeecg.modules.demo.jeecg_erp.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.demo.jeecg_erp.entity.ErpShop;
import org.jeecg.modules.demo.jeecg_erp.mapper.ErpShopMapper;
import org.jeecg.modules.demo.jeecg_erp.service.DingShiShopService;
import org.jeecg.modules.demo.jeecg_erp.service.IErpShopService;
import org.springframework.stereotype.Service;

/**
 * @Description: 商品
 * @Author: jeecg-boot
 * @Date:   2020-07-12
 * @Version: V1.0
 */
@DS("multi-datasource1")
@Service
public class DingShiShopServiceImpl extends ServiceImpl<ErpShopMapper, ErpShop> implements DingShiShopService {

}
