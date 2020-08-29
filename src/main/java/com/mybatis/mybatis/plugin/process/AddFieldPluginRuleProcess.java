package com.mybatis.mybatis.plugin.process;

import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.update.Update;

/**
 * @program: mybatis plugin
 * @description:
 * @author: lengrongfu
 * @created: 2020/08/29 13:58
 */
public class AddFieldPluginRuleProcess implements RulePolicyProcess {

    private AddInsertFieldPluginRuleProcess insertFieldPluginRuleProcess;

    private AddUpdateFieldPluginRuleProcess updateFieldPluginRuleProcess;


    public AddFieldPluginRuleProcess(AddInsertFieldPluginRuleProcess insertFieldPluginRuleProcess,
                                     AddUpdateFieldPluginRuleProcess updateFieldPluginRuleProcess) {
        this.insertFieldPluginRuleProcess = insertFieldPluginRuleProcess;
        this.updateFieldPluginRuleProcess = updateFieldPluginRuleProcess;
    }

    @Override
    public void ruleProcess(Statement statement, String field, String fieldValue) {
        if (statement instanceof Insert) {
            insertFieldPluginRuleProcess.ruleProcess(statement, field, fieldValue);
        } else if (statement instanceof Update) {
            updateFieldPluginRuleProcess.ruleProcess(statement, field, fieldValue);
        }
    }
}
