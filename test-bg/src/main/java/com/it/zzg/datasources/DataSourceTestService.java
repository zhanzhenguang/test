package com.it.zzg.datasources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.zzg.datasources.annotation.DataSource;
import com.it.zzg.modules.user.entity.UserEntity;
import com.it.zzg.modules.user.service.UserService;

/**
 * 测试
 * @author admin
 * @email admin
 * @date 2017/9/16 23:10
 */
@Service
public class DataSourceTestService implements DataSourceTestInterface{

    @Autowired
    private UserService userService;

    @Override
    public UserEntity queryObject(Long userId){
        return userService.queryObject(userId);
    }

    @DataSource(name = DataSourceNames.SECOND)
    @Override
    public UserEntity queryObject2(Long userId){
        return userService.queryObject(userId);
    }
}
