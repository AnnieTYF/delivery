package com.tyf.community.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.tyf.community.annotation.LoginRequired;
import com.tyf.community.entity.Result;
import com.tyf.community.entity.User;
import com.tyf.community.service.FollowService;
import com.tyf.community.service.LikeService;
import com.tyf.community.service.UserService;
import com.tyf.community.util.CommunityConstant;
import com.tyf.community.util.CommunityUtil;
import com.tyf.community.util.HostHolder;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController implements CommunityConstant {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Value("${community.path.upload}")
    private String uploadPath;

    @Value("${community.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Autowired
    private UserService userService;

    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private LikeService likeService;
    @Autowired
    private FollowService followService;


    @LoginRequired
    @RequestMapping(path = "/setting", method = RequestMethod.GET)
    public String getSettingPage(){
        return "/site/setting";
    }

    /**
     * 上传图片接口
     * @param headerImage
     * @return
     */
    @ApiOperation(value = "上传图片接口")
    @LoginRequired
    @RequestMapping(path = "/upload",method = RequestMethod.POST)
    @ResponseBody
    public Result uploadHeader(MultipartFile headerImage, String studentNum){
        Result res = new Result();
         if(headerImage == null){
             res.setMsg("未选择图片");
             return res;
         }

         String filename = headerImage.getOriginalFilename();
         String suffix = filename.substring(filename.lastIndexOf("."));
         if(StringUtils.isBlank(suffix)){
             res.setMsg("文件的格式不正确");
             return res;
         }
         //生成随机文件名
          filename = CommunityUtil.generateUUID()+ suffix;
         //确定文件存放路径
        File file = new File(uploadPath + "/" + filename);
        try {
            headerImage.transferTo(file);
        } catch (IOException e) {
            logger.error("上传文件失败: " + e.getMessage());
            throw new RuntimeException("上传文件失败,服务器发生异常", e);
        }
        // 更新当前用户的头像的路径(web访问路径)
        // http://localhost:8080/community/user/header/xxx.png
        String headerUrl = domain + contextPath + "/user/header/" + filename;
        userService.updateHeader(studentNum, headerUrl);
        res.setData(headerUrl);
        res.setCode("0");
        return res;
    }

    /**
     * 获取图片接口
     * @param fileName
     * @param response
     */
    @RequestMapping(path = "/header/{fileName}",method = RequestMethod.GET)
    public void getHeader(@PathVariable("fileName")String fileName, HttpServletResponse response){
        //服务器存放路径
        fileName = uploadPath + "/" + fileName;
        // 文件后缀
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        //响应图片
        response.setContentType("image/" + suffix);

        //这是java7的一个特殊语法，用来自动关闭输入流，输出流由springmvc控制关闭
        //当然也可以选择在finally里关闭，不过这样需要 FileInputStream在异常外定义
        try(  //文件的输出流
              OutputStream outputStream = response.getOutputStream();
              //文件的输入流
              FileInputStream fileInputStream = new FileInputStream(fileName);)
        {

            byte[] buffer = new byte[1024];
            int b = 0;
            while ((b = fileInputStream.read(buffer)) != -1){
                outputStream.write(buffer,0,b);
            }

        } catch (IOException e) {
            logger.error("读取头像失败:" + e.getMessage());
        }finally {

        }

    }

    //个人主页
    @ApiOperation(value = "修改个人主页接口")
    @RequestMapping(path = "/updateProfile", method = RequestMethod.POST)
    @ResponseBody
    public Result updateProfile(@RequestBody Map<String,Object> data){
        String stuNum = (String) data.get("studentNumber");
        String username = (String) data.get("username");
        String phone = (String) data.get("phone");
        User user = new User();
        user.setStudentNumber(stuNum);
        user.setUsername(username);
        user.setPhone(phone);
        return userService.updateUserInfo(user);
    }

}
