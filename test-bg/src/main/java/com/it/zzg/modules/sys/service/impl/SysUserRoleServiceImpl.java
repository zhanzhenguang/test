package com.it.zzg.modules.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.it.zzg.modules.sys.dao.SysUserRoleDao;
import com.it.zzg.modules.sys.service.SysUserRoleService;



/**
 * 用户与角色对应关系
 * 
 * @author admin
 * @email admin
 * @date 2016年9月18日 上午9:45:48
 */
@Service("sysUserRoleService")
public class SysUserRoleServiceImpl implements SysUserRoleService {
	@Autowired
	private SysUserRoleDao sysUserRoleDao;

	@Override
	public void saveOrUpdate(Long userId, List<Long> roleIdList) {
		if(roleIdList.size() == 0){
			return ;
		}
		
		//先删除用户与角色关系
		sysUserRoleDao.delete(userId);
		
		//保存用户与角色关系
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("roleIdList", roleIdList);
		sysUserRoleDao.save(map);
	}

	@Override
	public List<Long> queryRoleIdList(Long userId) {
		return sysUserRoleDao.queryRoleIdList(userId);
	}

	@Override
	public void delete(Long userId) {
		sysUserRoleDao.delete(userId);
	}
	
	@Override
	@Transactional
	public void insert(Long[] userId, String roleId) {
		Map<String, Object> map = new HashMap<>();
		map.put("roleId", roleId);
		map.put("userId", userId);
		sysUserRoleDao.insert(map);
	}
	
	public void cancelUser(Long roleId, Long userId) {
		Map<String, Object> map = new HashMap<>();
		map.put("roleId", roleId);
		map.put("userId", userId);
		sysUserRoleDao.cancelUser(map);
	}

	@Override
	public int querycount(Long userId) {
		return sysUserRoleDao.querycount(userId);
	}
}
