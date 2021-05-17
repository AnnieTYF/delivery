package com.tyf.community.service;

import com.tyf.community.dao.CircleDao;
import com.tyf.community.entity.Circle;
import com.tyf.community.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CircleService {

    @Autowired
    private CircleDao circleDao;

    public int addCircle(Circle circle){
        return circleDao.insertCircle(circle);
    }

    public Circle getCircleInfo(Integer id){
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
}
