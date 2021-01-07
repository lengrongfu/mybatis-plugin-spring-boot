package com.mybatis.mybatis.plugin.process;

import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.*;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.statement.update.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @program: mybatis plugin
 * @description: 修改表名
 * @author: hesheng
 * @created: 2021/1/7 18:17 下午
 */
public class ChangeTableNamePluginRuleProcess implements RulePolicyProcess {

    private static final Logger logger = LoggerFactory.getLogger(ChangeTableNamePluginRuleProcess.class);

    /**
     * @param statement  sql
     * @param field      字段
     * @param fieldValue 字段值
     */
    @Override
    public void ruleProcess(Statement statement, String field, String fieldValue) {
        if (statement instanceof Insert) {
            this.processInsert((Insert) statement, field, fieldValue);
        } else if (statement instanceof Select) {
            this.processSelectBody(((Select) statement).getSelectBody(), field, fieldValue);
        } else if (statement instanceof Update) {
            this.processUpdate((Update) statement, field, fieldValue);
        } else if (statement instanceof Delete) {
            this.processDelete((Delete) statement, field, fieldValue);
        }
    }

    /**
     * insert 语句处理
     */

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

    /**
     * select 语句处理
     */

    private void processSelectBody(SelectBody selectBody, String field, String fieldValue) {
        if (selectBody instanceof PlainSelect) {
            processPlainSelect((PlainSelect) selectBody, false, field, fieldValue);
        }
//        else if (selectBody instanceof WithItem) {
//            WithItem withItem = (WithItem) selectBody;
//            if (withItem.getSelectBody() != null) {
//                processSelectBody(withItem.getSelectBody(), field, fieldValue);
//            }
//        } else if (selectBody instanceof SetOperationList) {
//            SetOperationList operationList = (SetOperationList) selectBody;
//            if (operationList.getSelects() != null && operationList.getSelects().size() > 0) {
//                operationList.getSelects().forEach(item -> {
//                    processSelectBody(item, field, fieldValue);
//                });
//            }
//        } else if (selectBody instanceof ValuesStatement) {
//            ValuesStatement valuesStatement = (ValuesStatement) selectBody;
//        }
    }

    /**
     * update 语句处理
     */

    private void processUpdate(Update update, String field, String fieldValue) {
        Table table = update.getTables().get(0);
        update.setWhere(this.andExpression(table, update.getWhere(), field, fieldValue));
    }

    /**
     * delete 语句处理
     */

    private void processDelete(Delete delete, String field, String fieldValue) {
        delete.setWhere(this.andExpression(delete.getTable(), delete.getWhere(), field, fieldValue));
    }

    /**
     * 处理 PlainSelect
     *
     * @param plainSelect ignore
     * @param addColumn   是否添加租户列,insert into select语句中需要
     */
    private void processPlainSelect(PlainSelect plainSelect, boolean addColumn, String field, String fieldValue) {
        FromItem fromItem = plainSelect.getFromItem();
        if (fromItem instanceof Table) {
            Table fromTable = (Table) fromItem;
            //#1186 github
            plainSelect.setWhere(builderExpression(plainSelect.getWhere(), fromTable, field, fieldValue));
            if (addColumn) {
                plainSelect.getSelectItems().add(new SelectExpressionItem(
                        new Column(field)));
            }
        } else {
            processFromItem(fromItem, field, fieldValue);
        }
        List<Join> joins = plainSelect.getJoins();
        if (joins != null && joins.size() > 0) {
            joins.forEach(j -> {
                processJoin(j, field, fieldValue);
                processFromItem(j.getRightItem(), field, fieldValue);
            });
        }
    }

    /**
     * 处理子查询等
     */
    private void processFromItem(FromItem fromItem, String field, String fieldValue) {
        if (fromItem instanceof SubJoin) {
            SubJoin subJoin = (SubJoin) fromItem;
            if (subJoin.getJoin() != null) {
                Join join = subJoin.getJoin();
                processJoin(join, field, fieldValue);
            }
            if (subJoin.getLeft() != null) {
                processFromItem(subJoin.getLeft(), field, fieldValue);
            }
        } else if (fromItem instanceof SubSelect) {
            SubSelect subSelect = (SubSelect) fromItem;
            if (subSelect.getSelectBody() != null) {
                processSelectBody(subSelect.getSelectBody(), field, fieldValue);
            }
        } else if (fromItem instanceof ValuesList) {
            logger.debug("Perform a subquery, if you do not give us feedback");
        } else if (fromItem instanceof LateralSubSelect) {
            LateralSubSelect lateralSubSelect = (LateralSubSelect) fromItem;
            if (lateralSubSelect.getSubSelect() != null) {
                SubSelect subSelect = lateralSubSelect.getSubSelect();
                if (subSelect.getSelectBody() != null) {
                    processSelectBody(subSelect.getSelectBody(), field, fieldValue);
                }
            }
        }
    }

    /**
     * delete update 语句 where 处理
     */
    private BinaryExpression andExpression(Table table, Expression where, String field, String fieldValue) {
        //获得where条件表达式
        EqualsTo equalsTo = new EqualsTo();

        equalsTo.setLeftExpression(this.getAliasColumn(table, field));
        equalsTo.setRightExpression(new StringValue(fieldValue));
        if (null != where) {
            if (where instanceof OrExpression) {
                return new AndExpression(equalsTo, new Parenthesis(where));
            } else {
                return new AndExpression(equalsTo, where);
            }
        }
        return equalsTo;
    }

    /**
     * 租户字段别名设置
     * <p>tableName.tenantId 或 tableAlias.tenantId</p>
     *
     * @param table 表对象
     * @return 字段
     */
    private Column getAliasColumn(Table table, String field) {
        StringBuilder column = new StringBuilder();
        if (null == table.getAlias()) {
            column.append(table.getName());
        } else {
            column.append(table.getAlias().getName());
        }
        column.append(".");
        column.append(field);
        return new Column(column.toString());
    }

    /**
     * 处理条件:
     * 支持 getTenantHandler().getTenantId()是一个完整的表达式：tenant in (1,2)
     * 默认tenantId的表达式： LongValue(1)这种依旧支持
     */
    private Expression builderExpression(Expression currentExpression, Table table, String field, String fieldValue) {
        final Expression tenantExpression = new StringValue(fieldValue);
        Expression appendExpression;
        if (!(tenantExpression instanceof SupportsOldOracleJoinSyntax)) {
            appendExpression = new EqualsTo();
            ((EqualsTo) appendExpression).setLeftExpression(this.getAliasColumn(table, field));
            ((EqualsTo) appendExpression).setRightExpression(tenantExpression);
        } else {
            appendExpression = processTableAlias4CustomizedTenantIdExpression(tenantExpression, table);
        }
        if (currentExpression == null) {
            return appendExpression;
        }
        if (currentExpression instanceof BinaryExpression) {
            BinaryExpression binaryExpression = (BinaryExpression) currentExpression;
            doExpression(binaryExpression.getLeftExpression(), field, fieldValue);
            doExpression(binaryExpression.getRightExpression(), field, fieldValue);
        } else if (currentExpression instanceof InExpression) {
            InExpression inExp = (InExpression) currentExpression;
            ItemsList rightItems = inExp.getRightItemsList();
            if (rightItems instanceof SubSelect) {
                processSelectBody(((SubSelect) rightItems).getSelectBody(), field, fieldValue);
            }
        }
        if (currentExpression instanceof OrExpression) {
            return new AndExpression(new Parenthesis(currentExpression), appendExpression);
        } else {
            return new AndExpression(currentExpression, appendExpression);
        }
    }

    /**
     * 处理联接语句
     */
    private void processJoin(Join join, String field, String fieldValue) {
        if (join.getRightItem() instanceof Table) {
            Table fromTable = (Table) join.getRightItem();
            join.setOnExpression(builderExpression(join.getOnExpression(), fromTable, field, fieldValue));
        }
    }

    private void doExpression(Expression expression, String field, String fieldValue) {
        if (expression instanceof FromItem) {
            processFromItem((FromItem) expression, field, fieldValue);
        } else if (expression instanceof InExpression) {
            InExpression inExp = (InExpression) expression;
            ItemsList rightItems = inExp.getRightItemsList();
            if (rightItems instanceof SubSelect) {
                processSelectBody(((SubSelect) rightItems).getSelectBody(), field, fieldValue);
            }
        }
    }

    /**
     * 目前: 针对自定义的tenantId的条件表达式[tenant_id in (1,2,3)]，无法处理多租户的字段加上表别名
     * select a.id, b.name
     * from a
     * join b on b.aid = a.id and [b.]tenant_id in (1,2) --别名[b.]无法加上 TODO
     *
     * @param expression
     * @param table
     * @return 加上别名的多租户字段表达式
     */
    private Expression processTableAlias4CustomizedTenantIdExpression(Expression expression, Table table) {
        //cannot add table alias for customized tenantId expression,
        // when tables including tenantId at the join table poistion
        return expression;
    }
}
