package org.jeecg.modules.demo.sapling.service.impl;

import org.jeecg.modules.demo.sapling.entity.SaplingDemand;
import org.jeecg.modules.demo.sapling.entity.DemandSpecification;
import org.jeecg.modules.demo.sapling.mapper.DemandSpecificationMapper;
import org.jeecg.modules.demo.sapling.mapper.SaplingDemandMapper;
import org.jeecg.modules.demo.sapling.service.ISaplingDemandService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 需求
 * @Author: jeecg-boot
 * @Date:   2020-07-23
 * @Version: V1.0
 */
@Service
public class SaplingDemandServiceImpl extends ServiceImpl<SaplingDemandMapper, SaplingDemand> implements ISaplingDemandService {

	@Autowired
	private SaplingDemandMapper saplingDemandMapper;
	@Autowired
	private DemandSpecificationMapper demandSpecificationMapper;
	
	@Override
	@Transactional
	public void saveMain(SaplingDemand saplingDemand, List<DemandSpecification> demandSpecificationList) {
		saplingDemandMapper.insert(saplingDemand);
		if(demandSpecificationList!=null && demandSpecificationList.size()>0) {
			for(DemandSpecification entity:demandSpecificationList) {
				//外键设置
				entity.setSpecificationId(saplingDemand.getId());
				demandSpecificationMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void updateMain(SaplingDemand saplingDemand,List<DemandSpecification> demandSpecificationList) {
		saplingDemandMapper.updateById(saplingDemand);
		
		//1.先删除子表数据
		demandSpecificationMapper.deleteByMainId(saplingDemand.getId());
		
		//2.子表数据重新插入
		if(demandSpecificationList!=null && demandSpecificationList.size()>0) {
			for(DemandSpecification entity:demandSpecificationList) {
				//外键设置
				entity.setSpecificationId(saplingDemand.getId());
				demandSpecificationMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void delMain(String id) {
		demandSpecificationMapper.deleteByMainId(id);
		saplingDemandMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			demandSpecificationMapper.deleteByMainId(id.toString());
			saplingDemandMapper.deleteById(id);
		}
	}

    @Override
   // @Transactional
	public  SaplingDemand showlist(){
	    return saplingDemandMapper.showlist();
    }
}
