package com.honythink.biz.base.controller;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
