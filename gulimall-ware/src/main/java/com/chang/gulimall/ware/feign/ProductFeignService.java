package com.chang.gulimall.ware.feign;

import com.chang.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @title: ProductFeignService
 * @Author Chang
 * @Date: 2023/4/8 13:12
 * @Version 1.0
 */
@FeignClient("gulimall-product")
public interface ProductFeignService {
    /**
     *      /product/skuinfo/info/{skuId}
     *
     *   1)、让所有请求过网关: 相当于来到网关了。
     *          1、@FeignClient("gulimall-gateway")：给gulimall-gateway所在的机器发请求
     *          2、/api/product/skuinfo/info/{skuId}
     *   2)、直接让后台指定服务处理:
     *          1、@FeignClient("gulimall-product")
     *          2、/product/skuinfo/info/{skuId}
     *
     * @return
     */
    @GetMapping("/product/skuinfo/info/{skuId}")
    R info(@PathVariable("skuId") Long skuId);
}

