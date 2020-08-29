package com.mybatis.mybatis.plugin.process.field;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class SystemPolicyProcessTest {

    @InjectMocks
    private SystemPolicyProcess systemPolicyProcess;

    @Test
    public void processFieldValuePolicy() {
        String uuid = systemPolicyProcess.processFieldValuePolicy("uuid");
        assert uuid != null;

        uuid = systemPolicyProcess.processFieldValuePolicy("UUID");
        assert uuid != null;

        String now = systemPolicyProcess.processFieldValuePolicy("now");
        assert now != null;

        now = systemPolicyProcess.processFieldValuePolicy("NOW");
        assert now != null;

        String err = systemPolicyProcess.processFieldValuePolicy("err");
        assert err == null;
    }

}