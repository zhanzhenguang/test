package com.it.zzg.modules.utils;

import java.sql.Timestamp;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * <p>
 * 高并发场景下System.currentTimeMillis()的性能问题的优化
 * </p>
 * @author 
 */
public class SystemClock {

    private final long period;

    private final AtomicLong now;

    private SystemClock(long period) {
        this.period = period;
        this.now = new AtomicLong(System.currentTimeMillis());
        scheduleClockUpdating();
    }

    private static SystemClock instance() {
        return InstanceHolder.INSTANCE;
    }

    public static synchronized long now() {
        return instance().currentTimeMillis();
    }
    public static synchronized String stringNow() {
        return String.valueOf(instance().currentTimeMillis());
    }

    public static String nowDate() {
        return new Timestamp(instance().currentTimeMillis()).toString();
    }

    private void scheduleClockUpdating() {

        ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(1, new ThreadFactory() {

            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r, "System Clock");
                thread.setDaemon(true);
                return thread;
            }
        });

        scheduler.scheduleAtFixedRate(new Runnable() {

            @Override
            public void run() {
                now.set(System.currentTimeMillis());
            }
        }, period, period, TimeUnit.MILLISECONDS);
    }

    private long currentTimeMillis() {
        return now.get();
    }

    private static class InstanceHolder {
        public static final SystemClock INSTANCE = new SystemClock(1);
    }
    public static void main(String[] args) {
       /* long start = System.currentTimeMillis();
        for (long i = 0; i < Integer.MAX_VALUE; i++) {
            SystemClock.now();
        }
        long end = System.currentTimeMillis();
        System.out.println("SystemClock Time:" + (end - start) + "毫秒");
        long start2 = System.currentTimeMillis();
        for (long i = 0; i < Integer.MAX_VALUE; i++) {
            System.currentTimeMillis();
        }
        long end2 = System.currentTimeMillis();
        System.out.println("currentTimeMillis Time:" + (end2 - start2) + "毫秒");*/
    }
}
