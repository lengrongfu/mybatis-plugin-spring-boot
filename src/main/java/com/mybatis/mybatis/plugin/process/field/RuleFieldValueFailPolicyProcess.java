package com.mybatis.mybatis.plugin.process.field;

import com.mybatis.mybatis.plugin.config.PluginRule;
import com.mybatis.mybatis.plugin.config.RuleFieldValueFailPolicyType;
import com.mybatis.mybatis.plugin.exception.RuleProcessPolicyException;

/**
 * @program: mybatis plugin
 * @description:
 * @author: lengrongfu
 * @created: 2020/08/15 08:45
 */
public class RuleFieldValueFailPolicyProcess {

    /**
     * 获取字段值失败后执行策略
     *
     * @param type
     * @param rule
     */
    public void fieldValueFailPolicyProcess(RuleFieldValueFailPolicyType type, PluginRule rule) {
        if (RuleFieldValueFailPolicyType.run.equals(type)) {
        } else if (RuleFieldValueFailPolicyType.stop.equals(type)) {
            throw new RuleProcessPolicyException(rule);
        }
    }
}
