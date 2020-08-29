package com.mybatis.mybatis.plugin.process;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.update.Update;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class AddFieldPluginRuleProcessTest {

    private AddFieldPluginRuleProcess addFieldPluginRuleProcess;

    @MockBean
    private AddInsertFieldPluginRuleProcess insertFieldPluginRuleProcess;

    @MockBean
    private AddUpdateFieldPluginRuleProcess updateFieldPluginRuleProcess;

    @Before
    public void setup() {

        addFieldPluginRuleProcess = new AddFieldPluginRuleProcess(insertFieldPluginRuleProcess,
                updateFieldPluginRuleProcess);
    }

    @Test
    public void ruleProcess() throws JSQLParserException {
        String sql = "update test set a=1,b=2 where id=1";
        Update update = (Update) CCJSqlParserUtil.parse(sql);

        Mockito.doNothing().when(updateFieldPluginRuleProcess).ruleProcess(update, "c", "3");
        addFieldPluginRuleProcess.ruleProcess(update, "c", "3");

        String insertSql = "insert into test(a,b) values(1,2)";
        Insert insert = (Insert) CCJSqlParserUtil.parse(insertSql);
        Mockito.doNothing().when(insertFieldPluginRuleProcess).ruleProcess(insert, "c", "3");
        addFieldPluginRuleProcess.ruleProcess(update, "c", "3");
    }
}