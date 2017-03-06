/***
 * company ： Beijing HonyThink Co., Ltd.
 * @Copyright Beijing Honythink Tech Co.,Ltd
 * @since：JDK1.6
 * @version：1.0
 * @author zhouxing
 * @see：
 ***/
package com.honythink.biz.system.dto;

/**
 * @author zhouxing
 * @version : 1.00
 * @Copyright Beijing Honythink Tech Co.,Ltd
 * @Create Time : 2014-11-25 下午3:33:38
 * @Description :
 * @History：Editor version Time Operation Description*
 * 
 */
public class ResumeDto extends BaseDto {
    // 查询时的当前页数
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
}
