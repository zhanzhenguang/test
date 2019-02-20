package com.it.zzg.modules.flow.service;

import com.it.zzg.modules.flow.entity.ExecuteRoleUserEntity;

import java.util.List;
import java.util.Map;

/**
 * 流程执行角色用户中间表
 * 
 * @author ${author}
 * @email ${email}
 * @date 2018-09-18 09:55:25
 */
public interface ExecuteRoleUserService {
	
	ExecuteRoleUserEntity queryObject(String executeRole);
	
	List<ExecuteRoleUserEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(ExecuteRoleUserEntity executeRoleUser);
	
	void update(ExecuteRoleUserEntity executeRoleUser);
	
	void delete(String executeRole);
	
	void deleteBatch(String[] executeRoles);

	/**
	 * 保存流程角色关系
	 * @param flowRoleId
	 * @param userId
	 */
	void saveOrUpdate(List<String> flowRoleIdList, Long userId);

	List<String> queryRoleIdList(Long userId);

	List<String> queryRoleNameList(Long userId);

	void updateFlow();
}
