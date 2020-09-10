package com.hetaozi.login.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hetaozi.login.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 吴帆
 * @date 2020/9/10 0010 20:43
 */
@Mapper
//@DS("slave_1")//数据源切换，默认master
public interface SysUserMapper extends BaseMapper<SysUser> {

}