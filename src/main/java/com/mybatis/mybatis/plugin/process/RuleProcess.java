package com.mybatis.mybatis.plugin.process;

import com.mybatis.mybatis.plugin.config.PluginRule;
import com.mybatis.mybatis.plugin.exception.RuleProcessPolicyException;
import net.sf.jsqlparser.statement.Statement;

/**
 * @program:
 * @description: 规则处理器
 * @author: lengrongfu
 * @created: 2020/08/15 08:21
 */
public interface RuleProcess {

    /**
     * @throws RuleProcessPolicyException
     * @Description: 规则处理器逻辑
     * @return:
     * @Creator: lengrongfu
     * @Date: 2020/8/15 8:25 上午
     */
    void ruleProcess(Statement statement, PluginRule rule);
}
