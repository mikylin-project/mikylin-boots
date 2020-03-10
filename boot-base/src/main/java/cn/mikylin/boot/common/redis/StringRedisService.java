package cn.mikylin.boot.common.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;
import java.util.concurrent.TimeUnit;

/**
 * 基于 String 类型的 k-v 的 redis template 封装
 *
 * @author mikylin
 * @date 20200309
 */
@Component
public class StringRedisService extends RedisBaseService {

    @Autowired
    @Qualifier("redisTemplate-1")
    RedisTemplate<String,Object> redis;

    private ValueOperations<String,Object> opsForValue() {
        return redis.opsForValue();
    }

    @Override
    protected RedisTemplate<String, Object> redis() {
        return redis;
    }

    /**
     * 插入一个 k-v，原子化操作。
     * 如果不存在这个 key 就新增键值对，返回 true；
     * 如果存在这个 key 就不做任何操作，返回 false。
     */
    public Boolean setIfAbsent(String key,Object value,long expire,TimeUnit unit) {
        return  expire <= 0L ?
                opsForValue().setIfAbsent(key,value) :
                opsForValue().setIfAbsent(key,value,expire,unit);
    }

    public Boolean setIfAbsent(String key,Object value) {
        return setIfAbsent(key,value,-1L,null);
    }

    /**
     * 插入一个 k-v。
     */
    public void set(String key,Object value,long expire,TimeUnit unit) {
        if(expire <= 0L)
            opsForValue().set(key,value);
        opsForValue().set(key,value,expire,unit);
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
                opsForValue().setIfPresent(key,value) :
                opsForValue().setIfPresent(key,value,expire,unit);
    }

    public Boolean setIfPresent(String key, String value) {
        return setIfPresent(key,value,-1L,null);
    }


    /**
     * 根据 key 获取 value
     */
    public Object get(String key) {
        return opsForValue().get(key);
    }

    /**
     * 获取旧值，并替换成一个新值
     */
    public Object getAndSet(String key,Object value) {
        return opsForValue().getAndSet(key,value);
    }


    public Integer append(String key,String other) {
        return opsForValue().append(key,other);
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


}
