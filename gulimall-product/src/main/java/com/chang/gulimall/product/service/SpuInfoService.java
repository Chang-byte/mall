package com.chang.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chang.common.utils.PageUtils;
import com.chang.gulimall.product.entity.SpuInfoDescEntity;
import com.chang.gulimall.product.entity.SpuInfoEntity;
import com.chang.gulimall.product.vo.SpuSaveVo;

import java.util.Map;

/**
 * spu信息
 *
 * @author chang
 * @email sunlightcs@gmail.com
 * @date 2023-01-25 14:34:20
 */
public interface SpuInfoService extends IService<SpuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSpuInfo(SpuSaveVo vo);

    void saveBaseSpuInfo(SpuInfoEntity infoEntity);


    PageUtils queryPageByCondition(Map<String, Object> params);
}

