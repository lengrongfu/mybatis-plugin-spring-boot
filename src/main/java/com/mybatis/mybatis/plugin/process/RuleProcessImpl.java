package com.mybatis.mybatis.plugin.process;

import ch.qos.logback.classic.Logger;
import com.mybatis.mybatis.plugin.config.PluginRule;
import com.mybatis.mybatis.plugin.config.PluginRuleValueType;
import com.mybatis.mybatis.plugin.config.RuleFieldPolicy;
import com.mybatis.mybatis.plugin.config.RuleFieldValueFailPolicyType;
import com.mybatis.mybatis.plugin.config.RuleFieldValuePolicy;
import com.mybatis.mybatis.plugin.process.field.RuleFieldPolicyProcess;
import com.mybatis.mybatis.plugin.process.field.RuleFieldValueFailPolicyProcess;
import com.mybatis.mybatis.plugin.process.field.RuleFieldValuePolicyProcess;
import net.sf.jsqlparser.statement.Statement;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * @program: mybatis plugin
 * @description:
 * @author: lengrongfu
 * @created: 2020/08/15 08:25
 */
public class RuleProcessImpl implements RuleProcess {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(RuleProcessImpl.class);

    private RuleFieldPolicyProcess fieldPolicyProcess;

    private RuleFieldValuePolicyProcess fieldValuePolicyProcess;

    private RuleFieldValueFailPolicyProcess fieldValueFailPolicyProcess;

    private PluginRuleProcess pluginRuleProcess;

    public RuleProcessImpl(RuleFieldPolicyProcess fieldPolicyProcess,
                           RuleFieldValuePolicyProcess fieldValuePolicyProcess,
                           RuleFieldValueFailPolicyProcess fieldValueFailPolicyProcess,
                           PluginRuleProcess pluginRuleProcess) {
        this.fieldPolicyProcess = fieldPolicyProcess;
        this.fieldValuePolicyProcess = fieldValuePolicyProcess;
        this.fieldValueFailPolicyProcess = fieldValueFailPolicyProcess;
        this.pluginRuleProcess = pluginRuleProcess;
    }

    /**
     * @Description: 规则处理器实现类
     * @return:
     * @Creator: lengrongfu
     * @Date: 2020/8/15 8:28 上午
     */
    @Override
    public void ruleProcess(Statement statement, PluginRule rule) {
        /**
         * 通过策略获取字段
         */
        RuleFieldPolicy fieldPolicy = rule.getFieldPolicy();
        String field = fieldPolicyProcess.fieldPolicyProcess(fieldPolicy);
        if (logger.isDebugEnabled()) {
            logger.debug("fieldPolicyProcess field {}", field);
        }
        /**
         * 通过策略获取字段值
         */
        RuleFieldValuePolicy fieldValuePolicy = rule.getFieldValuePolicy();
        String fieldValue = fieldValuePolicyProcess.fieldValuePolicyProcess(fieldValuePolicy);
        if (logger.isDebugEnabled()) {
            logger.debug("fieldValuePolicy fieldValue {}", fieldValue);
        }
        /**
         * 获取字段值或者字段为空之后的策略
         */
        RuleFieldValueFailPolicyType fieldValueFailPolicy = rule.getFieldValueFailPolicy();
        if (Objects.isNull(fieldValue) || Objects.isNull(field)) {
            fieldValueFailPolicyProcess.fieldValueFailPolicyProcess(fieldValueFailPolicy, rule);
            /**
             * 如果是继续运行就直接返回，当前规则不用执行了
             */
            if (logger.isDebugEnabled()) {
                logger.debug("fieldValueFailPolicy {}", fieldValueFailPolicy.name());
            }
            return;
        }

        /**
         * 执行 SQL 加入逻辑
         */
        PluginRuleValueType ruleValue = rule.getValue();
        pluginRuleProcess.ruleProcess(statement, ruleValue, field, fieldValue);
        if (logger.isDebugEnabled()) {
            logger.debug("ruleValue {}", ruleValue.toString());
        }
    }
}
