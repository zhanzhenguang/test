package com.it.zzg.modules.flow.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.it.zzg.modules.flow.dao.ExecuteRoleDao;
import com.it.zzg.modules.flow.entity.ExecuteRoleEntity;
import com.it.zzg.modules.flow.service.ExecuteRoleService;



@Service("executeRoleService")
public class ExecuteRoleServiceImpl implements ExecuteRoleService {
	@Autowired
	private ExecuteRoleDao executeRoleDao;
	
	@Override
	public ExecuteRoleEntity queryObject(String executeRole){
		return executeRoleDao.queryObject(executeRole);
	}
	
	@Override
	public List<ExecuteRoleEntity> queryList(Map<String, Object> map){
		return executeRoleDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return executeRoleDao.queryTotal(map);
	}
	
	@Override
	public void save(ExecuteRoleEntity executeRole){
		executeRoleDao.save(executeRole);
	}
	
	@Override
	public void update(ExecuteRoleEntity executeRole){
		executeRoleDao.update(executeRole);
	}
	
	@Override
	public void delete(String executeRole){
		executeRoleDao.delete(executeRole);
	}
	
	@Override
	public void deleteBatch(String[] executeRoles){
		executeRoleDao.deleteBatch(executeRoles);
	}

	@Override
	public List<ExecuteRoleEntity> select(Map<String, Object> map) {
		return executeRoleDao.select(map);
	}

	@Override
	public List<ExecuteRoleEntity> selectForUser(Map<String, Object> map) {
		return executeRoleDao.selectForUser(map);
	}
	
}
