package com.chang.gulimall.product.vo;

import lombok.Data;

/**
 * @title: AttrGroupRelationVo
 * @Author Chang
 * @Date: 2023/3/31 13:30
 * @Version 1.0
 */
@Data
public class AttrGroupRelationVo {
    // [{"attrId":1,"attrGroupId":2}]
    private Long attrId;

    private Long attrGroupId;
}
