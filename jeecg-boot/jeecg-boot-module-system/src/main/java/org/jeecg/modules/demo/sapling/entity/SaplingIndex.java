package org.jeecg.modules.demo.sapling.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
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
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 树苗
 * @Author: jeecg-boot
 * @Date:   2020-07-23
 * @Version: V1.0
 */
@Data
@TableName("sapling_index")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="sapling_index对象", description="树苗")
public class SaplingIndex implements Serializable {
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
    private java.lang.String saplingName;
	/**树苗分类*/
	@Excel(name = "树苗分类", width = 15, dictTable = "sapling_type", dicText = "type_name", dicCode = "id")
	@Dict(dictTable = "sapling_type", dicText = "type_name", dicCode = "id")
    @ApiModelProperty(value = "树苗分类")
    private java.lang.String saplingType;
}
