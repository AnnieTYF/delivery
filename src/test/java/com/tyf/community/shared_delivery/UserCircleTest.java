package com.tyf.community.shared_delivery;

import com.tyf.community.CommunityApplication;
import com.tyf.community.dao.UserCircleMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class UserCircleTest {

    @Autowired
    private UserCircleMapper mapper;

    @Test
    public void test(){
        System.out.println(1111);
        System.out.println(mapper.selectCircleIdByUser("3119004823"));
    }

}
