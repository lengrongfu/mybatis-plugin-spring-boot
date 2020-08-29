package com.mybatis.mybatis.plugin.config;

public enum RuleFieldValuePolicyType {

    /**
     * 字段从线程变量中获取
     */
    threadLocal,
    /**
     * 字段从配置文件中获取
     */
    conf,
    /**
     * 字段通过自定义实现接口获取
     */
    customer,

    /**
     * 系统内嵌一些方式
     */
    system;
}
