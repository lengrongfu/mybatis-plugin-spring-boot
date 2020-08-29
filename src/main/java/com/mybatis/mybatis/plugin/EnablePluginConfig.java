package com.mybatis.mybatis.plugin;

import com.mybatis.mybatis.plugin.aware.InterceptorAware;
import com.mybatis.mybatis.plugin.aware.InterceptorAwareCollect;
import com.mybatis.mybatis.plugin.aware.InterceptorAwareCollections;
import com.mybatis.mybatis.plugin.aware.MybatisInterceptorAware;
import com.mybatis.mybatis.plugin.config.MybatisPluginsConfig;
import com.mybatis.mybatis.plugin.process.PluginRuleProcess;
import com.mybatis.mybatis.plugin.process.PluginRuleProcessImpl;
import com.mybatis.mybatis.plugin.process.PluginsProcess;
import com.mybatis.mybatis.plugin.process.PluginsProcessImpl;
import com.mybatis.mybatis.plugin.process.RuleProcess;
import com.mybatis.mybatis.plugin.process.RuleProcessImpl;
import com.mybatis.mybatis.plugin.process.field.RuleFieldPolicyProcess;
import com.mybatis.mybatis.plugin.process.field.RuleFieldPolicyProcessImpl;
import com.mybatis.mybatis.plugin.process.field.RuleFieldValueFailPolicyProcess;
import com.mybatis.mybatis.plugin.process.field.RuleFieldValuePolicyProcess;
import com.mybatis.mybatis.plugin.process.field.RuleFieldValuePolicyProcessImpl;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: mybatis plugin
 * @description:
 * @author: lengrongfu
 * @created: 2020/08/15 19:16
 */

@Configuration
@ConditionalOnWebApplication
@EnableConfigurationProperties(MybatisPluginsConfig.class)
@ConditionalOnProperty(
        prefix = "mybatis plugin",
        name = {"enable"},
        havingValue = "true"
)
public class EnablePluginConfig {

    List<InterceptorAware> mybatisInterceptorAwares = new ArrayList<>();

    @Autowired
    private MybatisPluginsConfig mybatisPluginsConfig;

    @Bean(name = "mybatisPluginInterceptor")
    public Interceptor mybatisPluginInterceptor() {
        return new MybatisPluginInterceptor(mybatisInterceptorAwares);
    }

    @Bean
    public InterceptorAwareCollect interceptorAwareCollect() {
        return new InterceptorAwareCollections(mybatisInterceptorAwares);
    }

    @Bean
    public RuleFieldPolicyProcess fieldPolicyProcess() {
        return new RuleFieldPolicyProcessImpl();
    }

    @Bean
    public RuleFieldValuePolicyProcess fieldValuePolicyProcess() {
        return new RuleFieldValuePolicyProcessImpl();
    }

    @Bean
    public RuleFieldValueFailPolicyProcess fieldValueFailPolicyProcess() {
        return new RuleFieldValueFailPolicyProcess();
    }

    @Bean
    public PluginRuleProcess pluginRuleProcess() {
        return new PluginRuleProcessImpl();
    }

    @Bean
    @DependsOn({"fieldPolicyProcess", "fieldValuePolicyProcess", "fieldValueFailPolicyProcess", "pluginRuleProcess"})
    public RuleProcess ruleProcess(RuleFieldPolicyProcess fieldPolicyProcess,
                                   RuleFieldValuePolicyProcess fieldValuePolicyProcess,
                                   RuleFieldValueFailPolicyProcess fieldValueFailPolicyProcess,
                                   PluginRuleProcess pluginRuleProcess) {
        return new RuleProcessImpl(fieldPolicyProcess, fieldValuePolicyProcess,
                fieldValueFailPolicyProcess, pluginRuleProcess);
    }

    @Bean
    @DependsOn("ruleProcess")
    public PluginsProcess pluginsProcess(RuleProcess ruleProcess) {
        return new PluginsProcessImpl(mybatisPluginsConfig, ruleProcess);
    }

    @Bean
    @DependsOn({"pluginsProcess", "interceptorAwareCollect"})
    public MybatisInterceptorAware mybatisInterceptorAware(PluginsProcess pluginsProcess,
                                                           InterceptorAwareCollect interceptorAwareCollect) {
        MybatisInterceptorAware mybatisInterceptorAware = new MybatisInterceptorAware(pluginsProcess);
        mybatisInterceptorAware.registerInterceptorAware(interceptorAwareCollect);
        return mybatisInterceptorAware;
    }

}
