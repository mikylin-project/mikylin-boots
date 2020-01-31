package cn.mikylin.boot.dubbo;

import cn.mikylin.boot.entity.UserEntity;
import cn.mikylin.boot.service.DubboUserProviderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;

import java.util.concurrent.TimeUnit;

/**
 * dubbo 服务提供者示例
 *
 * @author mikylin
 * @date 20200130
 */
@Slf4j
@Service(version = "${dubbo.version}")
public class DubboUserProviderServiceImpl implements DubboUserProviderService {

    @Override
    public UserEntity findUser(Long userId) {
        UserEntity u = new UserEntity();
        u.setUserName("test_user_name");
        u.setUserId(1L);

//        try {
//            TimeUnit.SECONDS.sleep(100L);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        return u;
    }
}
