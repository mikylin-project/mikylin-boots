package cn.mikylin.boot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 解决前端跨域问题
 *
 * @author mikylin
 * @date 20200204
 */
@Configuration
public class CorsConfig {

    //允许的 Http 连接类型
    static final String ORIGINS[] = new String[] { "GET", "POST", "PUT", "DELETE" };

    /**
     * 配置 bean 的一些注解
     *
     * @ConditionalOnBean(value = WebMvcConfigurer.class)
     * 当给定的在 bean 存在时,则实例化当前 bean
     * @ConditionalOnMissingBean(value = WebMvcConfigurer.class)
     * 当给定的在 bean 不存在时,则实例化当前 bean
     * @ConditionalOnClass(value = "org.springframework.web.servlet.config.annotation.WebMvcConfigurer")
     * 当给定的类名在类路径上存在，则实例化当前 bean
     * @ConditionalOnMissingClass(value = "org.springframework.web.servlet.config.annotation.WebMvcConfigurer")
     * 当给定的类名在类路径上不存在，则实例化当前 bean
     */

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                //设置所有的路径均允许跨域请求
                registry.addMapping("/**").allowedOrigins("*").allowCredentials(true).allowedMethods(ORIGINS)
                        .maxAge(3600).allowCredentials(true);
            }
        };
    }

}
