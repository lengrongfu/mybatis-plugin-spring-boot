package com.mybatis.mybatis.plugin.exception;

import com.mybatis.mybatis.plugin.config.PluginRule;

/**
 * @program: mybatis plugin
 * @description: field-value-fail-policy
 * @author: lengrongfu
 * @created: 2020/08/15 08:29
 */
public class RuleProcessPolicyException extends RuntimeException {
    private PluginRule rule;

    public RuleProcessPolicyException(PluginRule rule) {
        this(rule.toString());
    }

    public RuleProcessPolicyException(String message) {
        super(message);
    }

    public RuleProcessPolicyException(String message, Throwable cause) {
        super(message, cause);
    }

    public RuleProcessPolicyException(Throwable cause) {
        super(cause);
    }

    public RuleProcessPolicyException(String message, Throwable cause,
                                      boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
