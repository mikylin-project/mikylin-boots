package cn.mikylin.boot;

import cn.mikylin.boot.entity.UserEntity;
import cn.mikylin.boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class BaseBootTests {

    @Autowired
    UserService service;

    @Test
    public void saveTest() {
        List<UserEntity> uList = new ArrayList<>();
        for(int i = 0 ; i <= 500 ; i ++) {
            String s = String.valueOf(i);
            UserEntity e = new UserEntity();
            e.setUserId(Long.valueOf(s));
            e.setUserName("name " + s);
            e.setAge(i);
            e.setNotes("note " + s);
            e.setPassword("password " + s);
            e.setSex(1);
            uList.add(e);
        }

        service.saveUsers(uList);
    }

    @Test
    public void selectTest() {
        System.out.println(service.findCount());
    }

    @Test
    public void selectAll() {
        service.findAll();
    }

}
