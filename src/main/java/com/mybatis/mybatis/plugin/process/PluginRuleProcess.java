package com.mybatis.mybatis.plugin.process;

import com.mybatis.mybatis.plugin.config.PluginRuleValueType;
import com.mybatis.mybatis.plugin.exception.RulePolicyNoSupportException;
import net.sf.jsqlparser.statement.Statement;

/**
 * @program: mybatis plugin
 * @description: 插件规则值处理
 * @author: lengrongfu
 * @created: 2020/08/15 09:54
 * @see PluginRuleValueType
 */
public interface PluginRuleProcess {

    /**
     * @Description: 规则处理对应逻辑接口
     * @return:
     * @Creator: lengrongfu
     * @Date: 2020/8/15 9:56 上午
     * @throws RulePolicyNoSupportException
     */
    void ruleProcess(Statement statement, PluginRuleValueType type, String field, String fieldValue);
}
