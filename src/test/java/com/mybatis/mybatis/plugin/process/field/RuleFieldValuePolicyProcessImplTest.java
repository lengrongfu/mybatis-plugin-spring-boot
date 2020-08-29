package com.mybatis.mybatis.plugin.process.field;

import com.mybatis.mybatis.plugin.config.RuleFieldValuePolicy;
import com.mybatis.mybatis.plugin.config.RuleFieldValuePolicyType;
import com.mybatis.mybatis.plugin.exception.FieldValuePolicyNoSupportException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class RuleFieldValuePolicyProcessImplTest {

    @InjectMocks
    private RuleFieldValuePolicyProcessImpl ruleFieldValuePolicyProcess;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void fieldValuePolicyProcess() {
        RuleFieldValuePolicy ruleFieldValuePolicy = new RuleFieldValuePolicy();
        ruleFieldValuePolicy.setName(RuleFieldValuePolicyType.conf);
        ruleFieldValuePolicy.setValue("demo");
        ruleFieldValuePolicyProcess.fieldValuePolicyProcess(ruleFieldValuePolicy);

        ruleFieldValuePolicy.setName(RuleFieldValuePolicyType.customer);
        ruleFieldValuePolicy.setValue("demo");

        try {
            ruleFieldValuePolicyProcess.fieldValuePolicyProcess(ruleFieldValuePolicy);
        } catch (Exception e) {
            assert e instanceof FieldValuePolicyNoSupportException;
        }
    }
}