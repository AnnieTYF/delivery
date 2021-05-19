package com.tyf.community.controller;

import com.tyf.community.dto.TaskDto;
import com.tyf.community.entity.Result;
import com.tyf.community.entity.Task;
import com.tyf.community.entity.User;
import com.tyf.community.service.TaskService;
import com.tyf.community.util.CommunityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Api(value = "/task", tags = "任务")
@CrossOrigin(origins = "", maxAge = 3600)
@Controller
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @ApiOperation(value = "添加任务接口")
    @RequestMapping(path = "/addTask", method = RequestMethod.POST)
    @ResponseBody
    public int addTask(@RequestBody TaskDto taskDto) {
        Task task = new Task();
        task.setUserPost(taskDto.getUserPost());
        task.setTitle(taskDto.getTitle());
        task.setContent(taskDto.getContent());
        task.setStatus(0);
        task.setCircleId(taskDto.getCircleId());
        return taskService.addTask(task);
    }

    @ApiOperation(value = "获取单个任务信息接口")
    @RequestMapping(path = "/getSingleTask", method = RequestMethod.POST)
    @ResponseBody
    public Result getSingleTask(@RequestParam("taskId") Integer taskId){
        return taskService.getSingleTask(taskId);
    }

    @ApiOperation(value = "获取某个社交圈下的所有任务信息接口")
    @RequestMapping(path = "/getTasksByCircle", method = RequestMethod.POST)
    @ResponseBody
    public Result getTasksByCircle(@RequestParam("circleId") Integer circleId){
        return taskService.getTasksByCircle(circleId);
    }

    @ApiOperation(value = "获取用户发布的所有任务接口")
    @RequestMapping(path = "/getTaskByUserPost", method = RequestMethod.POST)
    @ResponseBody
    public Result getTaskByUserPost(@RequestParam("userPost") String userPost){
        return taskService.getTaskByUserPost(userPost);
    }

    @ApiOperation(value = "获取用户接受的所有任务接口")
    @RequestMapping(path = "/getTaskByUserGet", method = RequestMethod.POST)
    @ResponseBody
    public Result getTaskByUserGet(@RequestParam("userGet")String userGet){
        return taskService.getTaskByUserGet(userGet);
    }

    @ApiOperation(value = "用户接受任务接口")
    @RequestMapping(path = "/acceptTask", method = RequestMethod.POST)
    @ResponseBody
    public Result acceptTask(@RequestParam("taskId") Integer taskId,@RequestParam("userGet") String userGet){
        return taskService.acceptTask(taskId, userGet);
    }

    @ApiOperation(value = "用户删除任务接口")
    @RequestMapping(path = "/deleteTask", method = RequestMethod.POST)
    @ResponseBody
    public int deleteTask(@RequestParam("taskId") Integer taskId){
        return taskService.deleteTask(taskId);
    }

    @ApiOperation(value = "用户完成任务接口")
    @RequestMapping(path = "/completeTask", method = RequestMethod.POST)
    @ResponseBody
    public int completeTask(@RequestParam("taskId") Integer taskId){
        return taskService.completeTask(taskId);
    }

    @ApiOperation(value = "编辑任务信息接口")
    @RequestMapping(path = "/updateTaskInfo", method = RequestMethod.POST)
    @ResponseBody
    public Result updateTaskInfo(@RequestBody TaskDto taskDto){
        Task task = new Task();
        task.setId(taskDto.getId());
        task.setTitle(taskDto.getTitle());
        task.setContent(taskDto.getContent());
        return taskService.updateTaskInfo(task);
    }
}
