package com.honythink.biz.system.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;


public class InterviewExcelDto extends BaseDto{
    private Integer id;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date recommendTime;
    //人名
    private String resumeName;
    private String resumeMobile;
    //性别
    private String resumeGender;
    private String position;
    //公司名称
    private String name;
    
    private String realnameHr;
    //学历
    private String education;
    //学校
    private String resumeSchool;
    //毕业时间
    private String educationtime;
    //服务费
    private Float cover;
    //薪水
    private Float salary;
    private String workTime;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    private Date interviewTime;
    private String present;

    private String result;

    private String remark;

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

    public String getResumeName() {
        return resumeName;
    }

    public void setResumeName(String resumeName) {
        this.resumeName = resumeName;
    }

    public String getResumeMobile() {
        return resumeMobile;
    }

    public void setResumeMobile(String resumeMobile) {
        this.resumeMobile = resumeMobile;
    }

    public String getResumeGender() {
        return resumeGender;
    }

    public void setResumeGender(String resumeGender) {
        this.resumeGender = resumeGender;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRealnameHr() {
        return realnameHr;
    }

    public void setRealnameHr(String realnameHr) {
        this.realnameHr = realnameHr;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getResumeSchool() {
        return resumeSchool;
    }

    public void setResumeSchool(String resumeSchool) {
        this.resumeSchool = resumeSchool;
    }

    public String getEducationtime() {
        return educationtime;
    }

    public void setEducationtime(String educationtime) {
        this.educationtime = educationtime;
    }

    public Float getCover() {
        return cover;
    }

    public void setCover(Float cover) {
        this.cover = cover;
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

}