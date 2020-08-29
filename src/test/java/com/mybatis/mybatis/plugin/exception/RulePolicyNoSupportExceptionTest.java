package com.mybatis.mybatis.plugin.exception;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class RulePolicyNoSupportExceptionTest {

    @Test
    public void throwException() {
        try {
            throw new RulePolicyNoSupportException("a");
        } catch (RulePolicyNoSupportException e) {
            assert e.getMessage().contains("rule policy current only support");
        }
    }
}