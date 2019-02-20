package com.it.zzg.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.it.zzg.modules.sys.entity.AppEntity;

/**
 * APP管理
 * 
 * @author admin
 * @email admin
 * @date 2018-07-06 09:41:23
 */
@Mapper
public interface AppDao extends BaseDao<AppEntity> {

	List<Long> queryAppIdList(Long userId);
	
}
