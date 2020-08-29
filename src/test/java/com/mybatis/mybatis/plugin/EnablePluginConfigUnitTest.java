package com.mybatis.mybatis.plugin;

import com.mybatis.mybatis.plugin.aware.InterceptorAwareCollect;
import com.mybatis.mybatis.plugin.aware.InterceptorAwareCollections;
import com.mybatis.mybatis.plugin.aware.MybatisInterceptorAware;
import com.mybatis.mybatis.plugin.process.PluginRuleProcess;
import com.mybatis.mybatis.plugin.process.PluginsProcess;
import com.mybatis.mybatis.plugin.process.RuleProcess;
import com.mybatis.mybatis.plugin.process.field.RuleFieldPolicyProcess;
import com.mybatis.mybatis.plugin.process.field.RuleFieldValueFailPolicyProcess;
import com.mybatis.mybatis.plugin.process.field.RuleFieldValuePolicyProcess;
import org.apache.ibatis.plugin.Interceptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
class EnablePluginConfigUnitTest {

    @InjectMocks
    private EnablePluginConfig enablePluginConfig;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void mybatisPluginInterceptor() {
        Interceptor interceptor = enablePluginConfig.mybatisPluginInterceptor();
        assert interceptor != null;
    }

    @Test
    void interceptorAwareCollect() {
        InterceptorAwareCollect interceptorAwareCollect = enablePluginConfig.interceptorAwareCollect();
        assert interceptorAwareCollect != null;
    }

    @Test
    void fieldPolicyProcess() {
        RuleFieldPolicyProcess ruleFieldPolicyProcess = enablePluginConfig.fieldPolicyProcess();
        assert ruleFieldPolicyProcess != null;
    }

    @Test
    void fieldValuePolicyProcess() {
        RuleFieldValuePolicyProcess ruleFieldValuePolicyProcess = enablePluginConfig.fieldValuePolicyProcess();
        assert ruleFieldValuePolicyProcess != null;
    }

    @Test
    void fieldValueFailPolicyProcess() {
        RuleFieldValueFailPolicyProcess ruleFieldValueFailPolicyProcess = enablePluginConfig.
                fieldValueFailPolicyProcess();
        assert ruleFieldValueFailPolicyProcess != null;
    }

    @Test
    void pluginRuleProcess() {
        PluginRuleProcess pluginRuleProcess = enablePluginConfig.pluginRuleProcess();
        assert pluginRuleProcess != null;
    }

    @Test
    void ruleProcess() {
        RuleProcess ruleProcess = enablePluginConfig.ruleProcess(null, null, null, null);
        assert ruleProcess != null;
    }

    @Test
    void pluginsProcess() {
        PluginsProcess pluginsProcess = enablePluginConfig.pluginsProcess(null);
        assert pluginsProcess != null;
    }

    @Test
    void mybatisInterceptorAware() {
        InterceptorAwareCollections interceptorAwareCollections = new InterceptorAwareCollections(null);
        MybatisInterceptorAware mybatisInterceptorAware = enablePluginConfig.mybatisInterceptorAware(null, interceptorAwareCollections);
        assert mybatisInterceptorAware != null;
    }
}