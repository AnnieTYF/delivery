package com.tyf.community.service;

import com.tyf.community.dao.UserMapper;
import com.tyf.community.entity.Result;
import com.tyf.community.entity.User;
import com.tyf.community.util.CommunityConstant;
import com.tyf.community.util.CommunityUtil;
import com.tyf.community.util.RedisKeyUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


@Service
public class UserService implements CommunityConstant {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${community.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    public User findUserByStuNum(String stuNum){
        return userMapper.selectByStudentNumber(stuNum);
    }

    public Map<String,Object> register(User user){
        Map<String,Object> map = new HashMap<>();
        //控制处理
        if(user == null){
            map.put("msg","参数不能为空");
            return map;
        }
        if(StringUtils.isBlank(user.getStudentNumber())){
             map.put("msg","账号不能为空");
             return map;
        }
        if(StringUtils.isBlank(user.getPassword())){
            map.put("msg","密码不能为空");
            return map;
        }
        // 验证账号
        User u = userMapper.selectByStudentNumber(user.getStudentNumber());
        if (u != null) {
            map.put("msg", "该账号已存在!");
            return map;
        }
        // 注册用户
        user.setSalt(CommunityUtil.generateUUID().substring(0,5));
        user.setPassword(CommunityUtil.md5(user.getPassword() + user.getSalt()));
        user.setStatus(0);
        user.setHeaderUrl(String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));
        userMapper.insertUser(user);
        return map;
    }
    /**
     * 登录验证
     * @param password
     * @return
     */
    public  Result login(String studentNumber, String password) {
        Result res = new Result();
        // 空值处理
        if (StringUtils.isBlank(studentNumber)) {
            res.setMsg("账号不能为空!");
            return res;
        }
        if (StringUtils.isBlank(password)) {
            res.setMsg("密码不能为空!");
            return res;
        }
        // 验证账号
        User user = userMapper.selectByStudentNumber(studentNumber);
        if (user == null) {
           res.setMsg("该账号不存在!");
            return res;
        }
        // 验证密码
        password = CommunityUtil.md5(password + user.getSalt());
        System.out.println(password);
        if (!user.getPassword().equals(password)) {
            res.setMsg("密码不正确!");
            return res;
        }
        res.setData(user);
        res.setCode("0");
        return res;
    }

    public void logout(String sessionId) {
        try{
            redisTemplate.delete(sessionId);
        }catch (Exception e){
            logger.error("redis delete user failed");
        }

    }

    //更新用户头像路径
    public int updateHeader(String stuNum, String headerUrl){
        return userMapper.updateHeader(stuNum,headerUrl);
    }

    /**
     * 1.优先从缓存中取值
     */
    private String getCache(String sessionId){
        return (String)redisTemplate.opsForValue().get(sessionId);
    }



}
