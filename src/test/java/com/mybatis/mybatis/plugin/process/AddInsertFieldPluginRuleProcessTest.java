package com.mybatis.mybatis.plugin.process;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.insert.Insert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class AddInsertFieldPluginRuleProcessTest {

    private static final Logger logger = LoggerFactory.getLogger(AddInsertFieldPluginRuleProcessTest.class);

    @InjectMocks
    private AddInsertFieldPluginRuleProcess insertFieldPluginRuleProcess;

    @Test
    public void ruleProcess() throws JSQLParserException {
        String sql = "insert into test(a,b) values(1,2)";
        Insert insert = (Insert) CCJSqlParserUtil.parse(sql);
        insertFieldPluginRuleProcess.ruleProcess(insert, "c", "3");

        logger.info("insert sql: {}", insert.toString());
        assert insert.toString().contains("c");
    }
}