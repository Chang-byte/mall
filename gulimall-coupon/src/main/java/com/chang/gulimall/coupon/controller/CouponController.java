package com.chang.gulimall.coupon.controller;

import java.util.Arrays;
import java.util.Map;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import com.chang.gulimall.coupon.entity.CouponEntity;
import com.chang.gulimall.coupon.service.CouponService;
import com.chang.common.utils.PageUtils;
import com.chang.common.utils.R;



/**
 * 优惠券信息
 *
 * @author chang
 * @email sunlightcs@gmail.com
 * @date 2023-01-25 15:26:38
 */
@RestController
@RequestMapping("coupon/coupon")
@Slf4j
@RefreshScope
public class CouponController {
    @Autowired
    private CouponService couponService;


    @Value("${user.userName}")
    private String userName;

    @Value("${user.age}")
    private Integer age;
    @GetMapping("/test")
    public R test(){
        return R.ok().put("userName", userName)
                .put("age", age);
    }

    @GetMapping("/member/list")
    public R memberCoupons(){
        CouponEntity couponEntity = new CouponEntity();
        couponEntity.setCouponName("满100减10");
        return R.ok().put("coupons", Arrays.asList(couponEntity));
    }


    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = couponService.queryPage(params);
        log.info("List");
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		CouponEntity coupon = couponService.getById(id);

        return R.ok().put("coupon", coupon);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CouponEntity coupon){
		couponService.save(coupon);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CouponEntity coupon){
		couponService.updateById(coupon);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		couponService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
