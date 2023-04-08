package com.chang.gulimall.ware.vo;

import lombok.Data;

import java.util.List;

/**
 * @title: MergeVo
 * @Author Chang
 * @Date: 2023/4/7 21:52
 * @Version 1.0
 */
@Data
public class MergeVo {
    /*
    {
      purchaseId: 1, //订单id
      items:[1,2,3,4] //合并项集合
    }
     */
    private Long purchaseId;

    private List<Long> items;

}
