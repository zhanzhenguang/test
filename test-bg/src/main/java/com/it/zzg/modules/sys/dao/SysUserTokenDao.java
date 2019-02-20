package com.it.zzg.modules.sys.dao;

import org.apache.ibatis.annotations.Mapper;

import com.it.zzg.modules.sys.entity.SysUserTokenEntity;

/**
 * 系统用户Token
 * 
 * @author admin
 * @email admin
 * @date 2017-03-23 15:22:07
 */
@Mapper
public interface SysUserTokenDao extends BaseDao<SysUserTokenEntity> {
    
    SysUserTokenEntity queryByUserId(Long userId);

    SysUserTokenEntity queryByToken(String token);
	
}
