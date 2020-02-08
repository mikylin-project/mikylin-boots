package cn.mikylin.boot.service;

import cn.mikylin.boot.dao.master.MasterUserDao;
import cn.mikylin.boot.dao.shardingsphere.ShardingUserDao;
import cn.mikylin.boot.dao.slaver.SlaverUserDao;
import cn.mikylin.boot.entity.UserEntity;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.transaction.annotation.ShardingTransactionType;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
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
    ShardingUserDao shardingUserDao;
    @Resource
    MasterUserDao masterUserDao;
    @Resource
    SlaverUserDao slaverUserDao;

    @ShardingTransactionType(TransactionType.XA) // 分布式事务使用 xa，本地单库事务使用 local
    @Transactional(propagation = Propagation.REQUIRED) // sharding 的事务需要配合 transactional 注解使用
    public void saveUsers(List<UserEntity> us) {

        for(UserEntity u : us) {
            shardingUserDao.insert(u);
        }
    }

    public Long findCount() {
        // PageHelper 内部使用 thread local 进行线程隔离，不会有多线程并发问题
        // 但是同样的，PageHelper 必须和 dao 的方法调用在同一个线程中，如果这里使用了多线程分页就无效了
        // PageHelper 只会生效一次，在执行完下一句 sql 之后，分页数据就会失效了
        PageHelper.startPage(10,10);
        Long masterLong = masterUserDao.selectCount();

        // 使用多线程会使得分页无效
//        new Thread(() -> {
//            Long masterLong = masterUserDao.selectCount();
//        }).start();

        System.out.println(masterLong);
        Long slaverLong = slaverUserDao.selectCount();
        System.out.println(slaverLong);
        return shardingUserDao.selectCount();
    }

    public void findAll() {

        List<UserEntity> mu = masterUserDao.selectAll();
        System.out.println("mu:" + mu.size());
        List<UserEntity> su = slaverUserDao.selectAll();
        System.out.println("su:" + su.size());

        // PageHelper 可以对 sharding jdbc 使用，他在 myabtis 层面就会进行 sql 的分析
        PageHelper.startPage(10,10);
        List<UserEntity> shardingu = shardingUserDao.selectAll();
        System.out.println("shardingu:" + shardingu.size());
    }
}
