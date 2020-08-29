package com.mybatis.mybatis.plugin.process.field;

import com.mybatis.mybatis.plugin.config.RuleFieldValuePolicy;
import com.mybatis.mybatis.plugin.exception.FieldValuePolicyNoSupportException;

/**
 * @program: mybatis plugin
 * @description:
 * @author: lengrongfu
 * @created: 2020/08/15 08:43
 */
public interface RuleFieldValuePolicyProcess {

    /**
     * @Description: 规则字段值策略处理
     * @return:
     * @Creator: lengrongfu
     * @Date: 2020/8/15 8:44 上午
     * @throws FieldValuePolicyNoSupportException
     */
    String fieldValuePolicyProcess(RuleFieldValuePolicy policy);
}
