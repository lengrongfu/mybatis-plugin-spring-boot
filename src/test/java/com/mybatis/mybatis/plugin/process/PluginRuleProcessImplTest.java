package com.mybatis.mybatis.plugin.process;

import com.mybatis.mybatis.plugin.config.PluginRuleValueType;
import com.mybatis.mybatis.plugin.exception.RulePolicyNoSupportException;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.select.Select;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class PluginRuleProcessImplTest {

    @InjectMocks
    private PluginRuleProcessImpl pluginRuleProcess;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void ruleProcess() throws JSQLParserException {
        Select select = (Select) CCJSqlParserUtil.parse("select * from user");
        String field = "tenant_id";
        String fieldValue = "demo";
        try {
            pluginRuleProcess.ruleProcess(select, PluginRuleValueType.delete_field, field, fieldValue);
        } catch (Exception e) {
            assert e instanceof RulePolicyNoSupportException;
        }

        pluginRuleProcess.ruleProcess(select, PluginRuleValueType.add_where_field, field, fieldValue);
    }
}