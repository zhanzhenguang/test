package com.it.zzg.modules.flow.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.it.zzg.modules.flow.dao.ExecuteRoleUserDao;
import com.it.zzg.modules.flow.entity.ExecuteRoleUserEntity;
import com.it.zzg.modules.flow.service.ExecuteRoleUserService;



@Service("executeRoleUserService")
public class ExecuteRoleUserServiceImpl implements ExecuteRoleUserService {
	@Autowired
	private ExecuteRoleUserDao executeRoleUserDao;
	
	@Override
	public ExecuteRoleUserEntity queryObject(String executeRole){
		return executeRoleUserDao.queryObject(executeRole);
	}
	
	@Override
	public List<ExecuteRoleUserEntity> queryList(Map<String, Object> map){
		return executeRoleUserDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return executeRoleUserDao.queryTotal(map);
	}
	
	@Override
	public void save(ExecuteRoleUserEntity executeRoleUser){
		executeRoleUserDao.save(executeRoleUser);
	}
	
	@Override
	public void update(ExecuteRoleUserEntity executeRoleUser){
		executeRoleUserDao.update(executeRoleUser);
	}
	
	@Override
	public void delete(String executeRole){
		executeRoleUserDao.delete(executeRole);
	}
	
	@Override
	public void deleteBatch(String[] executeRoles){
		executeRoleUserDao.deleteBatch(executeRoles);
	}

	/**
	 * 保存流程角色关系
	 */
	@Override
	public void saveOrUpdate(List<String> flowRoleIdList, Long userId) {
		//先删除用户与角色关系
		executeRoleUserDao.delete(userId);
		
		//保存用户与角色关系
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("flowRoleIdList", flowRoleIdList);
		executeRoleUserDao.save(map);
	}

	@Override
	public List<String> queryRoleIdList(Long value) {
		String userId = String.valueOf(value);
		return executeRoleUserDao.queryRoleIdList(userId);
	}

	@Override
	public List<String> queryRoleNameList(Long value) {
		String userId = String.valueOf(value);
		return executeRoleUserDao.queryRoleNameList(userId);
	}

	@Override
	public void updateFlow() {
		executeRoleUserDao.updateFlow();
		
	}
	
}
