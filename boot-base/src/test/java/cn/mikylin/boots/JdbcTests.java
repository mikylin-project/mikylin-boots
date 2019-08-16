package cn.mikylin.boots;

import cn.mikylin.boots.dao.UserDao;
import cn.mikylin.boots.entity.UserEntity;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JdbcTests {

    @Autowired
    UserDao userDao;

    @Test
    public void findTest() {
        List<UserEntity> users = userDao.findUsers();
        System.out.println(users.get(0));
    }

    @Test
    public void insertTest(){
        UserEntity e = new UserEntity();
        e.setName("haha");
        e.setAge(11);
        e.setNotes("ccczxczxczx");
        e.setCreatetime(new Date());
        Long key = userDao.insert(e);
        System.out.println(key);
    }
}
