package com.tyf.community.controller;

import com.tyf.community.dto.CircleDto;
import com.tyf.community.entity.Circle;
import com.tyf.community.entity.Result;
import com.tyf.community.service.CircleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Api(value = "/circle", tags = "社交圈")
@Controller
@RequestMapping("/circle")
public class CircleController {
    @Autowired
    private CircleService circleService;

    @ApiOperation(value = "添加社交圈接口")
    @RequestMapping(path = "/addCircle", method = RequestMethod.POST)
    @ResponseBody
    public int addCircle(@RequestBody CircleDto circleDto){
        Circle circle = new Circle();
        circle.setName(circleDto.getName());
        circle.setManager(circleDto.getManager());
        return circleService.addCircle(circle);
    }

    @ApiOperation(value = "根据名称查询社交圈接口")
    @RequestMapping(path = "/searchCircleByName", method = RequestMethod.POST)
    @ResponseBody
    public Result searchCircleByName(@RequestParam("name") String name){
        return circleService.searchCircleByName(name);
    }

    @ApiOperation(value = "获取用户管理的社交圈接口")
    @RequestMapping(path = "/getCirclesByManager", method = RequestMethod.POST)
    @ResponseBody
    public Result getCirclesByManager(@RequestParam("manager") String manager){
        return circleService.getCirclesByManager(manager);
    }
}
