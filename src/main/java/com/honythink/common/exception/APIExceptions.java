package com.honythink.common.exception;

import java.util.ArrayList;
import java.util.List;

/* *
 * 
 * @author
 * @version : 1.00
 * @Copyright http://www.onehome.cn/
 * @Create Time : 2016年10月12日 上午11:40:45
 * @Description :
 * @History：Editor version Time Operation Description*
 *
 */
public class APIExceptions extends RuntimeException {

    private static final long serialVersionUID = 2548943619717022268L;
    // 错误code
    private List<String> errorCode = new ArrayList<String>();
    // 错误信息String
    private List<String> errorMsg = new ArrayList<String>();

    private List<APIException> exceptions = new ArrayList<APIException>();

    public void getMsg() {
        exceptions.forEach(e -> {
            errorCode.add(e.getErrorCode());
            errorMsg.add(e.getErrorCode() + " : " + e.getErrorMsg());
        });
    }

    public List<String> getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(List<String> errorCode) {
        this.errorCode = errorCode;
    }

    public List<String> getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(List<String> errorMsg) {
        this.errorMsg = errorMsg;
    }

    public void addAPIException(APIException e) {
        if (null != e)
            exceptions.add(e);
    }

    public List<APIException> getExceptions() {
        return exceptions;
    }

    public void setExceptions(List<APIException> exceptions) {
        this.exceptions = exceptions;
    }

}
