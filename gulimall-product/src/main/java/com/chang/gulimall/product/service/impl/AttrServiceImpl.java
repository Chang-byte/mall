package com.chang.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.chang.common.constant.ProductConstant;
import com.chang.gulimall.product.dao.AttrAttrgroupRelationDao;
import com.chang.gulimall.product.dao.AttrGroupDao;
import com.chang.gulimall.product.dao.CategoryDao;
import com.chang.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.chang.gulimall.product.entity.AttrGroupEntity;
import com.chang.gulimall.product.entity.CategoryEntity;
import com.chang.gulimall.product.service.CategoryService;
import com.chang.gulimall.product.vo.AttrRespVo;
import com.chang.gulimall.product.vo.AttrVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chang.common.utils.PageUtils;
import com.chang.common.utils.Query;

import com.chang.gulimall.product.dao.AttrDao;
import com.chang.gulimall.product.entity.AttrEntity;
import com.chang.gulimall.product.service.AttrService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    @Resource
    private AttrAttrgroupRelationDao relationDao;

    @Resource
    private AttrGroupDao attrGroupDao;

    @Resource
    private CategoryDao categoryDao;

    @Resource
    private CategoryService categoryService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    @Override
    @Transactional
    public void saveAttr(AttrVo attr) {
        // 1. 保存基本数据
        AttrEntity attrEntity = new AttrEntity();
        // 前提是属性名都是意义对应的
        BeanUtils.copyProperties(attr, attrEntity);
        this.save(attrEntity);
        if (attr.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()
        && attr.getAttrGroupId() != null) {
            // 2.保存关联关系
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            relationEntity.setAttrGroupId(attr.getAttrGroupId());
            relationEntity.setAttrId(attrEntity.getAttrId());
            relationDao.insert(relationEntity);
        }
    }

    @Override
    public PageUtils queryBaseAttrPage(Map<String, Object> params, Long catelogId, String attrType) {
        // 不论任何时候都要拼装
        QueryWrapper<AttrEntity> wrapper = new QueryWrapper<AttrEntity>()
                .eq("attr_type",
                        "base".equalsIgnoreCase(attrType) ?
                                ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode() :
                                ProductConstant.AttrEnum.ATTR_TYPE_SALE.getCode());

        if (catelogId != 0){
            wrapper.eq("catelog_id", catelogId);
        }

        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)){
            // 添加模糊查询
            wrapper.and((queryWrapper)->{
                queryWrapper.eq("attr_id", key).or().
                        like("attr_name", key);
            });
        }
        IPage<AttrEntity> page =
                this.page(new Query<AttrEntity>().getPage(params), wrapper);
        PageUtils pageUtils = new PageUtils(page);
        List<AttrEntity> records = page.getRecords();
        List<AttrRespVo> respVos = records.stream().map((attrEntity) -> {
            AttrRespVo attrRespVo = new AttrRespVo();
            BeanUtils.copyProperties(attrEntity, attrRespVo);
            if ("base".equalsIgnoreCase(attrType)){
                // 根据属性id，查询属性所在分组的id
                AttrAttrgroupRelationEntity attrId =
                        relationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>()
                                .eq("attr_id", attrEntity.getAttrId()));
                if (attrId != null && attrId.getAttrGroupId() != null) {
                    AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrId.getAttrGroupId());
                    attrRespVo.setGroupName(attrGroupEntity.getAttrGroupName());
                }
            }
            CategoryEntity categoryEntity = categoryDao.selectById(attrEntity.getCatelogId());
            if (categoryEntity != null) {
                attrRespVo.setCatelogName(categoryEntity.getName());
            }

            return attrRespVo;

        }).collect(Collectors.toList());
        pageUtils.setList(respVos);
        return pageUtils;
    }

    @Override
    @Transactional
    public AttrRespVo getAttrInfo(Long attrId) {
        // 查询当前attr的详细信息
        AttrRespVo attrRespVo = new AttrRespVo();
        AttrEntity attrEntity = this.getById(attrId);
        BeanUtils.copyProperties(attrEntity, attrRespVo);

        // 基本属性才需要查询分组信息
        if (attrEntity.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()){
            // 查询所属的分组信息
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity =
                    relationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>()
                            .eq("attr_id", attrId));
            if (attrAttrgroupRelationEntity != null) {
                attrRespVo.setAttrGroupId(attrAttrgroupRelationEntity.getAttrGroupId());
                AttrGroupEntity attrGroupEntity =
                        attrGroupDao.selectById(attrAttrgroupRelationEntity.getAttrGroupId());
                if (attrGroupEntity != null) {
                    attrRespVo.setGroupName(attrGroupEntity.getAttrGroupName());
                }
            }
        }

        // 查询分类信息 查询相应的完整的路径
        Long catelogId = attrEntity.getCatelogId();
        Long[] catelogPath = categoryService.getCatelogPath(catelogId);
        attrRespVo.setCatelogPath(catelogPath);
        CategoryEntity categoryEntity = categoryDao.selectById(catelogId);
        if (categoryEntity != null){
            attrRespVo.setCatelogName(categoryEntity.getName());
        }
        return attrRespVo;
    }

    @Override
    @Transactional
    public void updateAttr(AttrVo attrVo) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attrVo, attrEntity);
        this.updateById(attrEntity);

        if (attrEntity.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()){
            // 修改分组关联
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();

            relationEntity.setAttrId(attrVo.getAttrId());
            relationEntity.setAttrGroupId(attrVo.getAttrGroupId());
            // 根据传入的实体类的属性字段进行修改， where条件就是updateWrapper的eq条件
            // update pms_attr_attrgroup_relation wet attr_group_id = ? where attr_id = ?;
            relationDao.update(relationEntity, new UpdateWrapper<AttrAttrgroupRelationEntity>()
                    .eq("attr_id", attrVo.getAttrId()));
        }


    }

    /**
     * 获取当前分组没有关联的属性
     *  1. 当前分组只能关联自己所属的分类里的所有的属性
     *  2. 当前分组只能关联别的分组没有引用的属性。
     *  分类 category
     *      品牌 brand
     *      属性分组 attrGroup
     *          属性 attr
     * 整体思路：先找出其他分组已经关联的属性，再排除掉这些属性即可
     * 实现思路：1.根据catelogId获取分组对象集合-->2.获取分组对象的attrGroupId并封装为集合
     * -->3.根据attrGroupId获取中间表对象集合-->4.获取中间表对象的attrId并封装为集合
     * 此时就有了其他分组已经关联的属性-->5.移除这些属性
     *
     * 自己关联的属性也要移除。
     * @param params
     * @param attrgroupId
     * @return 只查询一些基本属性
     */
    @Override
    public PageUtils getNoRelationAttr(Map<String, Object> params, Long attrgroupId) {
        // 按照分组id，查询当前分组的所有的消息
        AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrgroupId);
        // 从当前分组中获取当前对应分类的id
        Long catelogId = attrGroupEntity.getCatelogId();
        // 查询当前分类下的分组
        List<AttrGroupEntity> group = attrGroupDao
                .selectList(new QueryWrapper<AttrGroupEntity>()
                .eq("catelog_id", catelogId));
//                .ne("attr_group_id", attrgroupId));
        // 其他分组id
        List<Long> otherAttrGroupIds = group.stream().map((item) -> {
            return item.getAttrGroupId();
        }).collect(Collectors.toList());
        // 查询分组所关联的属性
        List<AttrAttrgroupRelationEntity> others = relationDao
                .selectList(
                        new QueryWrapper<AttrAttrgroupRelationEntity>()
                                .in("attr_group_id", otherAttrGroupIds));
        List<Long> otherAttrIds = others.stream().map((item) -> {
            return item.getAttrId();
        }).collect(Collectors.toList());
        // 从当前分类的所有属性中移除这些属性
        QueryWrapper<AttrEntity> wrapper = new QueryWrapper<AttrEntity>()
                .eq("catelog_id", catelogId)
                .eq("attr_type", ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode());
        if (otherAttrIds != null && otherAttrIds.size() > 0) {
            wrapper.notIn("attr_id", otherAttrIds);
        }
        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)){
            wrapper.and((w) ->{
                w.eq("attr_id", key)
                        .or()
                        .like("attr_name", key);
            });
        }
        IPage<AttrEntity> page = this.page(new Query<AttrEntity>().getPage(params), wrapper);
        return new PageUtils(page);
    }

    /**
     * 根据分组id查询关联的所有属性
     * @param attrgroupId
     * @return
     */
    @Override
    public List<AttrEntity> getRelationAttr(Long attrgroupId) {
        List<AttrAttrgroupRelationEntity> entities = relationDao.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>()
                .eq("attr_group_id", attrgroupId));
        List<Long> attrIds =
                entities.stream().map((entity) -> entity.getAttrId())
                        .collect(Collectors.toList());
        if (attrIds.size() == 0 || attrIds == null) {
            return null;
        }
        // 根据分组id的集合进行查询。
        Collection<AttrEntity> attrEntities = this.listByIds(attrIds);
        return (List<AttrEntity>) attrEntities;
    }

}