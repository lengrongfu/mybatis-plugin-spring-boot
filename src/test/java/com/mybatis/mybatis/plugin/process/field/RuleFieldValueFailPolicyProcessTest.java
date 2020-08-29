package com.mybatis.mybatis.plugin.process.field;

import com.mybatis.mybatis.plugin.config.PluginRule;
import com.mybatis.mybatis.plugin.config.RuleFieldValueFailPolicyType;
import com.mybatis.mybatis.plugin.exception.RuleProcessPolicyException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class RuleFieldValueFailPolicyProcessTest {

    @InjectMocks
    private RuleFieldValueFailPolicyProcess ruleFieldValueFailPolicyProcess;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void fieldValueFailPolicyProcess() {
        PluginRule rule = new PluginRule();
        rule.setName("add_create_time");
        ruleFieldValueFailPolicyProcess.fieldValueFailPolicyProcess(RuleFieldValueFailPolicyType.run, rule);

        try {
            ruleFieldValueFailPolicyProcess.fieldValueFailPolicyProcess(RuleFieldValueFailPolicyType.stop, rule);
        } catch (Exception e) {
            assert e instanceof RuleProcessPolicyException;
        }
    }
}