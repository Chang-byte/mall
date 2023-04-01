package com.chang.gulimall.product.service.impl;

import com.chang.gulimall.product.service.CategoryBrandRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chang.common.utils.PageUtils;
import com.chang.common.utils.Query;

import com.chang.gulimall.product.dao.BrandDao;
import com.chang.gulimall.product.entity.BrandEntity;
import com.chang.gulimall.product.service.BrandService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;


@Service("brandService")
@Slf4j
public class BrandServiceImpl extends ServiceImpl<BrandDao, BrandEntity> implements BrandService {

    @Resource
    private CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        // 从params中获取参数
        String key = (String) params.get("key");
        QueryWrapper<BrandEntity> wrapper = new QueryWrapper<>();
        if (StringUtils.hasText(key)) {
            // 拼接查询条件
            wrapper.like("brand_id", key).or().like("name", key);
        }
        IPage<BrandEntity> page = this.page(
                new Query<BrandEntity>().getPage(params),
                wrapper
        );
        return new PageUtils(page);
    }

    @Override
    @Transactional
    public void updateDetail(BrandEntity brand) {
        // 保证冗余字段的一致

        // 1.更新自己
        this.updateById(brand);
        // 2.如果传递过来的更新的数据中有一些name字段，name在关联表中也需要更新。
        if (StringUtils.hasText(brand.getName())){
            // 更新所有该品牌id为这个的全部的名字name。
            categoryBrandRelationService.updateBrand(brand.getBrandId(),brand.getName());

            // TODO 更新其他关联
        }
    }

}