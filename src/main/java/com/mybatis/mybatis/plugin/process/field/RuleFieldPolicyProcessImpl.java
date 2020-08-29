package com.mybatis.mybatis.plugin.process.field;

import com.mybatis.mybatis.plugin.config.RuleFieldPolicy;
import com.mybatis.mybatis.plugin.config.RuleFieldPolicyType;
import com.mybatis.mybatis.plugin.exception.FieldPolicyNoSupportException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @program: mybatis plugin
 * @description:
 * @author: lengrongfu
 * @created: 2020/08/15 08:42
 */
public class RuleFieldPolicyProcessImpl implements RuleFieldPolicyProcess {

    /**
     * 存储策略对应的实现类
     */
    private static final Map<RuleFieldPolicyType, FieldPolicyProcess> fieldPolicyProcessMap = new HashMap<>();

    static {
        fieldPolicyProcessMap.put(RuleFieldPolicyType.threadLocal, new ThreadLocalPolicyProcess());
        fieldPolicyProcessMap.put(RuleFieldPolicyType.conf, new ConfPolicyProcess());
        fieldPolicyProcessMap.put(RuleFieldPolicyType.customer, new CustomerPolicyProcess());
    }

    @Override
    public String fieldPolicyProcess(RuleFieldPolicy policy) {
        RuleFieldPolicyType name = policy.getName();
        FieldPolicyProcess fieldPolicyProcess = fieldPolicyProcessMap.get(name);
        if (Objects.isNull(fieldPolicyProcess)) {
            throw new FieldPolicyNoSupportException(name.name());
        }
        String value = policy.getValue();
        String field = fieldPolicyProcess.processFieldPolicy(value);
        return field;
    }
}
