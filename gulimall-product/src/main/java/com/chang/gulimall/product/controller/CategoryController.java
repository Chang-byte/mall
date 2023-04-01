package com.chang.gulimall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.chang.gulimall.product.entity.CategoryEntity;
import com.chang.gulimall.product.service.CategoryService;
import com.chang.common.utils.PageUtils;
import com.chang.common.utils.R;



/**
 * 商品三级分类
 *
 * @author chang
 * @email sunlightcs@gmail.com
 * @date 2023-01-25 14:34:20
 */
@RestController
@RequestMapping("product/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;


    /**
     * 查出所有的分类以及子类，以树形结构组装起来。
     * @return
     */
    @GetMapping("/list/tree")
    public R list(){
        List<CategoryEntity> entities =  categoryService.liwtWithTree();
        return R.ok().put("data", entities);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = categoryService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{catId}")
    public R info(@PathVariable("catId") Long catId){
		CategoryEntity category = categoryService.getById(catId);

        return R.ok().put("category", category);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody CategoryEntity category){
		categoryService.save(category);

        return R.ok();
    }

    /**
     * 修改,和品牌一样，同时更新关联的所有的数据。
     */
    @PostMapping("/update")
    public R update(@RequestBody CategoryEntity category){
		categoryService.updateCascade(category);

        return R.ok();
    }

    /**
     * 删除
     * @RequestBody: 获取请求体，必须发送POST请求。
     * SpringMVC自动将请求体的数据(json)转换为对应的对象。
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] catIds){
//		categoryService.removeByIds(Arrays.asList(catIds));

        // 1.检查当前删除的菜单，是否被其他地方引用。
        categoryService.removeMenusByIds(Arrays.asList(catIds));

        return R.ok();
    }

    /**
     * 发送请求获取当前节点最新的数据 用以数据的回显
     * @param catId
     * @return
     */
    @GetMapping("/info/{catId}")
    public R getInfo(@PathVariable("catId") Long catId){
        CategoryEntity category = categoryService.getById(catId);
        return R.ok().put("data", category);
    }

}
