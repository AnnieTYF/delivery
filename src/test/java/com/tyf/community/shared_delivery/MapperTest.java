package com.tyf.community.shared_delivery;

import com.tyf.community.CommunityApplication;
import com.tyf.community.dao.CommentMapper;
import com.tyf.community.dao.DiscussPostMapper;
import com.tyf.community.dao.MessageMapper;
import com.tyf.community.dao.UserMapper;
import com.tyf.community.entity.Message;
import com.tyf.community.entity.User;
import com.tyf.community.service.DiscussPostService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class MapperTest {

   @Autowired
    private UserMapper userMapper;
//    @Autowired
//     private DiscussPostMapper discussPostMapper;
//
//    @Autowired
//    private MessageMapper messageMapper;
//    @Autowired
//    private CommentMapper commentMapper;
//    @Autowired
//    private DiscussPostService discussPostService;

    @Test
    public void testInsert(){

       // User user = new User("31170056898","小A","123","123","12345678901",0,"http://static.nowcoder.com/images/head/notify.png");

        System.out.println(userMapper.updatePassword("31170056898","321"));
     //    System.out.println(userMapper.selectByStudentNumber("31170056898"));
    }

//    @Test
//    public void testDiscussPost(){
//        int count = commentMapper.selectCountByEntity(1,275);
//        System.out.println(count);
//        discussPostService.updateCommentCount(275,count);
//       /* System.out.println(discussPostMapper.selectDiscussPostRows(0));
//        System.out.println(discussPostMapper.selectDiscussPosts(0,1,2));*/
//    }
//
//    @Test
//    public void testSelectLetters() {
//        List<Message> list = messageMapper.selectConversations(111, 0, 20);
//        for (Message message : list) {
//            System.out.println(message);
//        }
//
//        int count = messageMapper.selectConversationCount(111);
//        System.out.println(count);
//
//        list = messageMapper.selectLetters("111_112", 0, 10);
//        for (Message message : list) {
//            System.out.println(message);
//        }
//
//        count = messageMapper.selectLetterCount("111_112");
//        System.out.println(count);
//
//        count = messageMapper.selectLetterUnreadCount(131, "111_131");
//        System.out.println(count);
//
//    }


}
