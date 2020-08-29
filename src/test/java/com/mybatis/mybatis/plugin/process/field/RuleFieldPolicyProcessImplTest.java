package com.mybatis.mybatis.plugin.process.field;

import com.mybatis.mybatis.plugin.config.RuleFieldPolicy;
import com.mybatis.mybatis.plugin.config.RuleFieldPolicyType;
import com.mybatis.mybatis.plugin.exception.FieldPolicyNoSupportException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class RuleFieldPolicyProcessImplTest {

    @InjectMocks
    private RuleFieldPolicyProcessImpl ruleFieldPolicyProcess;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void fieldPolicyProcess() {
        RuleFieldPolicy ruleFieldPolicy = new RuleFieldPolicy();
        ruleFieldPolicy.setName(RuleFieldPolicyType.conf);
        ruleFieldPolicy.setValue("tenant_id");
        ruleFieldPolicyProcess.fieldPolicyProcess(ruleFieldPolicy);

        ruleFieldPolicy.setName(RuleFieldPolicyType.customer);
        ruleFieldPolicy.setValue("tenant_id");

        try {
            ruleFieldPolicyProcess.fieldPolicyProcess(ruleFieldPolicy);
        } catch (Exception e) {
            assert e instanceof FieldPolicyNoSupportException;
        }
    }
}