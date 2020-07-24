package org.jeecg.modules.demo.sapling.service;

import org.jeecg.modules.demo.sapling.entity.DemandSpecification;
import org.jeecg.modules.demo.sapling.entity.SaplingDemand;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 需求
 * @Author: jeecg-boot
 * @Date:   2020-07-23
 * @Version: V1.0
 */
public interface ISaplingDemandService extends IService<SaplingDemand> {

	/**
	 * 添加一对多
	 * 
	 */
	public void saveMain(SaplingDemand saplingDemand,List<DemandSpecification> demandSpecificationList) ;
	
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(SaplingDemand saplingDemand,List<DemandSpecification> demandSpecificationList);
	
	/**
	 * 删除一对多
	 */
	public void delMain (String id);
	
	/**
	 * 批量删除一对多
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);

    /**
     * 测试
     * @return
     */
	public SaplingDemand showlist();
	
}
