package com.it.zzg.modules.flow.dao;

import com.it.zzg.modules.flow.entity.ExecuteRoleEntity;
import com.it.zzg.modules.sys.dao.BaseDao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 流程执行角色表
 * 
 * @author ${author}
 * @email ${email}
 * @date 2018-09-18 09:55:25
 */
@Mapper
public interface ExecuteRoleDao extends BaseDao<ExecuteRoleEntity> {

	List<ExecuteRoleEntity> select(Map<String, Object> map);

	List<ExecuteRoleEntity> selectForUser(Map<String, Object> map);
	
}
