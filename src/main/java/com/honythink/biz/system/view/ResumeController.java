package com.honythink.biz.system.view;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;
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

    @RequestMapping("")
    public String index() {
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
                File resumeFile = new File(file.getOriginalFilename());
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
        List<Resume> record = resumeService.list(dto);
        return record;
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void download(Integer[] ids, HttpServletRequest request, HttpServletResponse response) {
        List<File> files = new ArrayList<File>();
        String base = request.getSession().getServletContext().getRealPath("/") + "upload/";
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
        createFile(base, fileName);
        File fileZip = new File(base + fileName);
        try {
            // 文件输出流
            FileOutputStream outStream = new FileOutputStream(fileZip);
            // 压缩流
            ZipOutputStream toClient = new ZipOutputStream(outStream);
            zipFile(files, toClient);
            toClient.close();
            outStream.close();
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }
        this.downloadFile(fileZip, response, true);
    }

    // 创建文件
    public void createFile(String path, String fileName) {
        // path表示你所创建文件的路径
        File f = new File(path);

        if (!f.exists()) {
            f.mkdirs();
        }

        // fileName表示你创建的文件名；为txt类型；
        File file = new File(f, fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void zipFile(List<File> files, ZipOutputStream outputStream) throws IOException, ServletException {
        try {
            int size = files.size();
            // 压缩列表中的文件
            for (int i = 0; i < size; i++) {
                File file = (File) files.get(i);
                zipFile(file, outputStream);
            }
        } catch (IOException e) {
            throw e;
        }
    }

    public static void zipFile(File inputFile, ZipOutputStream outputstream) throws IOException, ServletException {
        try {
            if (inputFile.exists()) {
                if (inputFile.isFile()) {
                    FileInputStream inStream = new FileInputStream(inputFile);
                    BufferedInputStream bInStream = new BufferedInputStream(inStream);
                    ZipEntry entry = new ZipEntry(inputFile.getName());
                    outputstream.putNextEntry(entry);

                    final int MAX_BYTE = 10 * 1024 * 1024; // 最大的流为10M
                    long streamTotal = 0; // 接受流的容量
                    int streamNum = 0; // 流需要分开的数量
                    int leaveByte = 0; // 文件剩下的字符数
                    byte[] inOutbyte; // byte数组接受文件的数据

                    streamTotal = bInStream.available(); // 通过available方法取得流的最大字符数
                    streamNum = (int) Math.floor(streamTotal / MAX_BYTE); // 取得流文件需要分开的数量
                    leaveByte = (int) streamTotal % MAX_BYTE; // 分开文件之后,剩余的数量

                    if (streamNum > 0) {
                        for (int j = 0; j < streamNum; ++j) {
                            inOutbyte = new byte[MAX_BYTE];
                            // 读入流,保存在byte数组
                            bInStream.read(inOutbyte, 0, MAX_BYTE);
                            outputstream.write(inOutbyte, 0, MAX_BYTE); // 写出流
                        }
                    }
                    // 写出剩下的流数据
                    inOutbyte = new byte[leaveByte];
                    bInStream.read(inOutbyte, 0, leaveByte);
                    outputstream.write(inOutbyte);
                    outputstream.closeEntry(); // Closes the current ZIP entry
                    // and positions the stream for
                    // writing the next entry
                    bInStream.close(); // 关闭
                    inStream.close();
                }
            } else {
                throw new ServletException("文件不存在！");
            }
        } catch (IOException e) {
            throw e;
        }
    }

    public void downloadFile(File file, HttpServletResponse response, boolean isDelete) {
        try {
            // 以流的形式下载文件。
            BufferedInputStream fis = new BufferedInputStream(new FileInputStream(file.getPath()));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(file.getName().getBytes("UTF-8"), "ISO-8859-1"));
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
            if (isDelete) {
                file.delete(); // 是否将生成的服务器端文件删除
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
