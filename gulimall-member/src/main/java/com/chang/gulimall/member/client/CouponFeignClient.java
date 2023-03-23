package com.chang.gulimall.member.client;

import com.chang.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @title: CouponFeignClient
 * @Author Chang
 * @Date: 2023/3/22 22:24
 * @Version 1.0
 */
@FeignClient("gulimall-coupon")
public interface CouponFeignClient {

    @GetMapping("/coupon/coupon/member/list")
    R memberCoupons();
}

