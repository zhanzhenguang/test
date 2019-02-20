package com.it.zzg.modules.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.it.zzg.modules.sys.dao.SysOrgDao;
import com.it.zzg.modules.sys.entity.SysOrgEntity;
import com.it.zzg.modules.sys.service.SysOrgService;


@Service("sysOrgService")
public class SysOrgServiceImpl implements SysOrgService {
	@Autowired
	private SysOrgDao sysOrgDao;
	
	@Override
	public List<SysOrgEntity> queryListParentId(Long parentId) {
		return sysOrgDao.queryListParentId(parentId);
	}

	@Override
	public List<SysOrgEntity> queryNotButtonList(Map<String, Object> params) {
		return sysOrgDao.queryNotButtonList(params);
	}

	
	@Override
	public SysOrgEntity queryObject(Long orgId) {
		return sysOrgDao.queryObject(orgId);
	}

	@Override
	public List<SysOrgEntity> queryList(Map<String, Object> map) {
		return sysOrgDao.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return sysOrgDao.queryTotal(map);
	}

	@Override
	public void save(SysOrgEntity org) {
		sysOrgDao.save(org);
	}

	@Override
	public void update(SysOrgEntity org) {
		sysOrgDao.update(org);
	}

	@Override
	@Transactional
	public void deleteBatch(Long[] ids) {
		sysOrgDao.deleteBatch(ids);
	}
	
	@Override
	public SysOrgEntity selectOrg(Long userId) {
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		return sysOrgDao.selectOrg(map);
	}

	@Override
	public void deleteByOrgCode(String orgCode) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("orgCode",orgCode);
		this.sysOrgDao.deleteByOrgCode(param);
	}

	@Override
	public Integer checkOrgCode(String orgCode) {
		return this.sysOrgDao.checkOrgCode(orgCode);
	}

}
