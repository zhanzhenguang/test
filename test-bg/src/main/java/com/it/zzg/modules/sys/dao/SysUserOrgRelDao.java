package com.it.zzg.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.it.zzg.modules.sys.entity.SysUserOrgRelEntity;

/**
 * 用户与机构对应关系
 * 
 * @author admin
 * @email admin
 * @date 2016年9月18日 上午9:34:46
 */
@Mapper
public interface SysUserOrgRelDao extends BaseDao<SysUserOrgRelEntity> {
	
	/**
	 * 根据用户ID，获取机构ID列表
	 */
	List<Long> queryOrgIdList(Long userId);
}
