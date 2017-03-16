package com.honythink.biz.system.view;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.beans.factory.annotation.Value;
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
import com.honythink.biz.system.service.MailService;
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
    
    @Autowired
    private MailService mailService;
    
    
    @Value("${spring.mail.username}")  
    private String from;  
    @Value("${spring.mail.cc}")  
    private String cc;  
    
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
    public List<InterviewDto> list(InterviewDto dto) {
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
    public static Map<String, List<InterviewDto>> group(List<InterviewDto> list) {
        Map<String, List<InterviewDto>> map = new HashMap<String,List<InterviewDto>>();
        if (null == list || null == map) {  
            return null;  
        }  
        // 按name开始分组  
        String key;  
        List<InterviewDto> listTmp;  
        for (InterviewDto val : list) {  
            key = val.getName();  
            listTmp = map.get(key);  
            if (null == listTmp) {  
                listTmp = new ArrayList<InterviewDto>();  
                map.put(key, listTmp);  
            }  
            listTmp.add(val);  
        }  
        return map;
    }
    @RequestMapping(value = "/push",method = RequestMethod.POST)
    @ResponseBody
    public String push(Integer[] ids,Integer[] sellerIds){
        String uuidName = UUID.randomUUID().toString();
        String to;
        List<InterviewDto> interviews = new ArrayList<InterviewDto>();
        for(Integer id:ids){
            InterviewDto dto = interviewMapper.selectDtoByPrimaryKey(id);
            interviews.add(dto);
        }
        Map<String, List<InterviewDto>> map = group(interviews);
        for(Integer sellerId:sellerIds){
            for(Map.Entry<String, List<InterviewDto>> entry:map.entrySet()){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
                String now = sdf.format(new Date());
                SysUser seller = sysUserMapper.selectByPrimaryKey(sellerId);
                to = seller.getUsername()+Constants.MAIL_EXTENTIONS;
                //获取信息 拼装table
                StringBuffer content = new StringBuffer();  
                content.append("<h2><font color=red>推荐列表</font></h2>");  
                content.append("<hr>");  
                content.append("<table border='1px'>");
                content.append("<tr>");  
                content.append("<td><strong>姓名</strong></td>");  
                content.append("<td><strong>电话</strong></td>");  
                content.append("<td><strong>岗位</strong></td>");  
                content.append("<td><strong>客户名称</strong></td>");  
                content.append("<td><strong>推荐人</strong></td>");  
                content.append("<td><strong>最高学历</strong></td>");  
                content.append("<td><strong>毕业日期</strong></td>");  
                content.append("<td><strong>期望薪资</strong></td>");  
                content.append("<td><strong>到岗时间</strong></td>");  
                content.append("</tr>");  
                for(InterviewDto dto:entry.getValue()){
                    content.append("<tr>");  
                    content.append("<td>"+(dto.getResumeName()==null?"":dto.getResumeName())+"</td>");  
                    content.append("<td>"+(dto.getResumeMobile()==null?"":dto.getResumeMobile())+"</td>");  
                    content.append("<td>"+(dto.getPosition()==null?"":dto.getPosition())+"</td>");  
                    content.append("<td>"+(dto.getName()==null?"":dto.getName())+"</td>");  
                    content.append("<td>"+(dto.getRealnameHr()==null?"":dto.getRealnameHr())+"</td>");  
                    content.append("<td>"+(dto.getEducation()==null?"":dto.getEducation())+"</td>");  
                    content.append("<td>"+(dto.getEducationtime()==null?"":dto.getEducationtime())+"</td>");  
                    content.append("<td>"+(dto.getSalary()==null?"":dto.getSalary())+"</td>");  
                    content.append("<td>"+(dto.getWorkTime()==null?"":dto.getWorkTime())+"</td>");  
                    content.append("</tr>"); 
                }
                content.append("</table>");  
                //打包zip
                File fileZip = generateZip(entry.getValue(),"outsource-honythink-"+now,uuidName);
                if(fileZip == null){
                    return Constants.ERROR_INCOMPLETE_PARAMS;
                }
                //发送邮件
                UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getPrincipal();
                String subject = "推荐"+entry.getKey()+"-弘毅知行-"+now;
                String copys = cc+","+userDetails.getUsername()+Constants.MAIL_EXTENTIONS;
                mailService.sendAttachmentsMail(to,copys,subject,content.toString(),fileZip.getPath());  
            }
        }
        return Constants.RET_SUCCESS;
    }
    
    public File generateZip(List<File> files,String zipName){
        String baseZip = Constants.RESUME_ZIP;
        File fileZip;
        //打包zip
        String fileName = zipName + Constants.SUFFIX_ZIP;
        try {
            FileUtils.createFile(baseZip, fileName);
            fileZip = new File(baseZip + fileName);
            // 文件输出流
            FileOutputStream outStream = new FileOutputStream(fileZip);
            // 压缩流
            ZipOutputStream toClient = new ZipOutputStream(outStream);
            FileUtils.zipFile(files, toClient);
            toClient.close();
            outStream.close();
            return fileZip;
        } catch (IOException | ServletException e) {
            log.error(e.getMessage());
        }
        return null;
    }
    /**
     * 
     * @param interviews  单个公司实体
     * @param zipName  zip包名,不含后缀
     * @param uuidName 整个事件uuidName
     * @return
     * @about version ：1.00
     * @auther : 
     * @Description ：
     */
    public File generateZip(List<InterviewDto> interviews,String zipName,String uuidName){
        List<File> files = new ArrayList<File>();
        String base = Constants.RESUME_TEMPLATE+uuidName+Constants.PATH_SEPERATOR;
        String baseZip = Constants.RESUME_ZIP;
        File fileZip;
        String customer ="honythink";
        for (InterviewDto interview : interviews) {
            customer = interview.getShortname();
            if(null == interview.getResumeName() || interview.getResumeName().equals("")){
                return null;
            }
            if(null == interview.getResumeMobile() || interview.getResumeMobile().equals("")){
                return null;
            }
            if(null == interview.getRecommendTime() || interview.getRecommendTime().equals("")){
                return null;
            }
            if(null == interview.getPosition() || interview.getPosition().equals("")){
                return null;
            }
            if(null == interview.getName() || interview.getName().equals("")){
                return null;
            }
            if(null == interview.getSalary() || interview.getSalary().equals("")){
                return null;
            }
            if(null == interview.getWorkTime() || interview.getWorkTime().equals("")){
                return null;
            }
            Integer resumeid = interview.getResumeId();
            Resume resume = resumeService.selectByPrimaryKey(resumeid);
            // 套用模板 生成简历
            String paths;
            try {
                    List<String> templatePaths = new ArrayList<String>();
                    if(null!=interview.getShortname()&&!interview.getShortname().equals("")){
                        if(null!=Constants.CUSTOMER_TEMPLATE_WORD.get(interview.getShortname()))
                        templatePaths.add(Constants.CUSTOMER_TEMPLATE_WORD.get(interview.getShortname()));
                    }
                    paths = OfficeWriteUtils.templateResume(base,resume,templatePaths);
                    if(null!=paths&&!paths.equals("")){
                        for (String path : paths.split("@@@@")) {
                            File file = new File(base+path);
                            files.add(file);
                        }
                    }
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
            //生成excel
            try {
            // OfficeWriteUtils.writeExcelSummarySelf(uuidName,interviews);
            OfficeWriteUtils.writeExcelSummaryCustomer(uuidName,interviews);
                files.add(new File(Constants.RESUME_TEMPLATE+uuidName+Constants.PATH_SEPERATOR+Constants.XLS_WORKBOOK_EXPORT_CUSTOMER));
                    //现在只有csix的
                    if(customer.equals(Constants.CUSTOMER_NAME_CSIX)){
                        OfficeWriteUtils.writeExcelSummaryCsix(uuidName,interviews);
                        files.add(new File(Constants.RESUME_TEMPLATE+uuidName+Constants.PATH_SEPERATOR+customer+Constants.SUFFIX_XLS));
                    }
            } catch (IOException e) {
                log.error(e.getMessage());
            }

        //打包zip
        String fileName = zipName + Constants.SUFFIX_ZIP;
        try {
            FileUtils.createFile(baseZip, fileName);
            fileZip = new File(baseZip + fileName);
            // 文件输出流
            FileOutputStream outStream = new FileOutputStream(fileZip);
            // 压缩流
            ZipOutputStream toClient = new ZipOutputStream(outStream);
            FileUtils.zipFile(files, toClient);
            toClient.close();
            outStream.close();
            return fileZip;
        } catch (IOException | ServletException e) {
            log.error(e.getMessage());
        }
        return null;
    }
    
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    @ResponseBody
    public String download(Integer[] ids, HttpServletRequest request, HttpServletResponse response) {
        String uuidName = UUID.randomUUID().toString();
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String now = sdf.format(new Date());
        
        List<InterviewDto> interviews = new ArrayList<InterviewDto>();
        for(Integer id:ids){
            InterviewDto dto = interviewMapper.selectDtoByPrimaryKey(id);
            interviews.add(dto);
        }
        List<File> files = new ArrayList<File>();
        Map<String, List<InterviewDto>> map = group(interviews);
        for(Map.Entry<String, List<InterviewDto>> entry:map.entrySet()){
            //打包zip
            File fileZip = generateZip(entry.getValue(),"推荐"+entry.getKey()+"-弘毅知行-"+now,uuidName);
            files.add(fileZip);
        }
        //整体xls
        try {
            OfficeWriteUtils.writeExcelSummarySelf(uuidName,interviews);
            OfficeWriteUtils.writeExcelSummaryCustomer(uuidName,interviews);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        files.add(new File(Constants.RESUME_TEMPLATE+uuidName+Constants.PATH_SEPERATOR+Constants.XLS_WORKBOOK_EXPORT_SELF));
        files.add(new File(Constants.RESUME_TEMPLATE+uuidName+Constants.PATH_SEPERATOR+Constants.XLS_WORKBOOK_EXPORT_CUSTOMER));
        File fileZip = generateZip(files,"推荐-弘毅知行-"+now);
        if(fileZip == null){
            return Constants.ERROR_INCOMPLETE_PARAMS;
        }
        try {
            FileUtils.downloadFile(fileZip, response, true);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return Constants.RET_SUCCESS;
    }
    
    @RequestMapping(value = "/validate", method = RequestMethod.GET)
    @ResponseBody
    public String validate(Integer[] ids, HttpServletRequest request, HttpServletResponse response) {
        String ret = Constants.RET_SUCCESS;
        for (Integer id : ids) {
                InterviewDto interview = interviewMapper.selectDtoByPrimaryKey(id);
                if(null == interview.getResumeName() || interview.getResumeName().equals("")){
                    ret = Constants.RET_ERROR;
                }
                if(null == interview.getResumeMobile() || interview.getResumeMobile().equals("")){
                    ret = Constants.RET_ERROR;
                }
                if(null == interview.getRecommendTime() || interview.getRecommendTime().equals("")){
                    ret = Constants.RET_ERROR;
                }
                if(null == interview.getPosition() || interview.getPosition().equals("")){
                    ret = Constants.RET_ERROR;
                }
                if(null == interview.getName() || interview.getName().equals("")){
                    ret = Constants.RET_ERROR;
                }
                if(null == interview.getSalary() || interview.getSalary().equals("")){
                    ret = Constants.RET_ERROR;
                }
                if(null == interview.getWorkTime() || interview.getWorkTime().equals("")){
                    ret = Constants.RET_ERROR;
                }
            }
        return ret;
    }
        
}
