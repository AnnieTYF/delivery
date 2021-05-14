package com.tyf.community.controller;

import com.tyf.community.entity.Comment;
import com.tyf.community.entity.DiscussPost;
import com.tyf.community.entity.Event;
import com.tyf.community.service.CommentService;
import com.tyf.community.service.DiscussPostService;
import com.tyf.community.util.CommunityConstant;
import com.tyf.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

@Controller
@RequestMapping("/comment")
public class CommentController implements CommunityConstant {
    @Autowired
    private CommentService commentService;
    //为了得到当前用户的id
    @Autowired
    private HostHolder hostHolder;
    /*@Autowired
    private EventProducer eventProducer;
*/
    @Autowired
    private DiscussPostService discussPostService;

    @RequestMapping(path = "/add/{discussPostId}",method = RequestMethod.POST)
    private String addComment(@PathVariable("discussPostId") int discussPostId, Comment comment){
        comment.setUserId(hostHolder.getUser().getId());
        comment.setStatus(0);
        comment.setCreateTime(new Date());
        commentService.addComment(comment);

       /* //触发评论事件，之所以可以这么写是因为我们改变了event的set方法，使其由原来的void返回Event
        Event event = new Event()
                .setTopic(TOPIC_COMMENT)
                .setUserId(hostHolder.getUser().getId())
                .setEntityType(comment.getEntityType())
                .setEntityId(comment.getEntityId())
                .setData("postId", discussPostId); //点击查看
        //给帖子作评论
        if (comment.getEntityType() == ENTITY_TYPE_POST) {
            DiscussPost target = discussPostService.findDiscussPostById(comment.getEntityId());
            event.setEntityUserId(target.getUserId());
        } else if (comment.getEntityType() == ENTITY_TYPE_COMMENT) {
            //评论的评论
            Comment target = commentService.findCommentById(comment.getEntityId());
            event.setEntityUserId(target.getUserId());
        }
        eventProducer.fireEvent(event);*/

        return "redirect:/discuss/detail/" + discussPostId;
    }

}
