package com.it.zzg.modules.flow.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.it.zzg.modules.flow.dao.ListDao;
import com.it.zzg.modules.flow.entity.ListEntity;
import com.it.zzg.modules.flow.service.ListService;



@Service("listService")
public class ListServiceImpl implements ListService {
	@Autowired
	private ListDao listDao;
	
	@Override
	public ListEntity queryObject(String flowCode){
		return listDao.queryObject(flowCode);
	}
	
	@Override
	public List<ListEntity> queryList(Map<String, Object> map){
		return listDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return listDao.queryTotal(map);
	}
	
	@Override
	public void save(ListEntity list){
		listDao.save(list);
	}
	
	@Override
	public void update(ListEntity list){
		listDao.update(list);
	}
	
	@Override
	public void delete(String flowCode){
		listDao.delete(flowCode);
	}
	
	@Override
	public void deleteBatch(String[] flowCodes){
		listDao.deleteBatch(flowCodes);
	}
	
}
