package com.tyf.community.controller;

import com.tyf.community.dto.TaskDto;
import com.tyf.community.entity.DiscussPost;
import com.tyf.community.entity.Result;
import com.tyf.community.entity.Task;
import com.tyf.community.entity.User;
import com.tyf.community.service.TaskService;
import com.tyf.community.util.CommunityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Api(value = "/task", tags = "任务")
@Controller
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @ApiOperation(value = "添加任务接口")
    @RequestMapping(path = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Result addDiscussPost(@RequestBody TaskDto taskDto) {
        Task task = new Task();
        task.setUserPost(taskDto.getUserPost());
        task.setTitle(taskDto.getTitle());
        task.setContent(taskDto.getContent());
        task.setStatus(0);
        return taskService.addTask(task);
    }
}
