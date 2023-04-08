package com.chang.gulimall.product.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.chang.gulimall.product.entity.AttrEntity;
import lombok.Data;

import java.util.List;

/**
 * @title: AttrGroupWithAttrsVo
 * @Author Chang
 * @Date: 2023/4/5 19:12
 * @Version 1.0
 */
@Data
public class AttrGroupWithAttrsVo {
    /**
     * 分组id
     */
    @TableId
    private Long attrGroupId;
    /**
     * 组名
     */
    private String attrGroupName;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 描述
     */
    private String descript;
    /**
     * 组图标
     */
    private String icon;
    /**
     * 所属分类id
     */
    private Long catelogId;

    private List<AttrEntity> Attrs;
}
