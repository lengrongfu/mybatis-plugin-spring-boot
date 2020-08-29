package com.mybatis.mybatis.plugin.process;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.update.Update;

import java.util.List;

/**
 * @program: mybatis plugin
 * @description:
 * @author: lengrongfu
 * @created: 2020/08/29 13:58
 */
public class AddUpdateFieldPluginRuleProcess implements RulePolicyProcess {

    @Override
    public void ruleProcess(Statement statement, String field, String fieldValue) {
        if (statement instanceof Update) {
            this.processUpdate((Update) statement, field, fieldValue);
        }
    }

    private void processUpdate(Update update, String field, String fieldValue) {

        List<Column> columns = update.getColumns();
        columns.add(new Column(field));

        List<Expression> expressions = update.getExpressions();
        StringValue value = new StringValue(fieldValue);
        expressions.add(value);
    }
}
