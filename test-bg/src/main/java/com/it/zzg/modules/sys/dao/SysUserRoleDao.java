package com.it.zzg.modules.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.it.zzg.modules.sys.entity.SysUserRoleEntity;

/**
 * 用户与角色对应关系
 * 
 * @author admin
 * @email admin
 * @date 2016年9月18日 上午9:34:46
 */
@Mapper
public interface SysUserRoleDao extends BaseDao<SysUserRoleEntity> {
	
	/**
	 * 根据用户ID，获取角色ID列表
	 */
	List<Long> queryRoleIdList(Long userId);
	
	void insert(Map<String, Object> map);
	
	void cancelUser(Map<String, Object> map);

	int querycount(@Param("userId")Long userId);
}
