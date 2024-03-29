package com.chang.gulimall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chang.common.utils.PageUtils;
import com.chang.gulimall.coupon.entity.HomeSubjectEntity;

import java.util.Map;

/**
 * 首页专题表【jd首页下面很多专题，每个专题链接新的页面，展示专题商品信息】
 *
 * @author chang
 * @email sunlightcs@gmail.com
 * @date 2023-01-25 15:26:38
 */
public interface HomeSubjectService extends IService<HomeSubjectEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

