package cn.mikylin.boot.common.spring.extension;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * Spring 扩展点之一：BeanPostProcessor。
 * 通过实现这个接口，可以往容器中注册拦截器，
 * 在 bean 的初始化前后对 bean 进行增强
 *
 * @author mikylin
 * @date 20200324
 */
@Component
@Slf4j
public class BeanPostProcessorImpl implements BeanPostProcessor {

    /**
     * 某个 bean 准备被初始化的时候
     */
    @Nullable @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
//        log.info("init before " + beanName);
        return bean;
    }

    /**
     * 某个 bean 初始化完成的时候
     */
    @Nullable @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//        log.info("init after " + beanName);
        return bean;
    }
}
