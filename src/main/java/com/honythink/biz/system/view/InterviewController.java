package com.honythink.biz.system.view;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.honythink.Constants;
import com.honythink.biz.base.controller.BaseController;
import com.honythink.biz.system.dto.BaseDto;
import com.honythink.biz.system.dto.InterviewDto;
import com.honythink.biz.system.service.ResumeService;
import com.honythink.common.util.FileUtils;
import com.honythink.common.util.OfficeWriteUtils;
import com.honythink.db.entity.Customer;
import com.honythink.db.entity.Interview;
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
 * @Description : interview
 * @History：Editor version Time Operation Description*
 *
 */
@Controller
@RequestMapping(value = "/interview")
public class InterviewController extends BaseController {
    
    private static final Logger log = LoggerFactory.getLogger("interviewController");

    @Autowired
    private InterviewMapper interviewMapper;
    
    @Autowired
    private SysUserMapper sysUserMapper;
    
    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private ResumeService resumeService;
    
    @RequestMapping(value = "/interview_index", method = RequestMethod.GET)
    public ModelAndView interview_index() {
        ModelAndView mav = new ModelAndView("interview_index");
        
        //hr
        List<SysUser> users = sysUserMapper.getUsersByRole(Constants.ROLE_HR);
        mav.addObject("users", users);
        
        BaseDto dto = new BaseDto();
        List<Customer> customers = customerMapper.list(dto);
        mav.addObject("customers", customers);
        return mav;
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public String add(Interview record,Integer[] ids) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
        .getAuthentication()
        .getPrincipal();
        if(!hasRole(Constants.ROLE_ADMIN)){
            record.setUsernameHr(userDetails.getUsername());
        }
        for(Integer id:ids){
            record.setResumeId(id);
            interviewMapper.insertSelective(record);
        }
        return Constants.RET_SUCCESS;
    }
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public String update(Interview record) {
        interviewMapper.updateByPrimaryKeySelective(record);
        return Constants.RET_SUCCESS;
    }
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public String delete(@PathVariable("id") Integer id) {
        interviewMapper.deleteByPrimaryKey(id);
        return Constants.RET_SUCCESS;
    }
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(Integer[] ids) {
        for(Integer id:ids)
            interviewMapper.deleteByPrimaryKey(id);
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
    public List<InterviewDto> list(BaseDto dto) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        if(!hasRole(Constants.ROLE_ADMIN)){
            dto.setUsername(userDetails.getUsername());
            dto.setRole(Constants.ROLE_HR);
        }
        dto.setPage(dto.getPage()-1);
        List<InterviewDto> record = interviewMapper.list(dto);
        return record;
    }
    
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void download(Integer[] ids, HttpServletRequest request, HttpServletResponse response) {
        List<File> files = new ArrayList<File>();
        String uuidName = UUID.randomUUID().toString();
        String base = Constants.RESUME_TEMPLATE+uuidName+Constants.PATH_SEPERATOR;
        String baseZip = Constants.RESUME_ZIP;
//        List<Resume> resumes = new ArrayList<Resume>();
        List<InterviewDto> interviews = new ArrayList<InterviewDto>();
        for (Integer id : ids) {
            // 套用模板 生成简历
            InterviewDto interview = interviewMapper.selectDtoByPrimaryKey(id);
            Integer resumeid = interview.getResumeId();
            Resume resume = resumeService.selectByPrimaryKey(resumeid);
            interviews.add(interview);
//            resumes.add(resume);
            //生成word
            String paths;
            try {
                paths = OfficeWriteUtils.templateResume(base,resume);
                for (String path : paths.split("@@@@")) {
                    File file = new File(base+path);
                    files.add(file);
                }
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        //生成excel
        try {
            OfficeWriteUtils.writeExcelSummaryCsix(uuidName,interviews);
            OfficeWriteUtils.writeExcelSummarySelf(uuidName,interviews);
            OfficeWriteUtils.writeExcelSummaryCustomer(uuidName,interviews);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        files.add(new File(Constants.RESUME_TEMPLATE+uuidName+Constants.PATH_SEPERATOR+Constants.XLS_WORKBOOK_EXPORT_CSIX));
        files.add(new File(Constants.RESUME_TEMPLATE+uuidName+Constants.PATH_SEPERATOR+Constants.XLS_WORKBOOK_EXPORT_SELF));
        files.add(new File(Constants.RESUME_TEMPLATE+uuidName+Constants.PATH_SEPERATOR+Constants.XLS_WORKBOOK_EXPORT_CUSTOMER));
       
        //打包zip
        String fileName = uuidName + Constants.SUFFIX_ZIP;
        try {
            FileUtils.createFile(baseZip, fileName);
            File fileZip = new File(baseZip + fileName);
            // 文件输出流
            FileOutputStream outStream = new FileOutputStream(fileZip);
            // 压缩流
            ZipOutputStream toClient = new ZipOutputStream(outStream);
            FileUtils.zipFile(files, toClient);
            toClient.close();
            outStream.close();
            FileUtils.downloadFile(fileZip, response, true);
        } catch (IOException | ServletException e) {
            log.error(e.getMessage());
        }
    }
}
