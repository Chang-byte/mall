package com.chang.gulimall.product.dao;

import com.chang.gulimall.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author chang
 * @email sunlightcs@gmail.com
 * @date 2023-01-25 14:34:20
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
