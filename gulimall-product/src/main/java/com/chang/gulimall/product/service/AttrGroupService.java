package com.chang.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chang.common.utils.PageUtils;
import com.chang.gulimall.product.entity.AttrGroupEntity;
import com.chang.gulimall.product.vo.AttrGroupRelationVo;

import java.util.Map;

/**
 * 属性分组
 *
 * @author chang
 * @email sunlightcs@gmail.com
 * @date 2023-01-25 14:34:20
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPage(Map<String, Object> params, Long catelogId);

    void deleteRelation(AttrGroupRelationVo[] vos);
}

