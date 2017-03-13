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
 * 
 * @author 
 * @version : 1.00
 * @Copyright http://www.onehome.cn/
 * @Create Time : 2017年3月13日 下午4:14:09
 * @Description : 
 * @History：Editor　　　version　　　Time　　　　　Operation　　　　　　　Description*
 *
 */
public class TreeDto {
    private Integer id;
    private Integer pId;
    private String name;
    private boolean checked;
    private boolean open;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getpId() {
        return pId;
    }
    public void setpId(Integer pId) {
        this.pId = pId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public boolean isChecked() {
        return checked;
    }
    public void setChecked(boolean checked) {
        this.checked = checked;
    }
    public boolean isOpen() {
        return open;
    }
    public void setOpen(boolean open) {
        this.open = open;
    }
    
    
	
}
