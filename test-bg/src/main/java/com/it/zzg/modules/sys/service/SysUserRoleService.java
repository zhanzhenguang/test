package com.it.zzg.modules.sys.service;

import java.util.List;



/**
 * 用户与角色对应关系
 * 
 * @author admin
 * @email admin
 * @date 2016年9月18日 上午9:43:24
 */
public interface SysUserRoleService {
	
	/**
	 * 保存用户与角色关系
	 * @param userId
	 * @param roleIdList
	 */
	void saveOrUpdate(Long userId, List<Long> roleIdList);
	
	/**
	 * 根据用户ID，获取角色ID列表
	 */
	List<Long> queryRoleIdList(Long userId);
	
	void delete(Long userId);
	
	void insert(Long[] userId, String roleId);
	
	void cancelUser(Long roleId, Long userId);

	int querycount(Long userId);
}
