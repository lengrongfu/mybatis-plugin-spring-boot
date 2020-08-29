package com.mybatis.mybatis.plugin.exception;

import com.mybatis.mybatis.plugin.config.PluginRuleValueType;

/**
 * @program: mybatis plugin
 * @description:
 * @author: lengrongfu
 * @created: 2020/08/15 11:56
 */
public class RulePolicyNoSupportException extends RuntimeException {
    private String name;
    private static StringBuilder builder = new StringBuilder();

    static {
        for (PluginRuleValueType value : PluginRuleValueType.values()) {
            builder.append(value.name());
            builder.append("、");
        }
    }

    public RulePolicyNoSupportException(String name) {
        this(String.format("rule policy current only support %s,place again choose", builder.toString()), name);
    }

    public RulePolicyNoSupportException(String message, String name) {
        super(message);
        this.name = name;
    }

    public RulePolicyNoSupportException(String message, Throwable cause, String name) {
        super(message, cause);
        this.name = name;
    }

    public RulePolicyNoSupportException(Throwable cause, String name) {
        super(cause);
        this.name = name;
    }

    public RulePolicyNoSupportException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String name) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.name = name;
    }
}
