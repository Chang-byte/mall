package com.chang.gulimall.ware.vo;

import lombok.Data;

/**
 * @title: PurchaserItemDoneVo
 * @Author Chang
 * @Date: 2023/4/8 12:20
 * @Version 1.0
 */
@Data
public class PurchaseItemDoneVo {

    private Long itemId;

    private Integer status;

    private String reason;

}
