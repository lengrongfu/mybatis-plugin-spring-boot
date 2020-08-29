package com.mybatis.mybatis.plugin.process;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.mybatis.mybatis.plugin.config.MybatisPluginsConfig;
import com.mybatis.mybatis.plugin.config.PluginConfig;
import com.mybatis.mybatis.plugin.config.PluginLevelType;
import com.mybatis.mybatis.plugin.config.PluginRule;
import com.mybatis.mybatis.plugin.config.PluginRuleValueType;
import com.mybatis.mybatis.plugin.config.RuleFieldPolicy;
import com.mybatis.mybatis.plugin.config.RuleFieldPolicyType;
import com.mybatis.mybatis.plugin.config.RuleFieldValueFailPolicyType;
import com.mybatis.mybatis.plugin.config.RuleFieldValuePolicy;
import com.mybatis.mybatis.plugin.config.RuleFieldValuePolicyType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
public class PluginsProcessImplTest {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(PluginsProcessImplTest.class);

    @InjectMocks
    private PluginsProcessImpl pluginsProcess;

    @Mock
    private MybatisPluginsConfig mybatisPluginsConfig;

    @Mock
    private RuleProcess ruleProcess;

    @Before
    public void setUp() throws Exception {
        logger.setLevel(Level.DEBUG);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void pluginsProcess() {
        String oldSql = "select * from user";
        List<PluginConfig> pluginConfigs = new ArrayList<>();
        Mockito.doReturn(pluginConfigs).when(mybatisPluginsConfig).getPlugins();
        String newSql = pluginsProcess.pluginsProcess(oldSql);

        assert newSql.equals(oldSql);


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
        newSql = pluginsProcess.pluginsProcess(oldSql);
        logger.info("newSql {}", newSql);
        assert newSql != null;

    }
}