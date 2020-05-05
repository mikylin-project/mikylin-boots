package cn.mikylin.boot.dubbo;

import cn.mikylin.boot.entity.UserEntity;
import cn.mikylin.boot.service.DubboUserProviderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

/**
 * dubbo 服务消费者示例
 *
 * @author mikylin
 * @date 20200130
 */
@Slf4j
@Service
public class DubboUserConsumerService {

    /**
     * 注入服务的提供者，并配置 mock 类
     */
    @Reference(version = "${dubbo.version}",
            mock = "cn.mikylin.boot.dubbo.DubboUserProviderServiceMock")
    DubboUserProviderService service;

    public void getUser(Long userId) {
        UserEntity user = service.findUser(userId);
        System.out.println(user);
    }
}
