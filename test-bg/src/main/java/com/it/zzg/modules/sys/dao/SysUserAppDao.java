package com.it.zzg.modules.sys.dao;

import org.apache.ibatis.annotations.Mapper;

import com.it.zzg.modules.sys.entity.SysUserAppEntity;

/**
 * 用户与APP对应关系
 * 
 * @author admin
 * @email admin
 * @date 2018-07-07 18:57:35
 */
@Mapper
public interface SysUserAppDao extends BaseDao<SysUserAppEntity> {
	
}
