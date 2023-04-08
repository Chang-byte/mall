package com.chang.gulimall.product.vo;

import lombok.Data;

/**
 * @title: BrandVo
 * @Author Chang
 * @Date: 2023/4/5 18:42
 * @Version 1.0
 */
@Data
public class BrandVo {
    /*
        "brandId": 0,
		"brandName": "string",
     */

    /**
     * 品牌id
     */
    private Long brandId;

    /**
     * 品牌名字
     */
    private String brandName;
}
