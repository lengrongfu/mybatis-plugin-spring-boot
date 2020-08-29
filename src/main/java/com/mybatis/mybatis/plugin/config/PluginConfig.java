package com.mybatis.mybatis.plugin.config;

import java.util.List;
import java.util.Objects;

/**
 * 插件配置
 *
 * @author lengrongfuß
 */
public class PluginConfig implements Comparable<PluginConfig> {

    /**
     * 插件名称
     */
    private String name;
    /**
     * 插件执行顺序，如果没有控制就按照配置的来执行
     */
    private Integer order;
    /**
     * 可以执行sql增强的级别
     */
    private PluginLevelType level;
    /**
     * 对应增强级别的值
     */
    private List<String> value;
    /**
     * 插件规则
     */
    private List<PluginRule> rules;

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

    public PluginLevelType getLevel() {
        return level;
    }

    public void setLevel(PluginLevelType level) {
        this.level = level;
    }

    public List<String> getValue() {
        return value;
    }

    public void setValue(List<String> value) {
        this.value = value;
    }

    public List<PluginRule> getRules() {
        return rules;
    }

    public void setRules(List<PluginRule> rules) {
        this.rules = rules;
    }

    @Override
    public int compareTo(PluginConfig o2) {
        PluginConfig o1 = this;
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
}
