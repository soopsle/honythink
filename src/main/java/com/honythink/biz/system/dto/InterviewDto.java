package com.honythink.biz.system.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;


public class InterviewDto extends BaseDto{
    private Integer id;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date recommendTime;

    private String position;

    private Integer customerId;
    //简历
    private Integer resumeId;
    //人名
    private String resumeName;
    //人名
    private String resumeMobile;
    //学历
    private String education;
    //毕业时间
    private String educationtime;
    //薪水
    private Float salary;
    //性别
    private String resumeGender;
    //工作年限
    private String resumeSeniority;
    //学校
    private String resumeSchool;
    //地址
    private String resumeSelf;
    //服务费
    private Float cover;
    
    private String status;

    private String usernameSell;
    private String realnameSell;
    
    private String usernameHr;
    private String realnameHr;


    private String workTime;

    private Date interviewDate;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date interviewTime;

    private String present;

    private String result;

    private String remark;

    private Date time;
    
    //公司名称
    private String name;
    //简称
    private String shortname;
    
    
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public String getRealnameSell() {
        return realnameSell;
    }

    public void setRealnameSell(String realnameSell) {
        this.realnameSell = realnameSell;
    }

    public String getRealnameHr() {
        return realnameHr;
    }

    public void setRealnameHr(String realnameHr) {
        this.realnameHr = realnameHr;
    }

    public String getResumeGender() {
        return resumeGender;
    }

    public void setResumeGender(String resumeGender) {
        this.resumeGender = resumeGender;
    }

    public String getResumeSeniority() {
        return resumeSeniority;
    }

    public void setResumeSeniority(String resumeSeniority) {
        this.resumeSeniority = resumeSeniority;
    }

    public String getResumeSchool() {
        return resumeSchool;
    }

    public void setResumeSchool(String resumeSchool) {
        this.resumeSchool = resumeSchool;
    }

    public String getResumeSelf() {
        return resumeSelf;
    }

    public void setResumeSelf(String resumeSelf) {
        this.resumeSelf = resumeSelf;
    }

    public Float getCover() {
        return cover;
    }

    public void setCover(Float cover) {
        this.cover = cover;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getEducationtime() {
        return educationtime;
    }

    public void setEducationtime(String educationtime) {
        this.educationtime = educationtime;
    }

    public String getResumeMobile() {
        return resumeMobile;
    }

    public void setResumeMobile(String resumeMobile) {
        this.resumeMobile = resumeMobile;
    }

    public Integer getResumeId() {
        return resumeId;
    }

    public void setResumeId(Integer resumeId) {
        this.resumeId = resumeId;
    }

    public String getResumeName() {
        return resumeName;
    }

    public void setResumeName(String resumeName) {
        this.resumeName = resumeName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getRecommendTime() {
        return recommendTime;
    }

    public void setRecommendTime(Date recommendTime) {
        this.recommendTime = recommendTime;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }


    public String getUsernameSell() {
        return usernameSell;
    }

    public void setUsernameSell(String usernameSell) {
        this.usernameSell = usernameSell;
    }

    public String getUsernameHr() {
        return usernameHr;
    }

    public void setUsernameHr(String usernameHr) {
        this.usernameHr = usernameHr;
    }

    public Float getSalary() {
        return salary;
    }

    public void setSalary(Float salary) {
        this.salary = salary;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public Date getInterviewDate() {
        return interviewDate;
    }

    public void setInterviewDate(Date interviewDate) {
        this.interviewDate = interviewDate;
    }

    public Date getInterviewTime() {
        return interviewTime;
    }

    public void setInterviewTime(Date interviewTime) {
        this.interviewTime = interviewTime;
    }

    public String getPresent() {
        return present;
    }

    public void setPresent(String present) {
        this.present = present;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}