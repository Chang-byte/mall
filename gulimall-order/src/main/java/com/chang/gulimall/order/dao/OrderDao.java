package com.chang.gulimall.order.dao;

import com.chang.gulimall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author chang
 * @email sunlightcs@gmail.com
 * @date 2023-01-25 15:30:12
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
