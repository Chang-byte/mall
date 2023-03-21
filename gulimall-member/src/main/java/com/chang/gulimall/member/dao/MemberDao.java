package com.chang.gulimall.member.dao;

import com.chang.gulimall.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author chang
 * @email sunlightcs@gmail.com
 * @date 2023-01-25 15:34:06
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
