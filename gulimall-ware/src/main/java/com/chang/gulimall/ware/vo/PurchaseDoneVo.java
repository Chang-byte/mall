package com.chang.gulimall.ware.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @title: PurchaseDoneVo
 * @Author Chang
 * @Date: 2023/4/8 12:19
 * @Version 1.0
 */
@Data
public class PurchaseDoneVo {
    @NotNull
    private Long id;

    private List<PurchaseItemDoneVo> items;
}
