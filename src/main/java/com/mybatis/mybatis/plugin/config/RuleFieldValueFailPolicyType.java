package com.mybatis.mybatis.plugin.config;

/**
 * 字段值未取到时执行策略
 *
 * @author lengrongfu
 */
public enum RuleFieldValueFailPolicyType {
    /**
     * @Description: 继续运行, 会忽略配置的规则
     * @return:
     * @Creator: lengrongfu
     * @Date: 2020/8/14 5:12 下午
     */
    run,

    /**
     * @Description: 停止运行，会抛出异常
     * @return:
     * @Creator: lengrongfu
     * @Date: 2020/8/14 5:13 下午
     */
    stop;
}
