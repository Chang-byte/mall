package com.chang.gulimall.product.controller;

import java.util.Arrays;
import java.util.Map;


import com.chang.common.valid.AddGro;
import com.chang.common.valid.UpdateGro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.chang.gulimall.product.entity.BrandEntity;
import com.chang.gulimall.product.service.BrandService;
import com.chang.common.utils.PageUtils;
import com.chang.common.utils.R;

import javax.validation.Valid;


/**
 * 品牌
 *
 * @author chang
 * @email sunlightcs@gmail.com
 * @date 2023-01-25 14:34:21
 */
@RestController
@RequestMapping("product/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = brandService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{brandId}")
    public R info(@PathVariable("brandId") Long brandId){
		BrandEntity brand = brandService.getById(brandId);

        return R.ok().put("brand", brand);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@Validated(value = AddGro.class) @RequestBody BrandEntity brand){
		brandService.save(brand);

        return R.ok();
    }

    /**
     * 修改， 当关联表中的字段 在原来的表中进行了修改，关联表中的字段的数据必须要同步过来。
     * 保证冗余字段的一致。
     */
    @PostMapping("/update")
    public R update(@Validated(value = UpdateGro.class) @RequestBody BrandEntity brand){
		brandService.updateDetail(brand);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] brandIds){
		brandService.removeByIds(Arrays.asList(brandIds));

        return R.ok();
    }

}
