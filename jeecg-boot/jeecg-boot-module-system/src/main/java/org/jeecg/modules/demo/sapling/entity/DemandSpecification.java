package org.jeecg.modules.demo.sapling.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: 树苗规格
 * @Author: jeecg-boot
 * @Date:   2020-07-23
 * @Version: V1.0
 */
@ApiModel(value="sapling_demand对象", description="需求")
@Data
@TableName("demand_specification")
public class DemandSpecification implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
	@ApiModelProperty(value = "主键")
	private java.lang.String id;
	/**创建人*/
	@ApiModelProperty(value = "创建人")
	private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "创建日期")
	private java.util.Date createTime;
	/**更新人*/
	@ApiModelProperty(value = "更新人")
	private java.lang.String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "更新日期")
	private java.util.Date updateTime;
	/**所属部门*/
	@ApiModelProperty(value = "所属部门")
	private java.lang.String sysOrgCode;
	/**径高*/
	@Excel(name = "径高", width = 15)
	@ApiModelProperty(value = "径高")
	private java.lang.Integer height;
	/**径粗*/
	@Excel(name = "径粗", width = 15)
	@ApiModelProperty(value = "径粗")
	private java.lang.String widht;
	/**年份*/
	@Excel(name = "年份", width = 15)
	@ApiModelProperty(value = "年份")
	private java.lang.String year;
	/**id*/
	@ApiModelProperty(value = "id")
	private java.lang.String specificationId;
}
