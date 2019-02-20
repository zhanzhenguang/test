package com.it.zzg.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.it.zzg.model.TUser;
import com.it.zzg.model.User;
import com.it.zzg.service.TUserService;
import com.it.zzg.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    TUserService tUserService;

    @GetMapping
    public List<User> getList() {
        return userService.selectAll();
    }

    @GetMapping("/{id}")
    public User detail(@PathVariable(name = "id", required = true) long id) {
        return userService.selectByKey(id);
    }

    @PostMapping
    public long create(User user) {
		user.setId(1);
		user.setName("Robert");
		user.setSex(0);
		userService.save(user);
		return 1;
    }

    @GetMapping("/save")
    public long save() {
    	try {
    		return userService.saveUser();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
        
    }
    
    @PutMapping("/{id}")
    public long update(@PathVariable(name = "id", required = true) long id, User user) {
        return userService.updateSelective(user);
    }
}
