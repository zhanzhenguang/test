package com.it.zzg.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.zzg.mapper.UserMapper;
import com.it.zzg.model.User;

/**
 * @author calf
 * @create 2017-09-14 17:31
 */
@Service
public class UserService extends BaseService<User> {

	@Autowired
	UserMapper userMapper;
	
	public long saveUser() {
		Long b = System.currentTimeMillis();
		System.out.println("插入数据begin="+b);
		SimpleDateFormat f = new SimpleDateFormat("yyMMddHHmmssSSS");
		Random random = new Random();
		User user = new User();
		for (int j = 0; j < 10; j++) {
			for(long i=0;i<1000;i++){
				Long id = Long.valueOf(f.format(new Date())+random.nextInt(10)+random.nextInt(10)+random.nextInt(10)+random.nextInt(10));
				user.setId(id);
				user.setName("Robert"+j);
				user.setSex(0);
	        	userMapper.insert(user);
	        	
	    	}
		}
		Long c = System.currentTimeMillis();
		System.out.println("插入数据end="+c);
		System.out.println("插入数据end=begin="+(c-b));
		return 1;
	}
}
