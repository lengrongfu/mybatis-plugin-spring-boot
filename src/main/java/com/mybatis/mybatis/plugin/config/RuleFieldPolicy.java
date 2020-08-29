package com.mybatis.mybatis.plugin.config;

/**
 * 字段策略
 *
 * @author lengrongfu
 */
public class RuleFieldPolicy {

    /**
     * @Description: 字段来源策略
     * @return:
     * @Creator: lengrongfu
     * @Date: 2020/8/14 5:15 下午
     */
    private RuleFieldPolicyType name;

    /**
     * @Description: 字段来源策略对应值
     * @return:
     * @Creator: lengrongfu
     * @Date: 2020/8/14 5:16 下午
     */
    private String value;


    public RuleFieldPolicyType getName() {
        return name;
    }

    public void setName(RuleFieldPolicyType name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
