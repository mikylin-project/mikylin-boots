package cn.mikylin.boot.dubbo;

import cn.mikylin.boot.entity.UserEntity;
import cn.mikylin.boot.service.DubboUserProviderService;

/**
 * dubbo 的消费者如果获取不到从提供者那边获得的结果，
 * 就会尝试调用 mock 类来获取一个默认结果
 *
 * @author mikylin
 * @date 20200130
 */
public class DubboUserProviderServiceMock implements DubboUserProviderService {

    @Override
    public UserEntity findUser(Long userId) {
        UserEntity u = new UserEntity();
        u.setUserName("test_user_name_mock");
        return u;
    }
}
