package com.it.zzg.modules.sys.service;

import java.util.List;



/**
 * 用户与机构对应关系
 * 
 */
public interface SysUserOrgRelService {
	
	/**
	 * 保存用户与角色关系
	 * @param userId
	 * @param orgIdList
	 */
	void saveOrUpdate(Long userId, List<Long> orgIdList);
	
	/**
	 * 根据用户ID，获取角色ID列表
	 */
	List<Long> queryOrgIdList(Long userId);
	
	void delete(Long userId);
}
