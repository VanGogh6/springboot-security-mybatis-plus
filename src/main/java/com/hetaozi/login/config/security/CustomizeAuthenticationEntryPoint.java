package com.hetaozi.login.config.security;

import com.alibaba.fastjson.JSON;
import com.hetaozi.login.common.JsonResult;
import com.hetaozi.login.common.ResultCode;
import com.hetaozi.login.common.ResultTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 吴帆
 * @date 2020/10/11 0011 14:42
 */
@Slf4j
@Component
public class CustomizeAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        JsonResult result = ResultTool.fail(ResultCode.USER_NOT_LOGIN);
        httpServletResponse.setContentType("text/json;charset=utf-8");
        String localAddr = httpServletRequest.getLocalAddr();
        log.info("用户没有登陆,登陆地址为" + localAddr);
        httpServletResponse.getWriter().write(JSON.toJSONString(result));
    }
}
