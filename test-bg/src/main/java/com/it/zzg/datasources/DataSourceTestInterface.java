package com.it.zzg.datasources;

import com.it.zzg.modules.user.entity.UserEntity;

/**
 * Created by admin on 2018/3/15.
 */
public interface DataSourceTestInterface {

    UserEntity queryObject(Long userId);

    UserEntity queryObject2(Long userId);
}
