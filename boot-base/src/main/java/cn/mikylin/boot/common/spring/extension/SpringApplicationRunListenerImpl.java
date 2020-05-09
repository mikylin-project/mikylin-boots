package cn.mikylin.boot.common.spring.extension;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * spring boot 扩展点之一：SpringApplicationRunListener。
 * 通过实现这个接口，可以监控 SpringBoot 容器的整个生命周期
 *
 * @author mikylin
 * @date 20200325
 */
@Slf4j
public class SpringApplicationRunListenerImpl implements SpringApplicationRunListener {

    public SpringApplicationRunListenerImpl(SpringApplication application, String[]  args) {
        System.out.println("constructor");
    }

    /**
     * boot 正在启动的时候
     */
    @Override
    public void starting() {
        log.info("starting");
    }

    /**
     * 当 Environment 加载完成，但 ApplicationContext 还未被构建时调用
     */
    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {
        log.info("environmentPrepared");
    }

    /**
     * 当 ApplicationContext 构建完成时，该方法被调用
     */
    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        log.info("contextPrepared");
    }

    /**
     * boot 的环境变量加载完成，也就是指的配置文件被读取完成了
     */
    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
    }

    /**
     * boot 的环境变量加载完成，也就是指的配置文件被读取完成了
     */
    @Override
    public void started(ConfigurableApplicationContext context) {
        log.info("started");
    }

    /**
     * boot 的环境变量加载完成，也就是指的配置文件被读取完成了
     */
    @Override
    public void running(ConfigurableApplicationContext context) {
        log.info("running");
    }

    /**
     * boot 的环境变量加载完成，也就是指的配置文件被读取完成了
     */
    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        log.info("failed");
    }
}
