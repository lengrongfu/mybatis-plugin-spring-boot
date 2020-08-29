package com.mybatis.mybatis.plugin.process.field;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class ThreadLocalPolicyProcessTest {

    @InjectMocks
    private ThreadLocalPolicyProcess threadLocalPolicyProcess;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void processFieldPolicy() {

        threadLocalPolicyProcess.processFieldPolicy("tenant_id");
    }

    @Test
    public void processFieldValuePolicy() {
        threadLocalPolicyProcess.processFieldValuePolicy("demo");
    }
}