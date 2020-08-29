package com.mybatis.mybatis.plugin.exception;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
public class FieldPolicyNoSupportExceptionTest {

    @Test
    public void throwException() {
        try {
            throw new FieldPolicyNoSupportException("a");
        } catch (FieldPolicyNoSupportException e) {
            assert e.getMessage().contains("field policy current only support threadLocal、conf、customer、,place again choose");
        }
    }
}