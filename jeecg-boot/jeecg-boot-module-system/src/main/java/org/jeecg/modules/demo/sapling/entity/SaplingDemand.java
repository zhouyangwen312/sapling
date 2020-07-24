package org.jeecg.modules.demo.sapling.entity;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: 需求
 * @Author: jeecg-boot
 * @Date:   2020-07-23
 * @Version: V1.0
 */
@ApiModel(value="sapling_demand对象", description="需求")
@Data
@TableName("sapling_demand")
public class SaplingDemand implements Serializable {
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
	/**树苗名称*/
	@Excel(name = "树苗名称", width = 15)
    @ApiModelProperty(value = "树苗名称")
    private java.lang.String demandName;
	/**需求树苗价格*/
	@Excel(name = "需求树苗价格", width = 15)
    @ApiModelProperty(value = "需求树苗价格")
    private java.lang.Double demandPrice;
	/**树苗数量*/
	@Excel(name = "树苗数量", width = 15)
    @ApiModelProperty(value = "树苗数量")
    private java.lang.Integer demandAmount;
	/**联系人姓名*/
	@Excel(name = "联系人姓名", width = 15)
    @ApiModelProperty(value = "联系人姓名")
    private java.lang.String contactName;
	/**联系人电话*/
	@Excel(name = "联系人电话", width = 15)
    @ApiModelProperty(value = "联系人电话")
    private java.lang.Integer contactUmber;
	/**联系人地址*/
	@Excel(name = "联系人地址", width = 15)
    @ApiModelProperty(value = "联系人地址")
    private java.lang.String contactSite;
	/**发布地区*/
	@Excel(name = "发布地区", width = 15)
    @ApiModelProperty(value = "发布地区")
    private java.lang.String demandArea;
	/**树苗备注*/
	@Excel(name = "树苗备注", width = 15)
    @ApiModelProperty(value = "树苗备注")
    private java.lang.String demandComunt;
	/**介绍费*/
	@Excel(name = "介绍费", width = 15)
    @ApiModelProperty(value = "介绍费")
    private java.lang.Integer ifMoeny;
}
