package com.mybatis.mybatis.plugin.process.field;

import com.mybatis.mybatis.plugin.config.RuleFieldPolicy;
import com.mybatis.mybatis.plugin.exception.FieldPolicyNoSupportException;

/**
 * @program: mybatis plugin
 * @description: 规则字段处理接口
 * @author: lengrongfu
 * @created: 2020/08/15 08:40
 */
public interface RuleFieldPolicyProcess {

    /**
     * 处理规则字段策略，获取字段
     * @param policy
     * @return
     * @throws FieldPolicyNoSupportException
     */
    String fieldPolicyProcess(RuleFieldPolicy policy);
}
