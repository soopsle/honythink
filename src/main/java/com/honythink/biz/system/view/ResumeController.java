package com.honythink.biz.system.view;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.honythink.Constants;
import com.honythink.biz.base.controller.BaseController;
import com.honythink.biz.system.dto.BaseDto;
import com.honythink.biz.system.service.ResumeService;
import com.honythink.common.util.FileUtils;
import com.honythink.common.util.OfficeUtils;
import com.honythink.common.util.OfficeWriteUtils;
import com.honythink.db.entity.Resume;

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

    @Autowired
    private ResumeService resumeService;

    @RequestMapping("/resume_index")
    public String resume_index() {
        return "/resume_index";
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
    public Map<String, Object> handleFileUpload(@RequestParam("file") MultipartFile file) {
        Map<String, Object> datas = new HashMap<String, Object>();
        if (!file.isEmpty()) {
            try {
                // 简历上传
                File resumeFile = FileUtils.createFile(Constants.RESUME_UPLOAD,UUID.randomUUID().toString()+"_"+file.getOriginalFilename());
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(resumeFile));
                out.write(file.getBytes());
                out.flush();
                out.close();
                dealResume(resumeFile, datas);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                datas.put("ret", false);
                datas.put("msg", e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
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
        BufferedOutputStream stream = null;
        for (int i = 0; i < files.size(); i++) {
            file = files.get(i);
            File resumeFile = new File(file.getOriginalFilename());
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    stream = new BufferedOutputStream(new FileOutputStream(resumeFile));
                    stream.write(bytes);
                    stream.close();
                    dealResume(resumeFile, datas);
                } catch (Exception e) {
                    stream = null;
                    datas.put("ret", false);
                    datas.put("msg", e.getMessage());
                }
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
    private Map<String, Object> dealResume(File resumeFile, Map<String, Object> datas) {
        // 处理简历
        InputStream is;
        try {
            is = new FileInputStream(resumeFile);
            String html;
            html = OfficeUtils.getContent(is);
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
            record.setResume_name(resumeFile.getName());
            // record.setTrain(OfficeUtils.find);
            record.setTime(new Date());
            // 数据入库
            resumeService.insertSelective(record);
            datas.put("ret", true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            datas.put("ret", false);
            datas.put("msg", e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
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
    public List<Resume> list(BaseDto dto) {
        dto.setPage(dto.getPage()-1);
        List<Resume> record = resumeService.list(dto);
        return record;
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void download(Integer[] ids, HttpServletRequest request, HttpServletResponse response) {
        List<File> files = new ArrayList<File>();
        String base = Constants.RESUME_TEMPLATE;
        String baseZip = Constants.RESUME_ZIP;
        for (Integer id : ids) {
            // 套用模板 生成简历
            Resume record = resumeService.selectByPrimaryKey(id);
            String paths = OfficeWriteUtils.templateResume(base,record);
            for (String path : paths.split("@@@@")) {
                File file = new File(base+path);
                files.add(file);
            }
        }
        String fileName = UUID.randomUUID().toString() + ".zip";
        FileUtils.createFile(baseZip, fileName);
        File fileZip = new File(baseZip + fileName);
        try {
            // 文件输出流
            FileOutputStream outStream = new FileOutputStream(fileZip);
            // 压缩流
            ZipOutputStream toClient = new ZipOutputStream(outStream);
            FileUtils.zipFile(files, toClient);
            toClient.close();
            outStream.close();
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }
        FileUtils.downloadFile(fileZip, response, true);
    }

}
