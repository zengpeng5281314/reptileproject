package com.mytest.utils;

/**
 * 返回结果中 code 值的定义
 */
public final class ResultCodeConst {
    /**
     * 操作失败。程序异常时返回0
     */
    public static final int FAULT = 0;

    /**
     * 操作成功。
     */
    public static final int SUCCESS = 1;

    /**
     * 空数据。客户端保留
     */
    public static final int NULL = 2;

    /**
     * token过期
     */
    public static final int TOKEN_EXPIRE = 3;

    /**
     * 提示错误
     */
    public static final int PROMPT_ERROR = 4;

    /**
     * APP端保留使用
     */
    public static final int APP_KEEP = 5;

    /**
     * 服务器参数校验未通过
     */
    public static final int PARAMETER_ERROR=6;

    private ResultCodeConst() {
    }

}
