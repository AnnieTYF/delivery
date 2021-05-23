package com.tyf.community.service;

import com.tyf.community.dao.CircleDao;
import com.tyf.community.dao.UserCircleMapper;
import com.tyf.community.entity.Circle;
import com.tyf.community.entity.Result;
import com.tyf.community.entity.User;
import com.tyf.community.util.CommunityConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CircleService {

    @Autowired
    private CircleDao circleDao;

    @Autowired
    private UserCircleMapper userCircleMapper;

    @Autowired
    private UserService userService;

    public int createCircle(Circle circle){
        circleDao.insertCircle(circle);
        userCircleMapper.insertUserCircle(circle.getManager(),circle.getId());
        return circle.getId();
    }

    public Circle getCircle(Integer id){
        return circleDao.selectCircleById(id);
    }

    public Result searchCircleByName(String name){
        Result res = new Result();
        List<Circle> circles = circleDao.selectCircleByName(name);
        if(circles != null){
            res.setData(circles);
            res.setCode(CommunityConstant.REQUEST_SUCCESS);
        }else{
            res.setCode(CommunityConstant.REQUEST_FAILED);
        }
        return res;
    }

    public Result getCirclesByManager(String manager) {
        Result res = new Result();
        List<Circle> circles = circleDao.selectCircleByManager(manager);
        if(circles != null){
            res.setData(circles);
            res.setCode(CommunityConstant.REQUEST_SUCCESS);
        }else{
            res.setCode(CommunityConstant.REQUEST_FAILED);
        }
        return res;
    }

    public Result getCircleListByUser(String stuNum){
        Result res = new Result();
        List<Integer> circleIdList = userCircleMapper.selectCircleIdByUser(stuNum);
        List<Circle> circleList = new ArrayList<>();
        for(Integer circleId : circleIdList){
            circleList.add(getCircle(circleId));
        }
        res.setData(circleList);
        res.setCode(CommunityConstant.REQUEST_SUCCESS);
        return res;
    }

    public Result getUserListByCircle(Integer circleId){
        Result res = new Result();
        List<String> userNumList = userCircleMapper.selectUserIdByCircleId(circleId);
        List<User> userList = new ArrayList<>();
        for(String userNum : userNumList){
            userList.add(userService.findUserByStuNum(userNum));
        }
        res.setData(userList);
        res.setCode(CommunityConstant.REQUEST_SUCCESS);
        return res;
    }

    public Result addCircle(String userNum, Integer circleId){
        Result res = new Result();
        if(getUserCircle(userNum,circleId).getCode() == 0){
            userCircleMapper.insertUserCircle(userNum, circleId);
            res.setCode(CommunityConstant.REQUEST_SUCCESS);
            res.setMsg("加入成功");
        }else{
            res.setCode(CommunityConstant.REQUEST_FAILED);
            res.setMsg("已经加入过啦");
        }
        return res;
    }

    public Result getUserCircle(String stuNum, Integer circleId){
        Result res = new Result();
        if(userCircleMapper.selectByCircleIdAndUserId(stuNum,circleId)!= null){
            res.setCode(CommunityConstant.REQUEST_SUCCESS);
            res.setMsg("用户已加入");
        }else{
            res.setCode(CommunityConstant.REQUEST_FAILED);
            res.setMsg("用户未加入");
        }
        return res;
    }
}
