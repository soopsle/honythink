package com.honythink.biz.system.dto;

public class KPIDto extends BaseDto{
    private String date;
    private String usernameHr;
    private String count;
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getUsernameHr() {
        return usernameHr;
    }
    public void setUsernameHr(String usernameHr) {
        this.usernameHr = usernameHr;
    }
    public String getCount() {
        return count;
    }
    public void setCount(String count) {
        this.count = count;
    }
 
    
}
