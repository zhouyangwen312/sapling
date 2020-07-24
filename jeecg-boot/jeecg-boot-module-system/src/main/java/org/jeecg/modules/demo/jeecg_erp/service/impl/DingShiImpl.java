package org.jeecg.modules.demo.jeecg_erp.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.demo.jeecg_erp.entity.ErpType;
import org.jeecg.modules.demo.jeecg_erp.mapper.ErpTypeMapper;
import org.jeecg.modules.demo.jeecg_erp.service.DingShi;

import org.springframework.stereotype.Service;

/**
 * @Description: 定时
 * @Author: jeecg-boot
 * @Date:   2020-07-12
 * @Version: V1.0
 */
@DS("multi-datasource1")
@Service
public class DingShiImpl extends ServiceImpl<ErpTypeMapper, ErpType> implements DingShi {




}
