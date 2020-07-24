package org.jeecg.modules.demo.sapling.mapper;

import java.util.List;
import org.jeecg.modules.demo.sapling.entity.DemandSpecification;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 树苗规格
 * @Author: jeecg-boot
 * @Date:   2020-07-23
 * @Version: V1.0
 */
public interface DemandSpecificationMapper extends BaseMapper<DemandSpecification> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<DemandSpecification> selectByMainId(@Param("mainId") String mainId);
}
