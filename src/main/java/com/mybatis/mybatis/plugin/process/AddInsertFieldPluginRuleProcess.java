package com.mybatis.mybatis.plugin.process;

import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.expression.operators.relational.MultiExpressionList;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.insert.Insert;

/**
 * @program: mybatis plugin
 * @description:
 * @author: lengrongfu
 * @created: 2020/08/29 13:57
 */
public class AddInsertFieldPluginRuleProcess implements RulePolicyProcess {

    @Override
    public void ruleProcess(Statement statement, String field, String fieldValue) {
        if (statement instanceof Insert) {
            this.processInsert((Insert) statement, field, fieldValue);
        }
    }

    private void processInsert(Insert insert, String field, String fieldValue) {
        insert.getColumns().add(new Column(field));
        if (insert.getSelect() != null) {
//            processPlainSelect((PlainSelect) insert.getSelect().getSelectBody(), true,field,fieldValue);
            // TODO insert into select 模式暂不支持
        } else if (insert.getItemsList() != null) {
            // fixed github pull/295
            ItemsList itemsList = insert.getItemsList();
            if (itemsList instanceof MultiExpressionList) {
                ((MultiExpressionList) itemsList).getExprList().forEach(el ->
                        el.getExpressions().add(new StringValue(fieldValue)));
            } else {
                ((ExpressionList) insert.getItemsList()).getExpressions().add(new StringValue(fieldValue));
            }
        } else {
            throw new RuntimeException("Failed to process multiple-table update, please exclude the tableName or statementId");
        }
    }
}
