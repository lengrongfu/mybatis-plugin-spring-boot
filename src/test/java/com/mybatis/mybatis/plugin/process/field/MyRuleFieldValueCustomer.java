package com.mybatis.mybatis.plugin.process.field;

import com.mybatis.mybatis.plugin.RuleFieldValueCustomer;

/**
 * @program: mybatis plugin
 * @description:
 * @author: lengrongfu
 * @created: 2020/08/29 12:43
 */
public class MyRuleFieldValueCustomer implements RuleFieldValueCustomer {

    public MyRuleFieldValueCustomer() {
    }

    @Override
    public String fieldValueKey() {
        return "demo";
    }
}
