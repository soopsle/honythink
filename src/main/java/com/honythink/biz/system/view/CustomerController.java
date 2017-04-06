package com.honythink.biz.system.view;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.honythink.Constants;
import com.honythink.biz.base.controller.BaseController;
import com.honythink.biz.system.dto.BaseDto;
import com.honythink.biz.system.dto.ResumeDto;
import com.honythink.biz.system.service.ResumeService;
import com.honythink.common.util.FileUtils;
import com.honythink.common.util.OfficeUtils;
import com.honythink.common.util.OfficeWriteUtils;
import com.honythink.db.entity.Customer;
import com.honythink.db.entity.Resume;
import com.honythink.db.entity.SysUser;
import com.honythink.db.mapper.CustomerMapper;
import com.honythink.db.mapper.InterviewMapper;
import com.honythink.db.mapper.SysUserMapper;

/**
 * 
 * @author
 * @version : 1.00
 * @Copyright http://www.onehome.cn/
 * @Create Time : 2017年2月28日 上午12:58:47
 * @Description : Customer
 * @History：Editor version Time Operation Description*
 *
 */
@Controller
@RequestMapping(value = "/customer")
public class CustomerController extends BaseController {
    
    private static final Logger log = LoggerFactory.getLogger("CustomerController");

    @Autowired
    private CustomerMapper customerMapper;
    
    @Autowired
    private SysUserMapper sysUserMapper;

    
    @RequestMapping(value = "/customer_index", method = RequestMethod.GET)
    public ModelAndView customer_index() {
        ModelAndView mav = new ModelAndView("customer_index");
        
        //hr
        List<SysUser> users = sysUserMapper.getUsersByRole(Constants.ROLE_HR);
        mav.addObject("users", users);
        mav.addObject("templates", Constants.CUSTOMER_TEMPLATE_NAME);
        return mav;
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public String add(Customer record) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
        .getAuthentication()
        .getPrincipal();
        if(!hasRoleAdmin()){
            record.setUsername(userDetails.getUsername());
        }
        customerMapper.insertSelective(record);
        return Constants.RET_SUCCESS;
    }
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public String update(Customer record) {
        customerMapper.updateByPrimaryKeySelective(record);
        return Constants.RET_SUCCESS;
    }
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public String delete(@PathVariable("id") Integer id) {
        customerMapper.deleteByPrimaryKey(id);
        return Constants.RET_SUCCESS;
    }
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(Integer[] ids) {
        for(Integer id:ids)
            customerMapper.deleteByPrimaryKey(id);
        return Constants.RET_SUCCESS;
    }
    /**
     * 
     * @param id
     * @return
     * @about version ：1.00
     * @auther :
     * @Description ：简历写入模板
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> list(BaseDto dto) {
        Map<String,Object> result = new HashMap<String,Object>();
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        if(!hasRoleAdmin()){
//            dto.setUsername(userDetails.getUsername());
        }
//        dto.setPage(dto.getPage()-1);
        dto.setPage((dto.getPage()-1)*dto.getRows());
        List<Customer> record = customerMapper.list(dto);
        dto.setPage(null);
        dto.setRows(null);
        result.put("total",customerMapper.list(dto).size());
        result.put("rows",record);
        
        return result;
    }

 
}
