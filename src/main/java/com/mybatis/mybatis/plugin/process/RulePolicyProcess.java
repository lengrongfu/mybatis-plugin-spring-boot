package com.mybatis.mybatis.plugin.process;

import net.sf.jsqlparser.statement.Statement;

/**
 * @program: mybatis plugin
 * @description:
 * @author: lengrongfu
 * @created: 2020/08/15 11:53
 */
public interface RulePolicyProcess {
    /**
     * 规则处理
     * @param statement
     * @param field
     * @param fieldValue
     */
    void ruleProcess(Statement statement, String field, String fieldValue);
}
