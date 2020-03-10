package cn.mikylin.boot.common.redis;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public abstract class RedisBaseService {

    protected abstract RedisTemplate<String,Object> redis();

    /**
     * 给 key 添加过期时间
     */
    public Boolean expire(String key, long expire, TimeUnit unit) {
        return redis().expire(key,expire,unit);
    }

    /**
     * 删除 k-v
     */
    public boolean delete(String key) {
        return redis().delete(key);
    }

    /**
     * 判断一个 key 是否存在
     */
    public Boolean exists(String key) {
        return redis().hasKey(key);
    }


    /**
     * 批量化获取 value
     */
    public Map<String,Object> batchGet(List<String> keys) {

        if (keys == null || keys.isEmpty())
            return new HashMap<>(1);

        HashMap<String,Object> resMap = new HashMap<>(keys.size());

        List<Object> values = redis().opsForValue().multiGet(keys);

        for (int i = 0 ; i < values.size() ; i++)
            resMap.put(keys.get(i), values.get(i));
        return resMap;
    }
}
