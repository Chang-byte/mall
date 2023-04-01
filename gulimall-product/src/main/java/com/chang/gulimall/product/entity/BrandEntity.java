package com.chang.gulimall.product.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.chang.common.valid.AddGro;
import com.chang.common.valid.ListValue;
import com.chang.common.valid.UpdateGro;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.*;

/**
 * 品牌
 * 
 * @author chang
 * @email sunlightcs@gmail.com
 * @date 2023-01-25 14:34:21
 */
@Data
@TableName("pms_brand")
public class BrandEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 品牌id
	 * js的 Number 类型最大长度是17位，mysql 使用bigint 类型长度是20位。所以才造成精度丢失。
	 */
	@TableId(type = IdType.AUTO)
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Long brandId;
	/**
	 * 品牌名
	 * 非空校验注解
	 * @NotNull
	 * @NotBlack
	 * @NotEmpty
	 */
	@NotBlank
	private String name;
	/**
	 * 品牌logo地址
	 */
	@NotEmpty(groups = {AddGro.class})
	@URL(message = "logo必须是一个合法的url地址。")
	private String logo;
	/**
	 * 介绍
	 */
	@NotEmpty(groups = {AddGro.class})
	private String descript;
	/**
	 * 显示状态[0-不显示；1-显示]
	 */
	@NotNull
	@ListValue(values = {0,1},groups = {AddGro.class, UpdateGro.class},message = "必须在指定集合中")
	private Integer showStatus;
	/**
	 * 检索首字母
	 */
	@NotEmpty
	@Pattern(regexp = "^[a-zA-Z]$", groups = {AddGro.class, UpdateGro.class})
	private String firstLetter;
	/**
	 * 排序
	 */
	@Min(value = 0, message = "排序必须大宇等0", groups = {AddGro.class, UpdateGro.class})
	@NotNull
	private Integer sort;

}
