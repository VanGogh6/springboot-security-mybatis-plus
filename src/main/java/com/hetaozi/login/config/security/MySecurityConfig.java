package com.hetaozi.login.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

/**
 * Security主要配置类
 *
 * @author 吴帆
 * @date 2020/9/10 0010 23:39
 */

@EnableWebSecurity
@Configuration
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomizeAuthenticationEntryPoint customAuthenticationEntryPoint;//匿名用户访问无权限资源时的异常处理
    @Autowired
    CustomizeAuthenticationSuccessHandler customizeAuthenticationSuccessHandler;//登录成功处理逻辑
    @Autowired
    CustomizeAuthenticationFailureHandler customizeAuthenticationFailureHandler;//登录失败处理逻辑
    @Autowired
    CustomizeLogoutSuccessHandler customizeLogoutSuccessHandler;//登出成功处理逻辑
    @Autowired
    CustomizeSessionInformationExpiredStrategy customizeSessionInformationExpiredStrategy;//会话失效(账号被挤下线)处理逻辑
    @Autowired
    CustomizeAccessDecisionManager customizeAccessDecisionManager;//决策管理器
    @Autowired
    CustomizeFilterInvocationSecurityMetadataSource customizeFilterInvocationSecurityMetadataSource;//安全元数据源
    @Autowired
    CustomizeAbstractSecurityInterceptor customizeAbstractSecurityInterceptor;//权限拦截器
    @Autowired
    CustomizeAccessDeniedHandler customizeAccessDeniedHandler;//权限拒绝处理逻辑

    /**
     * 功能: 配置认证方式等
     * 描述:
     * 参数 [auth]
     * 返回值: void
     * 时间: 21:02
     * 作者: WuFan
     **/
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());//配置认证方式
    }


    /**
     * 功能: http相关的配置，包括登入登出、异常处理、会话管理等
     * 描述:
     * 参数 [http]
     * 返回值: void
     * 时间: 21:03
     * 作者: WuFan
     **/
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();
        http.authorizeRequests().
                withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setAccessDecisionManager(customizeAccessDecisionManager);//决策管理器
                        o.setSecurityMetadataSource(customizeFilterInvocationSecurityMetadataSource);//安全元数据源
                        return o;
                    }
                }).
                and().logout().//登出
                permitAll().//允许所有用户
                logoutSuccessHandler(customizeLogoutSuccessHandler).//登出成功处理逻辑
                deleteCookies("JSESSIONID").//登出之后删除cookie
                and().formLogin().//登入
                permitAll().//允许所有用户
                successHandler(customizeAuthenticationSuccessHandler).//登录成功处理逻辑
                failureHandler(customizeAuthenticationFailureHandler).//登录失败处理逻辑
                and().exceptionHandling().//异常处理(权限拒绝、登录失效等)
                accessDeniedHandler(customizeAccessDeniedHandler).//权限拒绝处理逻辑
                authenticationEntryPoint(customAuthenticationEntryPoint).//匿名用户访问无权限资源时的异常处理
                and().sessionManagement().//会话管理
                maximumSessions(1).//同一账号同时登录最大用户数
                expiredSessionStrategy(customizeSessionInformationExpiredStrategy);//会话失效(账号被挤下线)处理逻辑
        http.addFilterBefore(customizeAbstractSecurityInterceptor, FilterSecurityInterceptor.class);//权限拦截器
    }


    /***
     * 功能:  UserDetailsService注入容器
     * 描述:  处理和存放用户账号密码及权限信息
     * 参数
     * 返回值: org.springframework.security.core.userdetails.UserDetailsService
     * 时间: 2020/10/11 0011
     * 作者: www.hetaozi.com
     **/
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }


    /**
     * 功能: security采用的加密方式
     * 描述:
     * 参数 []
     * 返回值: org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
     * 时间: 2020/10/11 0011
     * 作者: www.hetaozi.com
     **/
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
