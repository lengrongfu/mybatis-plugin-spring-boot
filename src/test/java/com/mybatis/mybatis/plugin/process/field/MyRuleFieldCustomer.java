package com.mybatis.mybatis.plugin.process.field;

import com.mybatis.mybatis.plugin.RuleFieldCustomer;

/**
 * @program: mybatis plugin
 * @description:
 * @author: lengrongfu
 * @created: 2020/08/29 12:43
 */
public class MyRuleFieldCustomer implements RuleFieldCustomer {
    /**
     * 反射时要有空的构造函数
     */
    public MyRuleFieldCustomer() {
    }

    @Override
    public String fieldKey() {
        return "tenant_key";
    }
}
