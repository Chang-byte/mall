package com.chang.gulimall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chang.gulimall.product.entity.BrandEntity;
import com.chang.gulimall.product.vo.BrandVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.chang.gulimall.product.entity.CategoryBrandRelationEntity;
import com.chang.gulimall.product.service.CategoryBrandRelationService;
import com.chang.common.utils.PageUtils;
import com.chang.common.utils.R;



/**
 * 品牌分类关联
 *
 * @author chang
 * @email sunlightcs@gmail.com
 * @date 2023-01-25 14:34:21
 */
@RestController
@RequestMapping("product/categorybrandrelation")
public class CategoryBrandRelationController {

    /*
        分类 品牌对应的关系
        手机 华为、苹果、OPPO、小米....

     */
    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;


    /**
     * /product/categorybrandrelation/brands/list
     * 获取分类关联的品牌
     * @param catId 分类id
     * @return
     *
     * controller: 处理请求，接收和校验数据
     * service: 接收controller传来的数据，进行业务处理
     * controller接收service处理完的数据，封装页面制定的vo
     */
    @GetMapping("/brands/list")
    public R relationBrandsList(@RequestParam(value = "catId", required = true) Long catId){
        List<BrandEntity> vos = categoryBrandRelationService.getBrandsByCatId(catId);

        List<BrandVo> collect = vos.stream().map(item -> {
            BrandVo brandVo = new BrandVo();
            brandVo.setBrandId(item.getBrandId());
            brandVo.setBrandName(item.getName());
            return brandVo;
        }).collect(Collectors.toList());

        return R.ok().put("data", collect);
    }

    /**
     * 获取当前品牌关联的所有的分类
     * eg: 品牌：华为  分类：手机、笔记本电脑、通信
     * @param brandId 品牌id
     * @return
     */
    @GetMapping("/catelog/list")
    public R getCatelogList(@RequestParam("brandId") Long brandId) {
        QueryWrapper<CategoryBrandRelationEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("brand_id", brandId);
        List<CategoryBrandRelationEntity> data = categoryBrandRelationService.list(wrapper);
        return R.ok().put("data", data);
    }




    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = categoryBrandRelationService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		CategoryBrandRelationEntity categoryBrandRelation = categoryBrandRelationService.getById(id);

        return R.ok().put("categoryBrandRelation", categoryBrandRelation);
    }

    /**
     * 新增品牌与分类关联关系
     * 因为我们接收的参数只有{"brandId":1,"catelogId":2}，所以，需要再查询数据库获取到对应的品牌名和分类名。
     * @param categoryBrandRelation
     * @return
     */
    @PostMapping("/save")
    public R save(@RequestBody CategoryBrandRelationEntity categoryBrandRelation){
		categoryBrandRelationService.saveDetail(categoryBrandRelation);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CategoryBrandRelationEntity categoryBrandRelation){
		categoryBrandRelationService.updateById(categoryBrandRelation);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		categoryBrandRelationService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
