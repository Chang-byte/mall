package com.chang.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.chang.gulimall.product.entity.BrandEntity;
import com.chang.gulimall.product.entity.CategoryEntity;
import com.chang.gulimall.product.service.BrandService;
import com.chang.gulimall.product.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chang.common.utils.PageUtils;
import com.chang.common.utils.Query;

import com.chang.gulimall.product.dao.CategoryBrandRelationDao;
import com.chang.gulimall.product.entity.CategoryBrandRelationEntity;
import com.chang.gulimall.product.service.CategoryBrandRelationService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service("categoryBrandRelationService")
@Slf4j
public class CategoryBrandRelationServiceImpl extends ServiceImpl<CategoryBrandRelationDao, CategoryBrandRelationEntity> implements CategoryBrandRelationService {

    @Resource
    private BrandService brandService;

    @Resource
    private CategoryService categoryService;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryBrandRelationEntity> page = this.page(
                new Query<CategoryBrandRelationEntity>().getPage(params),
                new QueryWrapper<CategoryBrandRelationEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    @Transactional
    public void saveDetail(CategoryBrandRelationEntity categoryBrandRelation) {
        // 品牌id
        Long brandId = categoryBrandRelation.getBrandId();
        // 分类id
        Long catelogId = categoryBrandRelation.getCatelogId();
        BrandEntity brand = brandService.getById(brandId);
        CategoryEntity category = categoryService.getById(catelogId);
        categoryBrandRelation.setBrandName(brand.getName());
        categoryBrandRelation.setCatelogName(category.getName());
        this.save(categoryBrandRelation);

    }

    @Override
    public void updateBrand(Long brandId, String name) {
        CategoryBrandRelationEntity relation = new CategoryBrandRelationEntity();
        relation.setBrandId(brandId);
        relation.setBrandName(name);
        // 传递brand_id条件， 只要brand_id为这个值的，都需要更新，更新就会根据这个relation实体的字段属性更新。
        this.update(relation,
                new UpdateWrapper<CategoryBrandRelationEntity>().eq("brand_id",brandId));
    }

    @Override
    public void updateCategory(Long catId, String name) {
        // 通过写sql去修改
        this.baseMapper.updateCategory(catId, name);
    }

}