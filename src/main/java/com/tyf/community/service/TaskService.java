package com.tyf.community.service;

import com.tyf.community.controller.UserController;
import com.tyf.community.dao.TaskMapper;
import com.tyf.community.entity.DiscussPost;
import com.tyf.community.entity.Result;
import com.tyf.community.entity.Task;
import com.tyf.community.util.SensitiveFilter;
import io.swagger.annotations.ApiModel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

@Service
public class TaskService {
    @Autowired
    private TaskMapper taskMapper;

    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

    @Autowired
    private SensitiveFilter sensitiveFilter;

    public Result addTask(Task task){
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
        try {
            taskMapper.insertTask(task);
            res.setMsg("发布任务成功");
            res.setCode("0");
        }catch (Exception e){
            logger.error("发布任务失败" + e.getMessage());
        }
        return res;
    }

    public Result getTaskByUserPost(String stuNum){
        Result res = new Result();

        return res;
    }

    public Result updateTask(Task task){
        Result res = new Result();
        if(StringUtils.isBlank(task.getTitle())){
            taskMapper.updateTaskTitle(task.getId(),task.getTitle());
        }
        if(StringUtils.isBlank(task.getContent())){
            taskMapper.updateTaskContent(task.getId(),task.getContent());
        }
        return res;
    }

}
