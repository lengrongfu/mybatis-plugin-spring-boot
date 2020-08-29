package com.mybatis.mybatis.plugin.config;

/**
 * 字段值来源策略
 *
 * @author lengrongfu
 */
public class RuleFieldValuePolicy {
    /**
     * @Description: 字段值来源策略
     * @return:
     * @Creator: lengrongfu
     * @Date: 2020/8/14 5:16 下午
     */
    private RuleFieldValuePolicyType name;

    /**
     * 字段值来源策略值
     */
    private String value;

    public RuleFieldValuePolicyType getName() {
        return name;
    }

    public void setName(RuleFieldValuePolicyType name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
