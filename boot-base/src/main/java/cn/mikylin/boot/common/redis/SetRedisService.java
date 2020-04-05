package cn.mikylin.boot.common.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;

/**
 * 基于 Set 类型的 k-v 的 redis template 封装
 *
 * @author mikylin
 * @date 20200313
 */
@Component
public class SetRedisService extends StringRedisBaseService {

    @Autowired
    StringRedisTemplate redis;

    private SetOperations<String,String> opsForSet() {
        return redis.opsForSet();
    }

    @Override
    protected StringRedisTemplate redis() {
        return redis;
    }

    /**
     * 获取整个 set。
     */
    public Set<String> getAll(String key) {
        return opsForSet().members(key);
    }


    /**
     * 插入一个 set 元素。
     * true - 插入成功
     * false - 执行错误，或者这个元素已经存在于 Set 中了，所以插入失败
     */
    public boolean add(String key,String... values) {
        return values == null || values.length == 0  ?
                false : Objects.equals(opsForSet().add(key,values),1L);
    }

    /**
     * 获取元素数量。
     */
    public Long size(String key) {
        return opsForSet().size(key);
    }



}
