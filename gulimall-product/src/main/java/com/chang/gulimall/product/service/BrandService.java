package com.chang.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chang.common.utils.PageUtils;
import com.chang.gulimall.product.entity.BrandEntity;

import java.util.Map;

/**
 * 品牌
 *
 * @author chang
 * @email sunlightcs@gmail.com
 * @date 2023-01-25 14:34:21
 */
public interface BrandService extends IService<BrandEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void updateDetail(BrandEntity brand);
}

