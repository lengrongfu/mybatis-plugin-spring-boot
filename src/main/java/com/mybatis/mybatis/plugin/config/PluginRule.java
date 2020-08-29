package com.mybatis.mybatis.plugin.config;

import java.util.Objects;

/**
 * 插件规则
 *
 * @author lengrongfu
 */
public class PluginRule implements Comparable<PluginRule> {

    /**
     * 规则名称
     */
    private String name;

    /**
     * 规则执行顺序
     */
    private Integer order;

    /**
     * 插件规则值类型，定义了几种通用的值类型
     */
    private PluginRuleValueType value;
    /**
     * 规则字段策略
     */
    private RuleFieldPolicy fieldPolicy;
    /**
     * 规则字段值策略
     */
    private RuleFieldValuePolicy fieldValuePolicy;
    /**
     * 规则字段值获取失败策略
     */
    private RuleFieldValueFailPolicyType fieldValueFailPolicy;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public PluginRuleValueType getValue() {
        return value;
    }

    public void setValue(PluginRuleValueType value) {
        this.value = value;
    }

    public RuleFieldPolicy getFieldPolicy() {
        return fieldPolicy;
    }

    public void setFieldPolicy(RuleFieldPolicy fieldPolicy) {
        this.fieldPolicy = fieldPolicy;
    }

    public RuleFieldValuePolicy getFieldValuePolicy() {
        return fieldValuePolicy;
    }

    public void setFieldValuePolicy(RuleFieldValuePolicy fieldValuePolicy) {
        this.fieldValuePolicy = fieldValuePolicy;
    }

    public RuleFieldValueFailPolicyType getFieldValueFailPolicy() {
        return fieldValueFailPolicy;
    }

    public void setFieldValueFailPolicy(RuleFieldValueFailPolicyType fieldValueFailPolicy) {
        this.fieldValueFailPolicy = fieldValueFailPolicy;
    }

    @Override
    public int compareTo(PluginRule o2) {
        PluginRule o1 = this;
        if (Objects.isNull(o2)) {
            return -1;
        }
        if (Objects.isNull(o1.getOrder()) && Objects.isNull(o2.getOrder())) {
            return 0;
        }
        if (Objects.nonNull(o1.getOrder()) && Objects.isNull(o2.getOrder())) {
            return -1;
        }
        if (Objects.isNull(o1.getOrder()) && Objects.nonNull(o2.getOrder())) {
            return 1;
        }
        if (o1.getOrder().equals(o2.getOrder())) {
            return 0;
        }
        return o1.getOrder() > o2.getOrder() ? 1 : -1;
    }

    @Override
    public String toString() {
        return "PluginRule{" +
                "name='" + name + '\'' +
                ", order=" + order +
                ", value=" + value +
                ", fieldPolicy=" + fieldPolicy +
                ", fieldValuePolicy=" + fieldValuePolicy +
                ", fieldValueFailPolicy=" + fieldValueFailPolicy +
                '}';
    }
}
