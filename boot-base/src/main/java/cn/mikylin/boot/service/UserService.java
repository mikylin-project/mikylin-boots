package cn.mikylin.boot.service;

import cn.mikylin.boot.dao.UserDao;
import cn.mikylin.boot.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.transaction.annotation.ShardingTransactionType;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;

/**
 * user service
 *
 * @author mikylin
 * @date 20200131
 */
@Slf4j
@Service
public class UserService {

    @Resource
    UserDao userDao;

    @ShardingTransactionType(TransactionType.XA) // 分布式事务使用 xa，本地单库事务使用 local
    @Transactional // sharding 的事务需要配合 transactional 注解使用
    public void saveUsers(List<UserEntity> us) {

        for(UserEntity u : us) {
            userDao.insert(u);
        }
    }

    public Long findCount() {
        return userDao.selectCount();
    }
}
