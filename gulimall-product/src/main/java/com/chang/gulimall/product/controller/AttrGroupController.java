package com.chang.gulimall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


import com.chang.gulimall.product.entity.AttrEntity;
import com.chang.gulimall.product.entity.CategoryEntity;
import com.chang.gulimall.product.service.AttrAttrgroupRelationService;
import com.chang.gulimall.product.service.AttrService;
import com.chang.gulimall.product.service.CategoryService;
import com.chang.gulimall.product.vo.AttrGroupRelationVo;
import com.chang.gulimall.product.vo.AttrGroupWithAttrsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.chang.gulimall.product.entity.AttrGroupEntity;
import com.chang.gulimall.product.service.AttrGroupService;
import com.chang.common.utils.PageUtils;
import com.chang.common.utils.R;

import javax.annotation.Resource;


/**
 * 属性分组
 *
 * @author chang
 * @email sunlightcs@gmail.com
 * @date 2023-01-25 14:34:20
 */
@RestController
@RequestMapping("product/attrgroup")
public class AttrGroupController {
    @Resource
    private AttrGroupService attrGroupService;

    @Resource
    private CategoryService categoryService;

    @Resource
    private AttrService attrService;

    @Resource
    private AttrAttrgroupRelationService relationService;


    /**
     * 获取分类属性分组
     * @param params 公共请求数据
     * @param catelogId 分类ID
     * @return
     */
    @GetMapping("/list/{catelogId}")
    public R getDataList(@RequestParam Map<String, Object> params,
                         @PathVariable("catelogId") Long catelogId) {
        PageUtils page = attrGroupService.queryPage(params, catelogId);
        // 自5.3版本起，isEmpty(Object)已建议弃用，使用hasLength(String)或hasText(String)替代。
        return R.ok().put("page", page);
    }

    /*
        查出当前分组关联出的所有属性。 多对多
        分组：内存
        属性: 128G、 256G、 512G
     */

    /*
        某一个分类下的所有分组：
            手机类
                主体
                基本信息
        某一分组还会有好多参数
            主体：
                上市年份 2022、 2023等
                入网型号 4G、5G 等
     */

    /**
     * product/attrgroup/{attrgroupId}/attr/relation
     * 获取所有分组与属性的关联
     * @param attrgroupId
     * @return
     */
    @GetMapping("/{attrgroupId}/attr/relation")
    public R attrRelation(@PathVariable("attrgroupId") Long attrgroupId){
        List<AttrEntity> entities = attrService.getRelationAttr(attrgroupId);
        return R.ok().put("data",entities);
    }


    // 获取分类下所有分组&关联属性
    // /product/attrgroup/{catelogId}/withattr
    @GetMapping("/{catelogId}/withattr")
    public R getAttrGroupWithAttrs(@PathVariable("catelogId") Long catelogId){
        // 查询当前分类下的所有属性分组
        // 查询每个属性分组的所有属性
        List<AttrGroupWithAttrsVo> vos = attrGroupService.getAttrGroupWithAttrs(catelogId);
        return R.ok().put("data", vos);
    }

    /**
     * 删除属性与分组的关联关系
     * 使用post请求，需要加上@RequestBody注解
     * @param vos
     * @return
     */
    @PostMapping("/attr/relation/delete")
    public R deleteRelation(@RequestBody AttrGroupRelationVo[] vos){
        attrGroupService.deleteRelation(vos);
        return R.ok();
    }

    // 获取当前分组没有关联的其他属性 /product/attrgroup/{attrgroupId}/noattr/relation
    @GetMapping("/{attrgroupId}/noattr/relation")
    public R attrNoRelation(@PathVariable("attrgroupId") Long attrgroupId,
                            @RequestParam Map<String, Object> params){
        PageUtils page = attrService.getNoRelationAttr(params, attrgroupId);
        return R.ok().put("page", page);
    }

    // 添加属性与分组关联关系 /product/attrgroup/attr/relation
    @PostMapping("/attr/relation")
    public R addRelation(@RequestBody List<AttrGroupRelationVo> vos){
        relationService.saveBatch(vos);
        return R.ok();
    }


    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = attrGroupService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrGroupId}")
    public R info(@PathVariable("attrGroupId") Long attrGroupId){
		AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);
        // 查询分类对应的三级，用数组返回
        Long catelogId = attrGroup.getCatelogId();
//        CategoryEntity category = categoryService.getById(catelogId);
        Long[] catelogPath = categoryService.getCatelogPath(catelogId);
        attrGroup.setCatelogPath(catelogPath);
        return R.ok().put("attrGroup", attrGroup);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.save(attrGroup);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.updateById(attrGroup);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] attrGroupIds){
		attrGroupService.removeByIds(Arrays.asList(attrGroupIds));

        return R.ok();
    }

}
