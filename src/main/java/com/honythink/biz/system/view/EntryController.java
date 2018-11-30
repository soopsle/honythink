package com.honythink.biz.system.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.honythink.Constants;
import com.honythink.biz.base.controller.BaseController;
import com.honythink.biz.system.dto.BaseDto;
import com.honythink.biz.system.dto.EntryDto;
import com.honythink.biz.system.service.MailService;
import com.honythink.biz.system.service.ResumeService;
import com.honythink.db.entity.Customer;
import com.honythink.db.entity.Dic;
import com.honythink.db.entity.Entry;
import com.honythink.db.entity.SysUser;
import com.honythink.db.mapper.CustomerMapper;
import com.honythink.db.mapper.DicMapper;
import com.honythink.db.mapper.EntryMapper;
import com.honythink.db.mapper.InterviewMapper;
import com.honythink.db.mapper.SysUserMapper;

/**
 * 
 * @author
 * @version : 1.00
 * @Copyright http://www.onehome.cn/
 * @Create Time : 2017年2月28日 上午12:58:47
 * @Description : interview
 * @History：Editor version Time Operation Description*
 *
 */
@Controller
@RequestMapping(value = "/entry")
public class EntryController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger("EntryController");
    
    @Autowired
    private EntryMapper entryMapper;

    @Autowired
    private InterviewMapper interviewMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private DicMapper dicMapper;
    
    @Value("${spring.mail.username}")
    private String from;
    @Value("${spring.mail.cc}")
    private String cc;

    @RequestMapping(value = "/entry_index", method = RequestMethod.GET)
    public ModelAndView entry_index() {
        ModelAndView mav = new ModelAndView("entry_index");

        // hr
        List<SysUser> users = sysUserMapper.getUsersByRole(Constants.ROLE_HR);
        mav.addObject("users", users);
        
      

        BaseDto dto = new BaseDto();
        List<Customer> customers = customerMapper.list(dto);
        mav.addObject("customers", customers);
        
        
        //数据字典
        List<Dic> taxs = dicMapper.selectByName("tax");
        List<Dic> isfund = dicMapper.selectByName("isfund");
        List<Dic> probation = dicMapper.selectByName("probation");
        List<Dic> probation_percent = dicMapper.selectByName("probation_percent");
        List<Dic> grant = dicMapper.selectByName("grant");
        List<Dic> computer = dicMapper.selectByName("computer");
        //税前后
        mav.addObject("taxs", JSON.toJSONString(taxs));
        mav.addObject("isfund", JSON.toJSONString(isfund));
        mav.addObject("probation", JSON.toJSONString(probation));
        mav.addObject("probation_percent", JSON.toJSONString(probation_percent));
        mav.addObject("grant", JSON.toJSONString(grant));
        mav.addObject("computer", JSON.toJSONString(computer));
        return mav;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public String add(Entry record, Integer[] ids) {
        for (Integer id : ids) {
            record.setInterviewId(id);
            entryMapper.insertSelective(record);
        }
        return Constants.RET_SUCCESS;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public String update(Entry record) {
        entryMapper.updateByPrimaryKeySelective(record);
        return Constants.RET_SUCCESS;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public String delete(@PathVariable("id") Integer id) {
        entryMapper.deleteByPrimaryKey(id);
        return Constants.RET_SUCCESS;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(Integer[] ids) {
        for (Integer id : ids)
            entryMapper.deleteByPrimaryKey(id);
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
    @RequestMapping(value = "/list")
    @ResponseBody
    public Map<String, Object> list(EntryDto dto) {
        Map<String, Object> result = new HashMap<String, Object>();
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!hasRole(Constants.ROLE_ADMIN)) {
            dto.setUsername(userDetails.getUsername());
            dto.setRole(Constants.ROLE_HR);
        }
        if(!hasRoleAdmin()){
          dto.setUsername(userDetails.getUsername());
      }
        dto.setPage((dto.getPage()-1)*dto.getRows());
        List<EntryDto> record = entryMapper.list(dto);
        dto.setPage(null);
        dto.setRows(null);
        result.put("total", entryMapper.list(dto).size());
        result.put("rows", record);
        return result;
    }

}
