package com.mybatis.mybatis.plugin.process;

import com.mybatis.mybatis.plugin.config.PluginRuleValueType;
import com.mybatis.mybatis.plugin.exception.RulePolicyNoSupportException;
import net.sf.jsqlparser.statement.Statement;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @program: mybatis plugin
 * @description:
 * @author: lengrongfu
 * @created: 2020/08/15 11:52
 */
public class PluginRuleProcessImpl implements PluginRuleProcess {

    private static Map<PluginRuleValueType, RulePolicyProcess> rulePolicyProcessMap = new HashMap<>();

    static {
        rulePolicyProcessMap.put(PluginRuleValueType.add_where_field, new AddWhereFieldPluginRuleProcess());

        AddInsertFieldPluginRuleProcess insertFieldPluginRuleProcess = new AddInsertFieldPluginRuleProcess();
        rulePolicyProcessMap.put(PluginRuleValueType.add_insert_field, insertFieldPluginRuleProcess);

        AddUpdateFieldPluginRuleProcess updateFieldPluginRuleProcess = new AddUpdateFieldPluginRuleProcess();
        rulePolicyProcessMap.put(PluginRuleValueType.add_update_field, updateFieldPluginRuleProcess);

        AddFieldPluginRuleProcess addFieldPluginRuleProcess = new AddFieldPluginRuleProcess(
                insertFieldPluginRuleProcess, updateFieldPluginRuleProcess);
        rulePolicyProcessMap.put(PluginRuleValueType.add_field, addFieldPluginRuleProcess);
    }

    @Override
    public void ruleProcess(Statement statement, PluginRuleValueType type, String field, String fieldValue) {
        RulePolicyProcess rulePolicyProcess = rulePolicyProcessMap.get(type);
        if (Objects.isNull(rulePolicyProcess)) {
            throw new RulePolicyNoSupportException(type.name());
        }
        rulePolicyProcess.ruleProcess(statement, field, fieldValue);
    }
}
