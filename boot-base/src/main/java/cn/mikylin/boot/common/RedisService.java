package cn.mikylin.boot.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * redis template 封装
 *
 * @author mikylin
 * @date 20200309
 */
@Component
public class RedisService {

    @Autowired @Qualifier("redisTemplate-1")
    RedisTemplate<String,Object> redis;

    /**
     * 加锁，默认不需要 value，过期时间单位毫秒
     */
    public Boolean tryLock(String key,long expire) {
        return setIfAbsent(key,"",expire,TimeUnit.MILLISECONDS);
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
        return delete(key);
    }

    /**
     * 插入一个 k-v，原子化操作。
     * 如果不存在这个 key 就新增键值对，返回 true；
     * 如果存在这个 key 就不做任何操作，返回 false。
     */
    public Boolean setIfAbsent(String key,Object value,long expire,TimeUnit unit) {
        return  expire <= 0L ?
                redis.opsForValue().setIfAbsent(key,value) :
                redis.opsForValue().setIfAbsent(key,value,expire,unit);
    }

    public Boolean setIfAbsent(String key,Object value) {
        return setIfAbsent(key,value,-1L,null);
    }

    /**
     * 插入一个 k-v。
     */
    public void set(String key,Object value,long expire,TimeUnit unit) {
        if(expire <= 0L)
            redis.opsForValue().set(key,value);
        redis.opsForValue().set(key,value,expire,unit);
    }

    public void set(String key, String value) {
        set(key,value,-1L,null);
    }

    /**
     * 插入一个 k-v，原子化操作。
     * 如果不存在这个 key 就不做任何操作，返回 false；
     * 如果存在这个 key 且 value 就是期望值，不做任何操作并返回 false；
     * 如果存在这个 key 且 value 不为期望值，做更新操作并返回 true。
     */
    public Boolean setIfPresent(String key,Object value,long expire,TimeUnit unit) {
        return  expire <= 0L ?
                redis.opsForValue().setIfPresent(key,value) :
                redis.opsForValue().setIfPresent(key,value,expire,unit);
    }

    public Boolean setIfPresent(String key, String value) {
        return setIfPresent(key,value,-1L,null);
    }


    /**
     * 根据 key 获取 value
     */
    public Object get(String key) {
        return redis.opsForValue().get(key);
    }

    /**
     * 获取旧值，并替换成一个新值
     */
    public Object getAndSet(String key,Object value) {
        return redis.opsForValue().getAndSet(key,value);
    }

    /**
     * 给 key 添加过期时间
     */
    public Boolean expire(String key,long expire,TimeUnit unit) {
        return redis.expire(key,expire,unit);
    }

    /**
     * 删除 k-v
     */
    public boolean delete(String key) {
        return redis.delete(key);
    }

    /**
     * 判断一个 key 是否存在
     */
    public Boolean exists(String key) {
        return redis.hasKey(key);
    }

    /**
     * add and get
     * 原子化的新增后并获取值
     */
    public Long incrAndGet(String key,long delta,long expire,TimeUnit unit) {
        RedisAtomicLong entityIdCounter = new RedisAtomicLong(key, redis.getConnectionFactory());
        if(expire >= 0)
            entityIdCounter.expire(expire,unit);
        return entityIdCounter.addAndGet(delta);
    }

    /**
     * decr ad get
     * 原子化的减少后并获取值
     **/
    public Long decrAndGet(String key,long delta,long expire,TimeUnit unit) {
        return incrAndGet(key,- delta,expire,unit);
    }

    /**
     * 批量化获取 value
     */
    public Map<String,Object> batchGet(List<String> keys) {

        if (keys == null || keys.isEmpty()) {
            return new HashMap<>(1);
        }

        List<Object> values = redis.opsForValue().multiGet(keys);

        HashMap<String,Object> resMap = new HashMap<>(keys.size());
        for (int i = 0 ; i < values.size() ; i++)
            resMap.put(keys.get(i), values.get(i));
        return resMap;
    }
}
