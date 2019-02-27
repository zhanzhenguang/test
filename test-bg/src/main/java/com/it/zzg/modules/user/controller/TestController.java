package com.it.zzg.modules.user.controller;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

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

    @RequestMapping(value = "/getRLock")
    @ResponseBody
    public Boolean getRLock() {	
    	String  recordId = "recordId_111";
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
        return true;
    }
    
    /**
     * redis单点获取锁 线程sleep获取自旋锁 注意retryTimes设置的合理性
     * @param timeout
     * @param sleepTime
     * @param retryTimes
     * @return
     * @throws InterruptedException 
     */
    public Boolean getSpinLock() throws InterruptedException {
        System.out.println("线程" + Thread.currentThread().getName() + "尝试获取锁");
        for (int i = 0; i < 3; i++) {
        	RLock lock = redissonService.getRLock("recordId_112");
        	boolean bs = lock.tryLock(5, 6, TimeUnit.SECONDS);
            if (bs) {
                System.out.println("获取了redis锁的当前线程是 " + Thread.currentThread().getName());
                return true;
            }
            // 自旋操作
            try {
                System.out.println("线程" + Thread.currentThread().getName()  + "占用锁失败，自旋等待结果");
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                continue;
            }finally {
            	lock.unlock();
			}
        }
        // 超过retryTimes之后 说明获取获取锁失败
        log.warn("redis get spinLock failed, key : {}, value : {}");
        return false;
    }

	
}
