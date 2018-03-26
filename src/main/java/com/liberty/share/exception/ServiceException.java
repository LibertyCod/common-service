package com.liberty.share.exception;

/**
 * 服务（业务）异常
 *
 * @author nickChen
 * @since 2018-1-17
 */
public class ServiceException extends BaseRuntimeException {

    public ServiceException() {
        super();
    }

    public ServiceException(int errorCode, String errorMsg, Object data) {
        super(errorCode, errorMsg, data);
    }

    public ServiceException(int errorCode, String errorMsg) {
        super(errorCode, errorMsg);
    }

    public ServiceException(int errorCode) {
        super(errorCode);
    }
}
