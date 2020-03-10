package cn.mikylin.boot.common.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * redis 分布式锁
 */
@Component
public class RedisLockService {

    @Autowired
    StringRedisService service;


    /**
     * 加锁，默认不需要 value，过期时间单位毫秒
     */
    public Boolean tryLock(String key,long expire) {
        return service.setIfAbsent(key,"",expire, TimeUnit.MILLISECONDS);
    }

    /**
     * 限时自旋锁
     */
    public boolean lockLimitTime(String key,long expire,long out) {
        boolean limitTime = out > 0L;
        long beginTime = System.currentTimeMillis();
        for(;!tryLock(key,expire);) {
            if(limitTime && System.currentTimeMillis() - beginTime > out) {
                return false;
            }
            Thread.yield();
        }
        return true;
    }

    /**
     * 非限时自旋锁
     */
    public boolean lockLimitTime(String key,long expire) {
        return lockLimitTime(key,expire,-1L);
    }

    /**
     * 解锁
     */
    public Boolean unlock(String key) {
        return service.delete(key);
    }
}
