package org.jeecg.modules.demo.sapling.service.impl;

import org.jeecg.modules.demo.sapling.entity.DemandSpecification;
import org.jeecg.modules.demo.sapling.mapper.DemandSpecificationMapper;
import org.jeecg.modules.demo.sapling.service.IDemandSpecificationService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 树苗规格
 * @Author: jeecg-boot
 * @Date:   2020-07-23
 * @Version: V1.0
 */
@Service
public class DemandSpecificationServiceImpl extends ServiceImpl<DemandSpecificationMapper, DemandSpecification> implements IDemandSpecificationService {
	
	@Autowired
	private DemandSpecificationMapper demandSpecificationMapper;
	
	@Override
	public List<DemandSpecification> selectByMainId(String mainId) {
		return demandSpecificationMapper.selectByMainId(mainId);
	}
}
