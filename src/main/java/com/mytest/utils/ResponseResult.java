package com.mytest.utils;

/**
 *
 */
public class ResponseResult<T> {
    /**
     * 是否成功相应客户端
     */
    private boolean success;

    /**
     * 返回代码
     */
    private int code;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 返回结果
     */
    private T result;

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
