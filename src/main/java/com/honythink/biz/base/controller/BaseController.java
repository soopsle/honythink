package com.honythink.biz.base.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.honythink.Constants;
import com.honythink.biz.system.dto.TreeDto;
import com.honythink.db.entity.SysRole;
import com.honythink.db.entity.SysUser;
import com.honythink.db.mapper.SysRoleMapper;
import com.honythink.db.mapper.SysRoleUserMapper;
import com.honythink.db.mapper.SysUserMapper;
import com.sun.tools.internal.ws.processor.model.Request;

/**
 * 
 * @author
 * @version : 1.00
 * @Copyright http://www.onehome.cn/
 * @Create Time : 2016年11月22日 下午5:45:14
 * @Description : 基础控制层
 * @History：Editor version Time Operation Description*
 *
 */
@Controller
public class BaseController {
    @Autowired
    public HttpServletRequest request;  
    
    @Autowired
    private SysUserMapper sysUserMapper;
    
    @Autowired
    private SysRoleMapper sysRoleMapper;
    
    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;


    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(HttpSession session) {
        return "成功连接到开放平台服务器,提供服务中:" + Math.random() + "";
    }
    @RequestMapping(value = "/index")
    public String index(HttpSession session) {
        return "index";
    }
    @RequestMapping(value = "/login")
    public String login(HttpSession session) {
        return "login";
    }
    
    
    @RequestMapping(value = "/generateTree")
    @ResponseBody
    public List<TreeDto> generateTree(){  
        List<TreeDto> tree = new ArrayList<TreeDto>();
        //首层
        TreeDto top = new TreeDto();
        top.setId(1);
        top.setpId(0);
        top.setName("弘毅知行");
        top.setOpen(true);
        tree.add(top);
        //第二层
        TreeDto second;
        TreeDto third;
        List<SysRole> roles = sysRoleMapper.selectRoles();
        for(SysRole role:roles){
            //只显示销售 HR
            if(role.getName().equals(Constants.ROLE_HR)||(role.getName().equals(Constants.ROLE_SELLER))){
                second = new TreeDto();
                second.setId(role.getRid()*10);
                second.setpId(1);
                second.setName(role.getRealname());
                second.setOpen(true);
                tree.add(second);
                List<SysUser> users = sysUserMapper.getUsersByRole(role.getName());
                for(SysUser user:users){
                    third = new TreeDto();
                    third.setId(user.getUid()*10000);
                    third.setpId(role.getRid()*10);
                    third.setName(user.getRealname());
                    third.setOpen(true);
                    tree.add(third);
                }
            }
        }
        return tree;
    }  
    public static boolean hasRole(String role) {
        Authentication authentication = getAuthentication();
        if (authentication == null) {
            return false;
        }
        Collection<GrantedAuthority> grantedAuthorityList = (Collection<GrantedAuthority>) authentication.getAuthorities();
        for (GrantedAuthority authority : grantedAuthorityList) {
            if (role.equals(authority.getAuthority())) {
                return true;
            }
        }
        return false;
    }

    private static Authentication getAuthentication() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null) {
            return null;
        }
        return context.getAuthentication();
    }
}
