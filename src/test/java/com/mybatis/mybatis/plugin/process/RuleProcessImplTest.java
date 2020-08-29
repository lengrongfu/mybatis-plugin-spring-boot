package com.mybatis.mybatis.plugin.process;

import ch.qos.logback.classic.Logger;
import com.mybatis.mybatis.plugin.config.PluginConfig;
import com.mybatis.mybatis.plugin.config.PluginLevelType;
import com.mybatis.mybatis.plugin.config.PluginRule;
import com.mybatis.mybatis.plugin.config.PluginRuleValueType;
import com.mybatis.mybatis.plugin.config.RuleFieldPolicy;
import com.mybatis.mybatis.plugin.config.RuleFieldPolicyType;
import com.mybatis.mybatis.plugin.config.RuleFieldValueFailPolicyType;
import com.mybatis.mybatis.plugin.config.RuleFieldValuePolicy;
import com.mybatis.mybatis.plugin.config.RuleFieldValuePolicyType;
import com.mybatis.mybatis.plugin.process.field.RuleFieldPolicyProcess;
import com.mybatis.mybatis.plugin.process.field.RuleFieldPolicyProcessImpl;
import com.mybatis.mybatis.plugin.process.field.RuleFieldValueFailPolicyProcess;
import com.mybatis.mybatis.plugin.process.field.RuleFieldValuePolicyProcess;
import com.mybatis.mybatis.plugin.process.field.RuleFieldValuePolicyProcessImpl;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.select.Select;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
public class RuleProcessImplTest {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(RuleProcessImplTest.class);

    private RuleProcessImpl ruleProcess;

    @Before
    public void setUp() throws Exception {
        RuleFieldPolicyProcess fieldPolicyProcess = new RuleFieldPolicyProcessImpl();
        RuleFieldValuePolicyProcess fieldValuePolicyProcess = new RuleFieldValuePolicyProcessImpl();
        RuleFieldValueFailPolicyProcess fieldValueFailPolicyProcess = new RuleFieldValueFailPolicyProcess();
        PluginRuleProcess pluginRuleProcess = new PluginRuleProcessImpl();
        ruleProcess = new RuleProcessImpl(fieldPolicyProcess,
                fieldValuePolicyProcess,
                fieldValueFailPolicyProcess,
                pluginRuleProcess);
    }

    @Test
    public void ruleProcess() throws JSQLParserException {
        String oldSql = "select * from user";
        Select select = (Select) CCJSqlParserUtil.parse(oldSql);
        List<PluginConfig> pluginConfigs = new ArrayList<>();

        PluginConfig pluginConfig = new PluginConfig();
        pluginConfig.setLevel(PluginLevelType.dml);
        pluginConfig.setValue(Arrays.asList("Select"));

        List<PluginRule> rules = new ArrayList<>();
        PluginRule rule = new PluginRule();
        rule.setName("add_field");
        rule.setValue(PluginRuleValueType.add_where_field);
        RuleFieldPolicy ruleFieldPolicy = new RuleFieldPolicy();
        ruleFieldPolicy.setName(RuleFieldPolicyType.conf);
        ruleFieldPolicy.setValue("tenant_id");
        rule.setFieldPolicy(ruleFieldPolicy);

        RuleFieldValuePolicy ruleFieldValuePolicy = new RuleFieldValuePolicy();
        ruleFieldValuePolicy.setName(RuleFieldValuePolicyType.conf);
        ruleFieldValuePolicy.setValue("demo");
        rule.setFieldValuePolicy(ruleFieldValuePolicy);

        rule.setFieldValueFailPolicy(RuleFieldValueFailPolicyType.run);
        rules.add(rule);
        pluginConfig.setRules(rules);
        pluginConfigs.add(pluginConfig);
        ruleProcess.ruleProcess(select, rule);

        String newSql = select.toString();
        logger.info("newSql {}", newSql);
        assert newSql.contains("tenant_id");
    }
}