package com.tyf.community.service;

import com.tyf.community.dao.CircleDao;
import com.tyf.community.dao.UserCircleMapper;
import com.tyf.community.entity.Circle;
import com.tyf.community.entity.Result;
import com.tyf.community.entity.User;
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
        res.setData(circles);
        res.setCode("0");
        return res;
    }

    public Result getCirclesByManager(String manager) {
        Result res = new Result();
        List<Circle> circles = circleDao.selectCircleByManager(manager);
        res.setData(circles);
        res.setCode("0");
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
        res.setCode("0");
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
        res.setCode("0");
        return res;
    }

    public int addCircle(String userNum, Integer circleId){
        return userCircleMapper.insertUserCircle(userNum, circleId);
    }
}
