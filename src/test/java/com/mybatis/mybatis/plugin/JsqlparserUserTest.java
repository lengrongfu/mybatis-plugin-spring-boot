package com.mybatis.mybatis.plugin;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.IsNullExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statements;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.util.SelectUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @program: mybatis plugin
 * @description:
 * @author: lengrongfu
 * @created: 2020/08/15 11:22
 */
@RunWith(SpringRunner.class)
public class JsqlparserUserTest {

    /**
     * 测试查询返回增加一列
     */
    @Test
    public void testAddSelectColumn() throws Exception {
        Select select = (Select) CCJSqlParserUtil.parse("select name from user where id = 1");
        SelectUtils.addExpression(select, new Column("mail"));
        Assert.assertEquals(select.toString(), "SELECT name, mail FROM user WHERE id = 1");
    }

    /**
     * 测试查询语句增加where条件
     */
    @Test
    public void testAddWhereCondition() throws Exception {
        Select select = (Select) CCJSqlParserUtil.parse("select name from user");
        PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
        if (plainSelect.getWhere() == null) {
            EqualsTo equalsTo = new EqualsTo();
            equalsTo.setLeftExpression(new Column("id"));
            equalsTo.setRightExpression(new LongValue(1000L));
            plainSelect.setWhere(equalsTo);
        }
        Assert.assertEquals(select.toString(), "SELECT name FROM user WHERE id = 1000");
    }

    /**
     * 测试增加where查询条件
     */
    @Test
    public void testAddCondition() throws Exception {
        Select select = (Select) CCJSqlParserUtil.parse("select name from user where id = 1000");
        PlainSelect plainSelect = (PlainSelect) select.getSelectBody();

        // 原where表达式
        Expression where = plainSelect.getWhere();
        // 新增的查询条件表达式
        EqualsTo equalsTo = new EqualsTo();
        equalsTo.setLeftExpression(new Column("name"));
        equalsTo.setRightExpression(new StringValue("'张三'"));
        // 用and链接条件
        AndExpression and = new AndExpression(where, equalsTo);
        // 设置新的where条件
        plainSelect.setWhere(and);

        Assert.assertEquals(select.toString(), "SELECT name FROM user WHERE id = 1000 AND name = '张三'");
    }


    /**
     * 测试null条件
     */
    @Test
    public void testNullCondition() throws Exception {
        Select select = (Select) CCJSqlParserUtil.parse("select name from user where id = 1000");
        PlainSelect plainSelect = (PlainSelect) select.getSelectBody();

        // 原where表达式
        Expression where = plainSelect.getWhere();
        // 新增的null判断条件
        IsNullExpression isNullExpression = new IsNullExpression();
        isNullExpression.setLeftExpression(new Column("name"));
        isNullExpression.setNot(true);
        // 用and链接条件
        AndExpression and = new AndExpression(where, isNullExpression);
        // 设置新的where条件
        plainSelect.setWhere(and);

        Assert.assertEquals(select.toString(), "SELECT name FROM user WHERE id = 1000 AND name IS NOT NULL");
    }

    @Test
    public void testInsertInto() throws JSQLParserException {
        Insert insert = (Insert) CCJSqlParserUtil.parse("insert into test.user(name,age) values('张三',10)");
        Table table = insert.getTable();
        System.out.println(table.getName());
        System.out.println(table.getFullyQualifiedName());

    }

    @Test
    public void testParseStatements() throws JSQLParserException {
        String sql = "select * from user;select * from student";
        Statements statements = CCJSqlParserUtil.parseStatements(sql);
        assert statements.getStatements().size() == 2;
    }
}
