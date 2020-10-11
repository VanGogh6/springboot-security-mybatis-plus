package com.hetaozi.login.config.security;

import com.alibaba.fastjson.JSON;
import com.hetaozi.login.common.JsonResult;
import com.hetaozi.login.common.ResultCode;
import com.hetaozi.login.common.ResultTool;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 会话失效(账号被挤下线)处理逻辑
 *
 * @author 吴帆
 * @date 2020/10/11 0011 19:54
 */
@Component
public class CustomizeSessionInformationExpiredStrategy implements SessionInformationExpiredStrategy {
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent sessionInformationExpiredEvent) throws IOException, ServletException {
        JsonResult result = ResultTool.fail(ResultCode.USER_ACCOUNT_USE_BY_OTHERS);
        HttpServletResponse httpServletResponse = sessionInformationExpiredEvent.getResponse();
        httpServletResponse.setContentType("text/json;charset=utf-8");
        httpServletResponse.getWriter().write(JSON.toJSONString(result));
    }
}
