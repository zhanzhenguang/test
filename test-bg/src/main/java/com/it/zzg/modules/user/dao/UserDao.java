package com.it.zzg.modules.user.dao;

import org.apache.ibatis.annotations.Mapper;

import com.it.zzg.modules.sys.dao.BaseDao;
import com.it.zzg.modules.user.entity.UserEntity;

/**
 * 用户
 * 
 * @author admin
 * @email admin
 * @date 2017-10-23 21:23:54
 */
@Mapper
public interface UserDao extends BaseDao<UserEntity> {
    UserEntity queryByMobile(String mobile);
}
