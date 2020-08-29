package com.mybatis.mybatis.plugin.process;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.update.Update;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class AddUpdateFieldPluginRuleProcessTest {

    private static final Logger logger = LoggerFactory.getLogger(AddUpdateFieldPluginRuleProcessTest.class);


    @InjectMocks
    private AddUpdateFieldPluginRuleProcess updateFieldPluginRuleProcess;


    @Test
    public void ruleProcess() throws JSQLParserException {
        String sql = "update test set a=1,b=2 where id=1";

        Update update = (Update) CCJSqlParserUtil.parse(sql);
        updateFieldPluginRuleProcess.ruleProcess(update, "c", "3");

        logger.info("sql {}", update.toString());
        assert update.toString().contains("c = '3'");

    }
}