package com.it.zzg.modules.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.zzg.modules.sys.dao.SysUserOrgRelDao;
import com.it.zzg.modules.sys.service.SysUserOrgRelService;



/**
 * 用户与机构对应关系
 * 
 */
@Service("sysUserOrgRelService")
public class SysUserOrgRelServiceImpl implements SysUserOrgRelService {
	@Autowired
	private SysUserOrgRelDao sysUserOrgRelDao;

	@Override
	public void saveOrUpdate(Long userId, List<Long> orgIdList) {
		if(orgIdList == null || orgIdList.size() == 0){
			return ;
		}
		
		//先删除用户与角色关系
		sysUserOrgRelDao.delete(userId);
		
		//保存用户与角色关系
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("orgIdList", orgIdList);
		sysUserOrgRelDao.save(map);
	}

	@Override
	public List<Long> queryOrgIdList(Long userId) {
		return sysUserOrgRelDao.queryOrgIdList(userId);
	}

	@Override
	public void delete(Long userId) {
		sysUserOrgRelDao.delete(userId);
	}
}
