package com.it.zzg.modules.flow.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.it.zzg.modules.flow.dao.NodeDao;
import com.it.zzg.modules.flow.entity.NodeEntity;
import com.it.zzg.modules.flow.service.NodeService;



@Service("nodeService")
public class NodeServiceImpl implements NodeService {
	@Autowired
	private NodeDao nodeDao;
	
	@Override
	public NodeEntity queryObjectByOne(String flowCode,String nodeCode){
		return nodeDao.queryObjectByOne(flowCode,nodeCode);
	}
	
	@Override
	public List<NodeEntity> queryList(Map<String, Object> map){
		return nodeDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return nodeDao.queryTotal(map);
	}
	
	@Override
	public void save(NodeEntity node){
		nodeDao.save(node);
	}
	
	@Override
	public void update(NodeEntity id){
		nodeDao.update(id);
	}
	
	@Override
	public void delete(String id){
		nodeDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		nodeDao.deleteBatch(ids);
	}
	
}
