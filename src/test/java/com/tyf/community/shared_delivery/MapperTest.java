package com.tyf.community.shared_delivery;

import com.tyf.community.CommunityApplication;
import com.tyf.community.dao.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class MapperTest {

//   @Autowired
//    private UserMapper userMapper;
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private CircleDao circleDao;
//
//    @Autowired
//    private MessageMapper messageMapper;
//    @Autowired
//    private CommentMapper commentMapper;
//    @Autowired
//    private DiscussPostService discussPostService;

//    @Test
//    public void testInsert(){
//
//       // User user = new User("31170056898","小A","123","123","12345678901",0,"http://static.nowcoder.com/images/head/notify.png");
//
//        System.out.println(userMapper.updatePassword("31170056898","321"));
//     //    System.out.println(userMapper.selectByStudentNumber("31170056898"));
//    }

//    @Test
//    public void testTask(){
////        Task task = new Task();
////        task.setUserPost("31170056898");
////        task.setTitle("代领快递！");
////        task.setContent("送达地点：广东工业大学西区，急需同学帮忙在广工西区丰巢快递4号柜领取快递，具体私聊！我的微信: 123456");
////        task.setStatus(0);
////        taskMapper.insertTask(task);
//        System.out.println(taskMapper.selectTaskById(3));
//    }
    @Test
      public void testCircle(){
//        Circle circle = new Circle();
//        circle.setName("广东工业大学");
//        circle.setManager("31170056898");
//        circleDao.insertCircle(circle);
        System.out.println(circleDao.selectCircleByName("广东"));
      }
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
