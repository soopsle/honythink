package com.honythink.common.exception;

/**
 * 
 * @author 
 * @version : 1.00
 * @Copyright http://www.onehome.cn/
 * @Create Time : 2016年10月12日 上午11:40:45
 * @Description :  将多个错误统一返回
 * @History：Editor　　　version　　　Time　　　　　Operation　　　　　　　Description*
 *
 */
public class CommonException extends RuntimeException {

    private static final long serialVersionUID = 2548943619717022268L;
    //错误code
    private String errorCode;
    //错误信息
    private String errorMsg;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public CommonException() {
        super();
    }

    public CommonException(String errorCode, String errorMsg) {
        super();
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }
    public CommonException(String errorCode, String errorMsg,Throwable cause) {
        super(errorMsg,cause);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

}
