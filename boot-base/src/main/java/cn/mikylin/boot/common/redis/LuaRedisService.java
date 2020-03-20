package cn.mikylin.boot.common.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;

@Component
public class LuaRedisService {

    @Autowired
    @Qualifier("redisTemplate-1")
    StringRedisTemplate redis;

    private String scriptPath = "redisScript.lua";
    private String script;

    private String getScript() {

        if (script == null) {
            synchronized (this) {
                if (script == null) {
                    RedisConnection conn = redis.getConnectionFactory().getConnection();
                    try {
                        script = conn.scriptLoad(incrRedisScript());
                    } finally {
                        conn.close();
                    }
                }
            }
        }
        return script;
    }

    private byte[] incrRedisScript() {
        DefaultRedisScript<Object> defaultRedisScript = new DefaultRedisScript<>();
        defaultRedisScript.setResultType(Object.class);
        defaultRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource(scriptPath)));
        String scriptString = defaultRedisScript.getScriptAsString();
        return scriptString.getBytes();
    }



}
