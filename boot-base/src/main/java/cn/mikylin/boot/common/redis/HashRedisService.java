package cn.mikylin.boot.common.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 基于 Hash 类型的 k-v 的 redis template 封装
 *
 * @author mikylin
 * @date 20200310
 */
@Component
public class HashRedisService extends StringRedisBaseService {

    @Autowired
    StringRedisTemplate redis;


    private HashOperations<String,String,Object> opsForHash() {
        return redis.opsForHash();
    }

    @Override
    protected StringRedisTemplate redis() {
        return redis;
    }


    /**
     * 存入一个 hash 类型的 k-v
     */
    public void put(String key,String hashKey,Object hashValue) {
        opsForHash().put(key,hashKey,hashValue);
    }

    /**
     * 取出一个 hash 类型的 k-v
     */
    public Object get(String key,String hashKey) {
        return opsForHash().get(key,hashKey);
    }

    /**
     * 取出整个 hash map
     */
    public Map<String,Object> getAll(String key) {
        return opsForHash().entries(key);
    }

    public Long delete(String key,String... hashKeys) {
        return hashKeys == null || hashKeys.length == 0 ?
                0L : opsForHash().delete(key,hashKeys);
    }

    /**
     * 批量化获取 value
     */
    public List<Object> batchGet(String key, String... hashKeys) {
        return hashKeys == null || hashKeys.length == 0 ?
                new ArrayList<>(1) : opsForHash().multiGet(key, Arrays.asList(hashKeys));
    }



}
