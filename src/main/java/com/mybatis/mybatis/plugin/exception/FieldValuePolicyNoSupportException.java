package com.mybatis.mybatis.plugin.exception;

import com.mybatis.mybatis.plugin.config.RuleFieldPolicyType;

/**
 * @program: mybatis plugin
 * @description: 字段值策略配置不支持异常
 * @author: lengrongfu
 * @created: 2020/08/15 09:22
 */
public class FieldValuePolicyNoSupportException extends RuntimeException {
    private String name;
    private static StringBuilder builder = new StringBuilder();

    static {
        RuleFieldPolicyType[] values = RuleFieldPolicyType.values();
        for (RuleFieldPolicyType value : values) {
            builder.append(value.name());
            builder.append("、");
        }
    }

    public FieldValuePolicyNoSupportException(String name) {
        this(String.format("field policy current only support %s,place again choose", builder.toString()), name);
    }

    public FieldValuePolicyNoSupportException(String message, String name) {
        super(message);
        this.name = name;
    }

    public FieldValuePolicyNoSupportException(String message, Throwable cause, String name) {
        super(message, cause);
        this.name = name;
    }

    public FieldValuePolicyNoSupportException(Throwable cause, String name) {
        super(cause);
        this.name = name;
    }

    public FieldValuePolicyNoSupportException(String message, Throwable cause, boolean enableSuppression,
                                              boolean writableStackTrace, String name) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.name = name;
    }
}
