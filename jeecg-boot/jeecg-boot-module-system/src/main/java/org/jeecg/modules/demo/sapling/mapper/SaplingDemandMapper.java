package org.jeecg.modules.demo.sapling.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.demo.sapling.entity.SaplingDemand;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 需求
 * @Author: jeecg-boot
 * @Date:   2020-07-23
 * @Version: V1.0
 */
public interface SaplingDemandMapper extends BaseMapper<SaplingDemand> {

    public SaplingDemand showlist();

}
