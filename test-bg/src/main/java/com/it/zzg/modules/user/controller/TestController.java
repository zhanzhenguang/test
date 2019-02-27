package com.it.zzg.modules.user.controller;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.it.zzg.modules.sys.service.RedissonService;




/**
 * 用户
 * 
 * @author admin
 * @email admin
 * @date 2017-10-23 21:23:54
 */
@RestController
@RequestMapping("/wx")
public class TestController {
	private static final Logger log = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private RedissonService redissonService;

    @RequestMapping(value = "/test")
    @ResponseBody
    public void test(String recordId) {

        RLock lock = redissonService.getRLock(recordId);
        try {
            boolean bs = lock.tryLock(5, 6, TimeUnit.SECONDS);
            if (bs) {
                // 业务代码
                log.info("进入业务代码: " + recordId);

                lock.unlock();
            } else {
                Thread.sleep(300);
            }
        } catch (Exception e) {
            log.error("", e);
            lock.unlock();
        }
    }
	
}
