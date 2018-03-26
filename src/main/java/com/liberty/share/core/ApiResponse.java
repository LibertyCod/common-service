package com.liberty.share.core;

import com.alibaba.fastjson.JSON;
import com.liberty.share.exception.errorcode.ErrorCodeHelper;
import com.liberty.share.exception.errorcode.SysErrorCode;

/**
 * 统一API响应结果封装
 *
 *
 */
public class ApiResponse {

    public static final ApiResponse BUSY_RESPONSE;
    public static final ApiResponse SUCC_RESPONSE;
    public static final ApiResponse FAIL_RESPONSE;

    static {
        BUSY_RESPONSE = new ApiResponse(SysErrorCode.BUSY, ErrorCodeHelper.get(SysErrorCode.BUSY));
        SUCC_RESPONSE = new ApiResponse(SysErrorCode.SUCCESS, ErrorCodeHelper.get(SysErrorCode.SUCCESS));
        FAIL_RESPONSE = new ApiResponse(SysErrorCode.FAIL, ErrorCodeHelper.get(SysErrorCode.FAIL));
    }


    private int code;
    private String message;
    private Object data;
    /**
     * api接口版本号
     */
    private String version;


    public static ApiResponse getSuccResponse(String msg) {
        return getSuccResponse(msg, null);
    }

    public static ApiResponse getSuccResponse(Object retVal) {
        return getSuccResponse(ErrorCodeHelper.get(SysErrorCode.SUCCESS), retVal);
    }

    public static ApiResponse getSuccResponse(String msg, Object retVal) {
        return new ApiResponse(SysErrorCode.SUCCESS, msg, retVal);
    }

    public static ApiResponse getErrResponse(int errorCode) {
        return getErrResponse(errorCode, ErrorCodeHelper.get(errorCode));
    }

    public static ApiResponse getErrResponse(String msg) {
        return getErrResponse(SysErrorCode.BUSY, msg);
    }

    public static ApiResponse getErrResponse(int errorCode, String msg) {
        return getErrResponse(errorCode, msg, null);
    }

    public static ApiResponse getErrResponse(int errorCode, String msg, Object retVal) {
        return new ApiResponse(errorCode, msg, retVal);
    }


    public ApiResponse() {
    }

    public ApiResponse(int result, String msg) {
        this(result, msg, null);
    }

    public ApiResponse(int result, Object retVal) {
        this(result, null, retVal);
    }

    public ApiResponse(int result, String msg, Object retVal) {
        this(result, msg, retVal, Version.SERVICE_VERSION);
    }

    public ApiResponse(int result, String msg, Object retVal, String version) {
        this.code = result;
        this.message = (msg == null ? ErrorCodeHelper.get(result) : msg);
        this.data = retVal;
        this.version = version;
    }


    public int getCode() {
        return code;
    }

    public ApiResponse setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ApiResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getData() {
        return data;
    }

    public ApiResponse setData(Object data) {
        this.data = data;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
