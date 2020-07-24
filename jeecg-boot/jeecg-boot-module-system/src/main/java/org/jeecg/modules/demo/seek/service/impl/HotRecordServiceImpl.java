package org.jeecg.modules.demo.seek.service.impl;

import org.jeecg.modules.demo.seek.entity.HotRecord;
import org.jeecg.modules.demo.seek.mapper.HotRecordMapper;
import org.jeecg.modules.demo.seek.service.IHotRecordService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 热门记录
 * @Author: jeecg-boot
 * @Date:   2020-07-23
 * @Version: V1.0
 */
@Service
public class HotRecordServiceImpl extends ServiceImpl<HotRecordMapper, HotRecord> implements IHotRecordService {

}
