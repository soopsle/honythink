/**
 * @company ： honythink., Ltd.
 * @version：1.0
 **/
package com.honythink.biz.base;

/**
 * @Description:
 * 
 */
public class Pagination {

    /**
     * 页数，默认是第一页
     */
    private Integer page = 1;
    /**
     * 每页显示记录条数，默认10条
     */
    private Integer rows = 10;
    /**
     * 排序字段
     */
    private String sort = "";
    /**
     * 排序方式
     */
    private String order = "";

    public Integer getPage() {
        if (page <= 0) {
            return 0;
        }

        return page - 1;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

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
     * @param sort
     *            the sort to set
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
     * @param order
     *            the order to set
     */
    public void setOrder(String order) {
        this.order = order;
    }

}
