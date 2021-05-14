package com.tyf.community.controller;

import com.tyf.community.entity.Result;
import com.tyf.community.entity.User;
import com.tyf.community.service.UserService;
import com.tyf.community.util.CommunityConstant;
import com.tyf.community.util.CommunityUtil;
import com.tyf.community.util.RedisKeyUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Controller
public class LoginController implements CommunityConstant {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 获取忘记密码页面
     * @return
     */
    @RequestMapping(path = "/forget",method = RequestMethod.GET)
    public String getForgetPage(){
        return "/site/forget";
    }

    @RequestMapping(path = "register", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> register(@RequestBody Map<String,Object> data){
        Map<String,Object> map = new HashMap<>();
        User user = new User();
        String stuNum = (String) data.get("studentNumber");
        String password = (String) data.get("password");
        String pswConfirm = (String)data.get("pswConfirm");
        if(!password.equals(pswConfirm)){
            map.put("msg","密码不一致，请重新输入");
            return map;
            }
        user.setStudentNumber(stuNum);
        user.setPassword(password);
        return userService.register(user);
    }

    /**
     * 忘记密码功能
     * 这几个参数都是我瞎写的，因为我不知道前端该怎么传进来
     * @param model
     * @param email
     * @param code
     * @param password
     * @return
     */
    @RequestMapping(path = "forgetPassword", method = RequestMethod.POST)
    public String forgetPassword(Model model, String email, String code, String password){
        // Map<String,Object> map = userService.forgetPassword(email,code,password);
        Map<String,Object> map = new HashMap<>();
         if(map == null || map.isEmpty()){
             model.addAttribute("msg","密码修改成功。请重新登录");
             model.addAttribute("target","/index");
             return "/site/operate-result";
         }else{
             model.addAttribute("saltMsg",map.get("saltMsg"));
             model.addAttribute("passwordMsg",map.get("passwordMsg"));
             model.addAttribute("emailMsg",map.get("emailMsg"));
             return "/site/forget";
         }
    }

    /**
     * 登录验证
     * @return
     */
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Result login(@RequestBody Map<String,Object> data, HttpServletRequest request, HttpServletResponse response){
        //账号 + 密码
        String stuNum = (String) data.get("studentNumber");
        String password = (String) data.get("password");
        String sessionId = getCookies(request);
        if(sessionId == null){
            // 生成登录凭证
            sessionId = CommunityUtil.generateUUID();
            Cookie cookie = new Cookie("sessionId",sessionId);
            response.addCookie(cookie);
            try {
                redisTemplate.opsForValue().set(sessionId, stuNum);
            }catch (Exception e){
                logger.error("redis save user failed, [studentNo]="+stuNum);
            }
        }
            return userService.login(stuNum,password);
    }

    public  String getCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("sessionId")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    /**
     * 退出登录
     * @param ticket
     * @return
     */
    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public String logout(@CookieValue("ticket")String ticket){
         userService.logout(ticket);
         return "redirect:/login";
    }





}
