package com.chang.gulimall.coupon.dao;

import com.chang.gulimall.coupon.entity.CouponEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券信息
 * 
 * @author chang
 * @email sunlightcs@gmail.com
 * @date 2023-01-25 15:26:38
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {
	
}
