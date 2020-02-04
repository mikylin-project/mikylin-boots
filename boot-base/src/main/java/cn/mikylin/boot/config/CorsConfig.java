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
