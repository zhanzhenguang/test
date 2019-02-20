package com.it.zzg.modules.job.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.it.zzg.modules.job.entity.ScheduleJobEntity;
import com.it.zzg.modules.sys.dao.BaseDao;

/**
 * 定时任务
 * 
 * @author admin
 * @email admin
 * @date 2016年12月1日 下午10:29:57
 */
@Mapper
public interface ScheduleJobDao extends BaseDao<ScheduleJobEntity> {
	
	/**
	 * 批量更新状态
	 */
	int updateBatch(Map<String, Object> map);
}
