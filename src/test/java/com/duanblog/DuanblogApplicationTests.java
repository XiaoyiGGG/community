package com.duanblog;

import com.duanblog.mapper.UserMapper;
import com.duanblog.model.User;
import com.duanblog.model.UserExample;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
@RunWith(SpringRunner.class)
@SpringBootTest
public class DuanblogApplicationTests {
    @Autowired
    private UserMapper userMapper;


    @Test
    public void contextLoads() {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andTokenEqualTo("d44d2e5e-a99f-4772-8a56-f0d6399c2a07");
        List<User> users = userMapper.selectByExample(userExample);
        System.out.println(users);

    }
}
