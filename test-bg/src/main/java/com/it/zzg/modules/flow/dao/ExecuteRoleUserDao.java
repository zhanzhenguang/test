package com.it.zzg.modules.flow.dao;

import com.it.zzg.modules.flow.entity.ExecuteRoleUserEntity;
import com.it.zzg.modules.sys.dao.BaseDao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

/**
 * 流程执行角色用户中间表
 * 
 * @author ${author}
 * @email ${email}
 * @date 2018-09-18 09:55:25
 */
@Mapper
public interface ExecuteRoleUserDao extends BaseDao<ExecuteRoleUserEntity> {

	List<String> queryRoleIdList(String userId);

	List<String> queryRoleNameList(String userId);

	void updateFlow();
	
}
