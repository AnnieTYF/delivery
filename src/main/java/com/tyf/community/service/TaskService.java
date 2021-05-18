package com.tyf.community.service;

import com.tyf.community.controller.UserController;
import com.tyf.community.dao.TaskMapper;
import com.tyf.community.entity.DiscussPost;
import com.tyf.community.entity.Result;
import com.tyf.community.entity.Task;
import com.tyf.community.util.CommunityConstant;
import com.tyf.community.util.SensitiveFilter;
import io.swagger.annotations.ApiModel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskMapper taskMapper;

    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

    @Autowired
    private SensitiveFilter sensitiveFilter;

    public int addTask(Task task){
        if(task == null){
            throw new IllegalArgumentException("参数不能为空");
        }
        Result res = new Result();
        //过滤html标签
        task.setTitle(HtmlUtils.htmlEscape(task.getTitle()));
        task.setContent(HtmlUtils.htmlEscape(task.getContent()));
        //过滤敏感词
        task.setTitle(sensitiveFilter.filter(task.getTitle()));
        task.setContent(sensitiveFilter.filter(task.getContent()));
        return taskMapper.insertTask(task);
    }

    public Result getSingleTask(Integer taskId){
        Result res = new Result();
        Task task = taskMapper.selectTaskById(taskId);
        res.setData(task);
        res.setCode("0");
        return res;
    }

    public Result getTasksByCircle(Integer circleId){
        Result res = new Result();
        List<Task> tasks = taskMapper.selectTasksByCircle(circleId);
        res.setData(tasks);
        res.setCode("0");
        return res;
    }

    public Result getTaskByUserPost(String userPost){
        Result res = new Result();
        List<Task> tasks = taskMapper.selectTasksByUserPost(userPost);
        res.setData(tasks);
        res.setCode("0");
        return res;
    }

    public Result getTaskByUserGet(String userGet){
        Result res = new Result();
        List<Task> tasks = taskMapper.selectTasksByUserGet(userGet);
        res.setData(tasks);
        res.setCode("0");
        return res;
    }

    public Result acceptTask(Integer taskId,String userGet){
        Result res = new Result();
        taskMapper.updateTaskStatus(taskId, CommunityConstant.TASK_ACCEPTED);
        taskMapper.updateTaskUserGet(taskId,userGet);
        res.setCode("0");
        res.setMsg("接受任务成功");
        return res;
    }

    public int deleteTask(Integer taskId){
        return taskMapper.updateTaskStatus(taskId,CommunityConstant.TASK_DELETED);
    }

    public int completeTask(Integer taskId){
        return taskMapper.updateTaskStatus(taskId,CommunityConstant.TASK_DONE);
    }

    public Result updateTaskInfo(Task task){
        Result res = new Result();
        if(StringUtils.isBlank(task.getTitle())){
            taskMapper.updateTaskTitle(task.getId(),task.getTitle());
        }
        if(StringUtils.isBlank(task.getContent())){
            taskMapper.updateTaskContent(task.getId(),task.getContent());
        }
        res.setCode("0");
        return res;
    }

}
