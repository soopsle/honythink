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
 * @History：Editor　　　version　　　Time　　　　　Operation　　　　　　　Description*
 *  
 */
public class BaseDto {
	// 查询时的当前页数
	protected Integer page;
	// 定义的当前每页显示rows
	protected Integer rows;
	// 排序字段
	protected String sort;//0=综合排序，1=离我最近
	// 排序方式
	protected String order;//0=asc,1=desc
	
	protected String pageSize;
	
	protected String total;
	
	protected String username;
	
	protected String role;
	
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPageSize() {
        return pageSize;
    }
    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }
    public String getTotal() {
        return total;
    }
    public void setTotal(String total) {
        this.total = total;
    }
    /**
	 * @return the page
	 */
	public Integer getPage() {
		return page;
	}
	/**
	 * @param page the page to set
	 */
	public void setPage(Integer page) {
		this.page = page;
	}
	/**
	 * @return the rows
	 */
	public Integer getRows() {
		return rows;
	}
	/**
	 * @param rows the rows to set
	 */
	public void setRows(Integer rows) {
		this.rows = rows;
	}
	/**
	 * @return the sort
	 */
	public String getSort() {
		return sort;
	}
	/**
	 * @param sort the sort to set
	 */
	public void setSort(String sort) {
		this.sort = sort;
	}
	/**
	 * @return the order
	 */
	public String getOrder() {
		return order;
	}
	/**
	 * @param order the order to set
	 */
	public void setOrder(String order) {
		this.order = order;
	}
	
}
