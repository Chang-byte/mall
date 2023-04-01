package com.chang.gulimall.product.service.impl;

import com.chang.common.utils.R;
import com.chang.gulimall.product.dao.AttrAttrgroupRelationDao;
import com.chang.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.chang.gulimall.product.vo.AttrGroupRelationVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chang.common.utils.PageUtils;
import com.chang.common.utils.Query;

import com.chang.gulimall.product.dao.AttrGroupDao;
import com.chang.gulimall.product.entity.AttrGroupEntity;
import com.chang.gulimall.product.service.AttrGroupService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;


@Service("attrGroupService")
@Slf4j
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Resource
    private AttrAttrgroupRelationDao relationDao;




    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void deleteRelation(AttrGroupRelationVo[] vos) {
        List<AttrAttrgroupRelationEntity> entities = Arrays.stream(vos).map((entity) -> {
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            BeanUtils.copyProperties(entity, relationEntity);
            return relationEntity;
        }).collect(Collectors.toList());
        relationDao.deleteBatchRelation(entities);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params, Long catelogId) {
        log.info("Query{}", params);
        QueryWrapper<AttrGroupEntity> wrapper = new QueryWrapper<>();
        String key = (String) params.get("key");
        // 拼接sql select * from pms_attr_group where catelog_id = ? and (attr_group_id = key or attr_group_name like ?)
        if (!StringUtils.isEmpty(key)){
            wrapper.and((obj)->{
                obj.eq("attr_group_id", key)
                        .or().like("attr_group_name", key);
            });
        }
        if (catelogId == 0){
            // 分页查询
            IPage<AttrGroupEntity> page = this.page(new Query<AttrGroupEntity>().getPage(params),
                    wrapper);
            return new PageUtils(page);
        }else {
            wrapper.eq("catelog_id", catelogId);
            IPage<AttrGroupEntity> page = this.page(new Query<AttrGroupEntity>().getPage(params), wrapper);
            return new PageUtils(page);
        }
    }

}