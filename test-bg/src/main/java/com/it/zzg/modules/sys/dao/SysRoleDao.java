package com.it.zzg.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.it.zzg.modules.sys.entity.SysRoleEntity;

/**
 * 角色管理
 * 
 * @author admin
 * @email admin
 * @date 2016年9月18日 上午9:33:33
 */
@Mapper
public interface SysRoleDao extends BaseDao<SysRoleEntity> {
	
	/**
	 * 查询用户创建的角色ID列表
	 */
	List<Long> queryRoleIdList(Long userId);

}
