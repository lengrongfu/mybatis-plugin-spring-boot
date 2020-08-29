package com.mybatis.mybatis.plugin.process.field;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class ConfPolicyProcessTest {

    @InjectMocks
    private ConfPolicyProcess confPolicyProcess;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void processFieldPolicy() {
        String tenantId = confPolicyProcess.processFieldPolicy("tenant_id");
        assert tenantId.equals("tenant_id");
    }

    @Test
    public void processFieldValuePolicy() {
        String demo = confPolicyProcess.processFieldValuePolicy("demo");
        assert demo.equals("demo");
    }
}