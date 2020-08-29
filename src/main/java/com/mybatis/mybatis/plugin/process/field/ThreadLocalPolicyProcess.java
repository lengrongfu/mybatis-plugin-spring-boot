package com.mybatis.mybatis.plugin.process.field;

import com.mybatis.mybatis.plugin.RuleFieldThreadLocal;

/**
 * @program: mybatis plugin
 * @description: 线程缓存变量策略获取字段
 * @author: lengrongfu
 * @created: 2020/08/15 09:08
 */
public class ThreadLocalPolicyProcess implements FieldPolicyProcess, FieldValuePolicyProcess {

    /**
     * 线程缓存变量获取字段
     * 需要用户提前放入次字段值
     *
     * @param value
     * @return
     */
    @Override
    public String processFieldPolicy(String value) {
        Object variable = RuleFieldThreadLocal.getVariable(value);
        return (String) variable;
    }

    /**
     * 线程缓存变量获取字段值
     * 需要用户提前放入次字段值
     *
     * @param value
     * @return
     */
    @Override
    public String processFieldValuePolicy(String value) {
        Object variable = RuleFieldThreadLocal.getVariable(value);
        return (String) variable;
    }
}
