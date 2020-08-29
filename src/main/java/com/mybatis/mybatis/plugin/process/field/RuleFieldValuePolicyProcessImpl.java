package com.mybatis.mybatis.plugin.process.field;

import com.mybatis.mybatis.plugin.config.RuleFieldValuePolicy;
import com.mybatis.mybatis.plugin.config.RuleFieldValuePolicyType;
import com.mybatis.mybatis.plugin.exception.FieldValuePolicyNoSupportException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @program: mybatis plugin
 * @description: 字段值来源策略
 * @author: lengrongfu
 * @created: 2020/08/15 08:44
 */
public class RuleFieldValuePolicyProcessImpl implements RuleFieldValuePolicyProcess {


    /**
     * 存储策略对应的实现类
     */
    private static Map<RuleFieldValuePolicyType, FieldValuePolicyProcess> fieldValuePolicyProcessMap = new HashMap<>();

    static {
        fieldValuePolicyProcessMap.put(RuleFieldValuePolicyType.threadLocal, new ThreadLocalPolicyProcess());
        fieldValuePolicyProcessMap.put(RuleFieldValuePolicyType.conf, new ConfPolicyProcess());
        fieldValuePolicyProcessMap.put(RuleFieldValuePolicyType.customer, new CustomerPolicyProcess());
        fieldValuePolicyProcessMap.put(RuleFieldValuePolicyType.system, new SystemPolicyProcess());
    }

    /**
     * @Description: 规则字段值策略处理
     * @return:
     * @Creator: lengrongfu
     * @Date: 2020/8/15 9:18 上午
     */
    @Override
    public String fieldValuePolicyProcess(RuleFieldValuePolicy policy) {
        RuleFieldValuePolicyType name = policy.getName();
        FieldValuePolicyProcess fieldValuePolicyProcess = fieldValuePolicyProcessMap.get(name);
        if (Objects.isNull(fieldValuePolicyProcess)) {
            throw new FieldValuePolicyNoSupportException(name.name());
        }
        String value = policy.getValue();
        String fieldValue = fieldValuePolicyProcess.processFieldValuePolicy(value);
        return fieldValue;
    }
}
