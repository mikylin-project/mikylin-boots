package cn.mikylin.boot.common.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * 通过环境变量获取到配置文件的值
 *
 * @author mikylin
 * @date 20200228
 */
@Component
public class EnvironmentService {

    @Autowired
    Environment environment;

    public String getValue(String key) {
        return environment.getProperty(key);
    }
}
