package com.hetaozi.login.controller;

import cn.hutool.crypto.SecureUtil;
import com.hetaozi.login.entity.SysUser;
import com.hetaozi.login.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 吴帆
 * @date 2020/9/10 0010 22:29
 */
@RestController
@Slf4j
public class UserController {

    @Autowired
    private SysUserService sysUserServiceImpl;

    @PostMapping("/insert")
    public String insertUser(SysUser user) {
        log.info("未加密的用户:" + user);
        String userPassword = user.getPassword();
        //采用hutool的MD5加密方式加密数据库密码
        String password = SecureUtil.md5(userPassword);
        user.setPassword(password);
        log.info("加密的用户:" + user);
        boolean save = sysUserServiceImpl.save(user);
        return "ok" + save;
    }
}
