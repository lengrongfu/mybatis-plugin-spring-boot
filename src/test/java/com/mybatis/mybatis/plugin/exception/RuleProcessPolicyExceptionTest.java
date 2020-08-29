package com.mybatis.mybatis.plugin.exception;

import com.mybatis.mybatis.plugin.config.PluginRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
public class RuleProcessPolicyExceptionTest {

    @Test
    public void throwException() {
        PluginRule rule = new PluginRule();
        rule.setName("rule");
        try {
            throw new RuleProcessPolicyException(rule);
        } catch (RuleProcessPolicyException e) {
            assert e.getMessage().contains("rule");
        }
    }
}