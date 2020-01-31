package cn.mikylin.boot.service;

import cn.mikylin.boot.entity.UserEntity;

public interface DubboUserProviderService {

    UserEntity findUser(Long userId);
}
