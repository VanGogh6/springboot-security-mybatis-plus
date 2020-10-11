package com.hetaozi.login.config.security;

import com.alibaba.fastjson.JSON;
import com.hetaozi.login.common.JsonResult;
import com.hetaozi.login.common.ResultCode;
import com.hetaozi.login.common.ResultTool;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 权限拒绝处理逻辑
 *
 * @author 吴帆
 * @date 2020/10/11 0011 20:27
 */
@Service
public class CustomizeAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        JsonResult result = ResultTool.fail(ResultCode.NO_PERMISSION);
        httpServletResponse.setContentType("text/json;charset=utf-8");
        httpServletResponse.getWriter().write(JSON.toJSONString(result));
    }
}
