package com.tyf.community.controller.advice;

import com.tyf.community.entity.Result;
import com.tyf.community.util.CommunityConstant;
import com.tyf.community.util.CommunityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * 用于统一处理Controller异常请求
 */
@ControllerAdvice(annotations = Controller.class)
public class ExceptionAdvice {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);

    @ExceptionHandler({Exception.class})
    public Result handleException(Exception e , HttpServletRequest request, HttpServletResponse response) throws IOException {
          Result res = new Result();
          logger.error("服务器发生异常："+ e.getMessage());
          for(StackTraceElement element : e.getStackTrace()){
              //打印栈中的错误信息
              logger.error(element.toString());
          }
          res.setCode(CommunityConstant.REQUEST_FAILED);
          res.setMsg("服务器异常");
          return res;
    }

}
