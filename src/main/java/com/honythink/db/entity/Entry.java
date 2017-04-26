package com.honythink.db.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Entry {
    private Integer id;

    private Integer interviewId;

    private Integer resumeId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date entrytime;

    private BigDecimal salary;

    private String afterBeforeTax;

    private String isfund;

    private String probation;

    private String probationPercent;

    private String grant;

    private String computer;

    private String profit;

    private String profitRate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getInterviewId() {
        return interviewId;
    }

    public void setInterviewId(Integer interviewId) {
        this.interviewId = interviewId;
    }

    public Integer getResumeId() {
        return resumeId;
    }

    public void setResumeId(Integer resumeId) {
        this.resumeId = resumeId;
    }

    public Date getEntrytime() {
        return entrytime;
    }

    public void setEntrytime(Date entrytime) {
        this.entrytime = entrytime;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getAfterBeforeTax() {
        return afterBeforeTax;
    }

    public void setAfterBeforeTax(String afterBeforeTax) {
        this.afterBeforeTax = afterBeforeTax;
    }

    public String getIsfund() {
        return isfund;
    }

    public void setIsfund(String isfund) {
        this.isfund = isfund;
    }

    public String getProbation() {
        return probation;
    }

    public void setProbation(String probation) {
        this.probation = probation;
    }

    public String getProbationPercent() {
        return probationPercent;
    }

    public void setProbationPercent(String probationPercent) {
        this.probationPercent = probationPercent;
    }

    public String getGrant() {
        return grant;
    }

    public void setGrant(String grant) {
        this.grant = grant;
    }

    public String getComputer() {
        return computer;
    }

    public void setComputer(String computer) {
        this.computer = computer;
    }

    public String getProfit() {
        return profit;
    }

    public void setProfit(String profit) {
        this.profit = profit;
    }

    public String getProfitRate() {
        return profitRate;
    }

    public void setProfitRate(String profitRate) {
        this.profitRate = profitRate;
    }
}