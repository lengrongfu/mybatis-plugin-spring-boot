package com.mybatis.mybatis.plugin.process;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.update.Update;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class AddWhereFieldPluginRuleProcessTest {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(AddWhereFieldPluginRuleProcessTest.class);

    @InjectMocks
    private AddWhereFieldPluginRuleProcess addFieldPluginRuleProcess;

    @Before
    public void setUp() throws Exception {
        logger.setLevel(Level.DEBUG);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void ruleProcess() throws JSQLParserException {
        /**
         * select
         */
        String field = "tenant_key";
        String fieldValue = "demo";
        Select select = (Select) CCJSqlParserUtil.parse("select * from user");
        addFieldPluginRuleProcess.ruleProcess(select, field, fieldValue);
        String newSql = select.toString();
        logger.info("select newSql: {}", newSql);
        assert newSql.contains("tenant_key") && newSql.contains("demo");

        /**
         * insert
         */
        Insert insert = (Insert) CCJSqlParserUtil.parse("insert into user(name) value('zs')");
        addFieldPluginRuleProcess.ruleProcess(insert, field, fieldValue);
        newSql = insert.toString();
        logger.info("insert newSql: {}", newSql);
        assert newSql.contains("tenant_key") && newSql.contains("demo");

        /**
         * update
         */
        Update update = (Update) CCJSqlParserUtil.parse("update user set name=1 where id=1");
        addFieldPluginRuleProcess.ruleProcess(update, field, fieldValue);
        newSql = update.toString();
        logger.info("update newSql: {}", newSql);
        assert newSql.contains("tenant_key") && newSql.contains("demo");

        /**
         * delete
         */
        Delete delete = (Delete) CCJSqlParserUtil.parse("delete from user where id=1");
        addFieldPluginRuleProcess.ruleProcess(delete, field, fieldValue);
        newSql = delete.toString();
        logger.info("delete newSql: {}", newSql);
        assert newSql.contains("tenant_key") && newSql.contains("demo");

    }
}