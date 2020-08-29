package com.mybatis.mybatis.plugin;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@ContextConfiguration(classes = EnablePluginConfig.class)
@RunWith(SpringRunner.class)
public class EnablePluginConfigTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void mybatisPluginInterceptor() {
    }

    @Test
    public void interceptorAwareCollect() {
    }

    @Test
    public void fieldPolicyProcess() {
    }

    @Test
    public void fieldValuePolicyProcess() {
    }

    @Test
    public void fieldValueFailPolicyProcess() {
    }

    @Test
    public void pluginRuleProcess() {
    }

    @Test
    public void ruleProcess() {
    }

    @Test
    public void pluginsProcess() {
    }

    @Test
    public void mybatisInterceptorAware() {
    }
}