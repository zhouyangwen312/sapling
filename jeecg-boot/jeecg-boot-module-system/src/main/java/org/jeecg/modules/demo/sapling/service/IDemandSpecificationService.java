package org.jeecg.modules.demo.sapling.service;

import org.jeecg.modules.demo.sapling.entity.DemandSpecification;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 树苗规格
 * @Author: jeecg-boot
 * @Date:   2020-07-23
 * @Version: V1.0
 */
public interface IDemandSpecificationService extends IService<DemandSpecification> {

	public List<DemandSpecification> selectByMainId(String mainId);
}
