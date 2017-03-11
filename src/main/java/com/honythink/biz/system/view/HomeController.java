package com.honythink.biz.system.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.honythink.db.entity.SysUser;
import com.honythink.db.mapper.SysUserMapper;

/**
 * Created by yangyibo on 17/1/18.
 */
@Controller
@RequestMapping("/users")
public class HomeController {
    @Autowired
    SysUserMapper sysUserMapper;

    @RequestMapping(value = "/getUsers",method = RequestMethod.GET)
    @ResponseBody
    public String getUsers() {
        return "getUsers";
    }
    @Secured({"ROLE_ADMIN"})//此方法只允许 ROLE_ADMIN 和ROLE_USER 角色 访问
    @RequestMapping(value = "/getUsers1",method = RequestMethod.GET)
    @ResponseBody
    public String getUsers1() {
        return "getUsers";
    }
    @Secured({"ROLE_ADMIN"})//此方法只允许 ROLE_ADMIN 和ROLE_USER 角色 访问
    @RequestMapping(value = "/getUsers2",method = RequestMethod.GET)
    @ResponseBody
    public String getUsers2() {
        return "getUsers";
    }

    @Secured({"ROLE_ADMIN","ROLE_USER"})//此方法只允许 ROLE_ADMIN 和ROLE_USER 角色 访问
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @ResponseBody
    public Object save(@RequestBody SysUser record) {
        return  sysUserMapper.insertSelective(record);
    }
}
