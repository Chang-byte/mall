package com.chang.gulimall.product.service.impl;

import com.chang.gulimall.product.service.CategoryBrandRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chang.common.utils.PageUtils;
import com.chang.common.utils.Query;

import com.chang.gulimall.product.dao.CategoryDao;
import com.chang.gulimall.product.entity.CategoryEntity;
import com.chang.gulimall.product.service.CategoryService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static com.sun.javafx.robot.impl.FXRobotHelper.getChildren;


@Service("categoryService")
@Slf4j
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Resource
    private CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> liwtWithTree() {
        // 1. 查出所有分类
        List<CategoryEntity> entities = this.list(null);
        // 2. 组装成父子的结构
        // 2.1 找到所有的一级分类
        List<CategoryEntity> level1Menus  = entities.stream()
                // 查询符合条件的filter
                .filter((categoryEntity -> categoryEntity.getParentCid() == 0))
                .map(item -> {
                    item.setChildrens(getChildren(item, entities));
                    return item;
                })//升序排列
                .sorted(Comparator.comparingInt(menu -> (menu.getSort() == null ? 0 : menu.getSort())))
                .collect(Collectors.toList());

        return level1Menus;
    }

    @Override
    public void removeMenusByIds(List<Long> asList) {
        // TODO: 检查当前删除的菜单，是否被其他地方引用
        // 使用逻辑删除0 删除 1 不删除 showStatus字段
        this.removeByIds(asList);
    }

    /**
     * 根据当前分类ID 查询出对应的数组的ID
     * @param catelogId
     * @return
     */
    @Override
    public Long[] getCatelogPath(Long catelogId) {
        log.info("catelog path:{}", catelogId);
        List<Long> list = new ArrayList<>();
        findParentPath(catelogId, list);
        return list.toArray(new Long[0]);
    }

    /**
     * 级联更新所有关联的数据
     * 除了更新自己，还需要更新关联表中的所有的数据
     * @param category
     */
    @Override
    @Transactional
    public void updateCascade(CategoryEntity category) {
        this.updateById(category);
        // 级联更新
        categoryBrandRelationService.updateCategory(category.getCatId(),category.getName());
    }

    private void findParentPath(Long catelogId, List<Long> list){
        // 查询当前分类
        CategoryEntity category = this.getById(catelogId);
        if (category.getParentCid() != 0){ // 还有父分类属性
            findParentPath(category.getParentCid(), list);
        }
        // 收集当前结点id
        list.add(catelogId);
    }

    /**
     * 递归查询所有菜单的子菜单
     * @param item 菜单
     * @param all 全体菜单
     * @return
     */
    public List<CategoryEntity> getChildren(CategoryEntity item, List<CategoryEntity> all){
        List<CategoryEntity> children = all.stream()
                .filter(categoryEntity -> categoryEntity.getParentCid().equals(item.getCatId())
        ).map(categoryEntity -> {
            categoryEntity.setChildrens(getChildren(categoryEntity, all));
            return categoryEntity;
        })
                .sorted(Comparator.comparingInt(menu -> (menu.getSort() == null ? 0 : menu.getSort())))
                .collect(Collectors.toList());
        // 排序要这样不然空指针来了.sorted(Comparator.comparingInt(menu -> (menu.getSort() == null ? 0 : menu.getSort())))
        return children;
    }

}