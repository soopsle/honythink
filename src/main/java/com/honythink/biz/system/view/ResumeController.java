package com.honythink.biz.system.view;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
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
 * @Description : resume
 * @History：Editor version Time Operation Description*
 *
 */
@Controller
@RequestMapping(value = "/resume")
public class ResumeController extends BaseController {
    
    private static final Logger log = LoggerFactory.getLogger("ResumeController");

    @Autowired
    private ResumeService resumeService;
    
    @Autowired
    private SysUserMapper sysUserMapper;
    
    @Autowired
    private CustomerMapper customerMapper;

    @RequestMapping("/resume_index")
    public ModelAndView resume_index() {
        ModelAndView mav = new ModelAndView("resume_index");
        
        //hr
        List<SysUser> hrs = sysUserMapper.getUsersByRole(Constants.ROLE_HR);
        mav.addObject("hrs", hrs);
        
        //seller
        List<SysUser> sellers = sysUserMapper.getUsersByRole(Constants.ROLE_SELLER);
        mav.addObject("sellers", sellers);
        
        //客户
        BaseDto dto = new BaseDto();
        List<Customer> customers = customerMapper.list(dto);
        mav.addObject("customers", customers);
        return mav;
    }

    @RequestMapping("/singefile")
    public String file() {
        return "/file";
    }

    @RequestMapping("/mutifile")
    public String mutifile() {
        return "/mutifile";
    }

    /**
     * 文件上传具体实现方法;
     * 
     * @param file
     * @return
     */
    @RequestMapping("/upload")
    @ResponseBody
    public Map<String, Object> handleFileUpload(@RequestParam("file") MultipartFile file,HttpServletResponse response) {
        Map<String, Object> datas = new HashMap<String, Object>();
        if (!file.isEmpty()) {
            try {
                // 简历上传
                File resumeFile = FileUtils.createFile(Constants.RESUME_UPLOAD,UUID.randomUUID().toString()+"_"+file.getOriginalFilename());
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(resumeFile));
                out.write(file.getBytes());
                out.flush();
                out.close();
                dealResume(file.getOriginalFilename(),resumeFile, datas);
            } catch (FileNotFoundException e) {
                log.error(e.getMessage());
                datas.put("ret", false);
                datas.put("msg", e.getMessage());
            } catch (IOException e) {
                log.error(e.getMessage());
                datas.put("ret", false);
                datas.put("msg", e.getMessage());
            }
            datas.put("ret", true);
        } else {
            datas.put("ret", false);
            datas.put("msg", "文件为空");
        }
        return datas;
    }

    /**
     * 多文件具体上传时间，主要是使用了MultipartHttpServletRequest和MultipartFile
     * 
     * @param request
     * @return
     */

    @RequestMapping(value = "/batch/upload", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> handleFileUpload(HttpServletRequest request) {
        Map<String, Object> datas = new HashMap<String, Object>();
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        MultipartFile file = null;
        for (int i = 0; i < files.size(); i++) {
            file = files.get(i);
            if (!file.isEmpty()) {
                try {
                    // 简历上传
                    File resumeFile = FileUtils.createFile(Constants.RESUME_UPLOAD,UUID.randomUUID().toString()+"_"+file.getOriginalFilename());
                    BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(resumeFile));
                    out.write(file.getBytes());
                    out.flush();
                    out.close();
                    dealResume(file.getOriginalFilename(),resumeFile, datas);
                } catch (FileNotFoundException e) {
                    log.error(e.getMessage());
                    datas.put("ret", false);
                    datas.put("msg", e.getMessage());
                } catch (IOException e) {
                    log.error(e.getMessage());
                    datas.put("ret", false);
                    datas.put("msg", e.getMessage());
                }
                datas.put("ret", true);
            } else {
                datas.put("ret", false);
                datas.put("msg", "文件为空");
            }
        }
        return datas;
    }

    /**
     * 
     * @param resumeFile
     * @param datas
     * @return
     * @about version ：1.00
     * @auther :
     * @Description ：解析简历
     */
    private Map<String, Object> dealResume(String originalName,File resumeFile, Map<String, Object> datas) {
        // 处理简历
        InputStream is;
        try {
            is = new FileInputStream(resumeFile);
            String html;
            html = OfficeUtils.getContentNoBr(is);
            Resume record = new Resume();
            record.setName(OfficeUtils.findName(html));
            record.setGender(OfficeUtils.findGender(html));
            record.setBirthday(OfficeUtils.findBirthday(html));
            record.setAge(OfficeUtils.findAge(html));
            record.setSeniority(OfficeUtils.findSeniority(html));
            record.setCard(OfficeUtils.findCard(html));
            record.setMobile(OfficeUtils.findMobile(html));
            // record.setEmail(OfficeUtils.find);
            record.setMarriage(OfficeUtils.findMarried(html));
            record.setCity(OfficeUtils.findCity(html));
            // record.setAddr(OfficeUtils.findA));
            record.setResidence(OfficeUtils.findResidence(html));
            record.setSchool(OfficeUtils.findSchool(html));
            record.setMajor(OfficeUtils.findMajor(html));
            record.setEducation(OfficeUtils.findEducation(html));
            record.setEducationtime(OfficeUtils.findEducationtime(html));
            // record.setMoney(money);
            record.setSelf(OfficeUtils.findSelf(html));
            record.setWork(OfficeUtils.findWork(html));
            record.setProject(OfficeUtils.findProjects(html));
            record.setResumeName(originalName);
            record.setTrain(OfficeUtils.findTrain(html));
            record.setTime(new Date());
            
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getPrincipal();
            record.setUsername(userDetails.getUsername());
            // 数据入库
            resumeService.insertSelective(record);
            datas.put("ret", true);
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
            datas.put("ret", false);
            datas.put("msg", e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
            datas.put("ret", false);
            datas.put("msg", e.getMessage());
        }
        return datas;
    }
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public String list(Resume record) {
        resumeService.updateByPrimaryKeySelective(record);
        return Constants.RET_SUCCESS;
    }
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public String list(@PathVariable("id") Integer id) {
        resumeService.deleteByPrimaryKey(id);
        return Constants.RET_SUCCESS;
    }
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String list(Integer[] ids) {
        for(Integer id:ids)
        resumeService.deleteByPrimaryKey(id);
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
    public Map<String,Object> list(ResumeDto dto) {
        Map<String,Object> result = new HashMap<String,Object>();
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        if(!hasRoleAdmin()){
            dto.setUsername(userDetails.getUsername());
        }
        dto.setPage(dto.getPage()-1);
        List<Resume> record = resumeService.list(dto);
        
        dto.setPage(null);
        dto.setRows(null);
        result.put("total",resumeService.list(dto).size());
        result.put("rows",record);
        return result;
    }

    public static boolean hasRoleAdmin() {
        Authentication authentication = getAuthentication();
        if (authentication == null) {
            return false;
        }
        Collection<GrantedAuthority> grantedAuthorityList = (Collection<GrantedAuthority>) authentication.getAuthorities();
        for (GrantedAuthority authority : grantedAuthorityList) {
            if ("ROLE_ADMIN".equals(authority.getAuthority())) {
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
        
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void download(Integer[] ids, HttpServletRequest request, HttpServletResponse response) {
        List<File> files = new ArrayList<File>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String now = sdf.format(new Date());
        String base = Constants.RESUME_TEMPLATE+now+Constants.PATH_SEPERATOR;
        String baseZip = Constants.RESUME_ZIP;
        List<Resume> resumes = new ArrayList<Resume>();
        for (Integer id : ids) {
            // 套用模板 生成简历
            Resume record = resumeService.selectByPrimaryKey(id);
            resumes.add(record);
            //生成word
            String paths;
            List<String> templatePaths = new ArrayList<String>();
            templatePaths.add(Constants.TEMPLATE_HONYTHINK+Constants.SUFFIX_DOC);
            templatePaths.add(Constants.TEMPLATE_CSIX+Constants.SUFFIX_DOC);
            templatePaths.add(Constants.TEMPLATE_YINGU+Constants.SUFFIX_DOC);
            try {
                paths = OfficeWriteUtils.templateResume(base,record,templatePaths);
                for (String path : paths.split("@@@@")) {
                    File file = new File(base+path);
                    files.add(file);
                }
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        //打包zip
        String fileName = Constants.FREFIX_ZIP+now + Constants.SUFFIX_ZIP;
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
