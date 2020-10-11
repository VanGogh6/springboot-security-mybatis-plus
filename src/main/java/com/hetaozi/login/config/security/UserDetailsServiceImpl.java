package com.hetaozi.login.config.security;

import com.hetaozi.login.entity.SysPermission;
import com.hetaozi.login.entity.SysUser;
import com.hetaozi.login.service.SysPermissionService;
import com.hetaozi.login.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 吴帆
 * @date 2020/10/10 0010 21:04
 */
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysPermissionService sysPermissionService;

    /**
     * 功能: 自定义用户认证的核心逻辑
     * 描述: oadUserByUsername(String username)的参数就是登录时提交的用户名，返回类型是一个叫UserDetails 的接口，需要在这里构造出他的一个实现类User，这是Spring security提供的用户信息实体
     * 参数 [username]
     * 返回值: org.springframework.security.core.userdetails.UserDetails
     * 时间: 21:05
     * 作者: www.hetaozi.com
     **/
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null || "".equals(username)) {
            throw new RuntimeException("用户不能为空");
        }
        //更具用户输入信息查询用户
        SysUser sysUser = sysUserService.getOneUser(username);
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if (sysUser != null) {
            //获取该用户所拥有的权限
            List<SysPermission> sysPermissions = sysPermissionService.selectListByUser(sysUser.getId());
            // 声明用户授权
            sysPermissions.forEach(sysPermission -> {
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(sysPermission.getPermissionCode());
                grantedAuthorities.add(grantedAuthority);
            });
        }
        return new User(sysUser.getAccount(), sysUser.getPassword(), sysUser.getEnabled(), sysUser.getAccountNotExpired(), sysUser.getCredentialsNotExpired(), sysUser.getAccountNotLocked(), grantedAuthorities);
    }
}
