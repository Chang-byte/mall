package com.chang.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chang.common.utils.PageUtils;
import com.chang.gulimall.product.entity.AttrEntity;
import com.chang.gulimall.product.vo.AttrRespVo;
import com.chang.gulimall.product.vo.AttrVo;

import java.util.List;
import java.util.Map;

/**
 * 商品属性
 *
 * @author chang
 * @email sunlightcs@gmail.com
 * @date 2023-01-25 14:34:21
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveAttr(AttrVo attr);

    PageUtils queryBaseAttrPage(Map<String, Object> params, Long catelogId, String attrType);

    AttrRespVo getAttrInfo(Long attrId);

    void updateAttr(AttrVo attrVo);

    List<AttrEntity> getRelationAttr(Long attrgroupId);

    PageUtils getNoRelationAttr(Map<String, Object> params, Long attrgroupId);
}

