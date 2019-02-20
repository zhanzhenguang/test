package com.it.zzg.modules.flow.service;

import com.it.zzg.modules.flow.entity.ExecuteRoleEntity;

import java.util.List;
import java.util.Map;

/**
 * 流程执行角色表
 * 
 * @author ${author}
 * @email ${email}
 * @date 2018-09-18 09:55:25
 */
public interface ExecuteRoleService {
	
	ExecuteRoleEntity queryObject(String executeRole);
	
	List<ExecuteRoleEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(ExecuteRoleEntity executeRole);
	
	void update(ExecuteRoleEntity executeRole);
	
	void delete(String executeRole);
	
	void deleteBatch(String[] executeRoles);

	List<ExecuteRoleEntity> select(Map<String, Object> map);

	List<ExecuteRoleEntity> selectForUser(Map<String, Object> map);
}
