package com.honythink.biz.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.honythink.db.entity.SysRole;
import com.honythink.db.entity.SysUser;
import com.honythink.db.mapper.SysRoleMapper;
import com.honythink.db.mapper.SysRoleUserMapper;
import com.honythink.db.mapper.SysUserMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangyibo on 17/1/18.
 */
@Service
public class CustomUserServiceImpl implements UserDetailsService { //自定义UserDetailsService 接口

    @Autowired
    SysUserMapper sysUserMapper;
    @Autowired
    SysRoleMapper sysRoleMapper;
    @Autowired
    SysRoleUserMapper sysRoleUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) { //重写loadUserByUsername 方法获得 userdetails 类型用户
        SysUser user = sysRoleUserMapper.findByUserName(username);
        if(user == null){
            throw new UsernameNotFoundException("用户名不存在");
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        //用于添加用户的权限。只要把用户权限添加到authorities 就万事大吉。
        for(SysRole role:user.getRoles())
        {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        user.setGrantedAuthorities(authorities); //用于登录时 @AuthenticationPrincipal 标签取值
        return user;
    }
}