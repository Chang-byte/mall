package com.chang.gulimall.product.vo;

import lombok.Data;

/**
 * @title: AttrRespVo
 * @Author Chang
 * @Date: 2023/3/31 10:50
 * @Version 1.0
 */
@Data
public class AttrRespVo extends AttrVo{

    /**
     * 所属分类的名字
     */
    private String catelogName;

    /**
     * 所属分组
     */
    private String groupName;

    /**
     * 分组的id
     */
    private Long[] catelogPath;
}
