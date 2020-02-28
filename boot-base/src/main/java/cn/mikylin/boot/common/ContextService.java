package cn.mikylin.boot.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 获取 spring 容器
 *
 * @author mikylin
 * @date 20200228
 */
@Component
public class ContextService implements ApplicationContextAware {

    private ApplicationContext context = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(context == null)
            context = applicationContext;
    }

    public ApplicationContext getContext() {
        return context;
    }
}
