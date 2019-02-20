package com.it.zzg.modules.job.dao;

import org.apache.ibatis.annotations.Mapper;

import com.it.zzg.modules.job.entity.ScheduleJobLogEntity;
import com.it.zzg.modules.sys.dao.BaseDao;

/**
 * 定时任务日志
 * 
 * @author admin
 * @email admin
 * @date 2016年12月1日 下午10:30:02
 */
@Mapper
public interface ScheduleJobLogDao extends BaseDao<ScheduleJobLogEntity> {
	
}
