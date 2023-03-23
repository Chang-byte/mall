package com.chang.gulimall.member.controller;

import java.util.Arrays;
import java.util.Map;

import com.chang.gulimall.member.client.CouponFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.chang.gulimall.member.entity.MemberEntity;
import com.chang.gulimall.member.service.MemberService;
import com.chang.common.utils.PageUtils;
import com.chang.common.utils.R;

import javax.annotation.Resource;


/**
 * 会员
 *
 * @author chang
 * @email sunlightcs@gmail.com
 * @date 2023-01-25 15:34:06
 */
@RestController
@RequestMapping("member/member")
public class MemberController {
    @Autowired
    private MemberService memberService;


    @Resource
    private CouponFeignClient couponFeignClient;

    @GetMapping("/coupons")
    public R test(){
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setNickname("张三");
        R memberCoupons = couponFeignClient.memberCoupons();
        return R.ok()
                .put("member", memberEntity)
                .put("coupons", memberCoupons.get("coupons"));
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = memberService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		MemberEntity member = memberService.getById(id);

        return R.ok().put("member", member);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody MemberEntity member){
		memberService.save(member);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody MemberEntity member){
		memberService.updateById(member);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		memberService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
