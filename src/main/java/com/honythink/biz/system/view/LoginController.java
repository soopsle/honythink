package com.honythink.biz.system.view;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.honythink.db.entity.SysUser;


/**
 * Created by yangyibo on 17/3/1.
 */
@RestController
public class LoginController {

//    @RequestMapping(value = "/login")
//    @ResponseBody
//    public Object login(@AuthenticationPrincipal SysUser loginedUser, @RequestParam(name = "logout", required = false) String logout) {
//        if (logout != null) {
//            return null;
//        }
//        if (loginedUser != null) {
//            return loginedUser;
//        }
//        return null;
//    }
}