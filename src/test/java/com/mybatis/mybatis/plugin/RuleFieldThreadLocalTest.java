package com.mybatis.mybatis.plugin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RunWith(SpringRunner.class)
public class RuleFieldThreadLocalTest {

    @Test
    public void setVariable() {
        RuleFieldThreadLocal.setVariable("a", 1);
        assert Objects.equals(RuleFieldThreadLocal.getVariable("a"), 1);
    }

    @Test
    public void set() {
        Map<String, Object> map = new HashMap<>();
        map.put("1", 1);
        RuleFieldThreadLocal.set(map);

        assert RuleFieldThreadLocal.get().size() == 1;
    }

    @Test
    public void getVariable() {
        RuleFieldThreadLocal.setVariable("a", 1);
        assert Objects.equals(RuleFieldThreadLocal.getVariable("a"), 1);
    }

    @Test
    public void getVariableOrDefault() {
        RuleFieldThreadLocal.setVariable("a", 1);
        assert Objects.equals(RuleFieldThreadLocal.getVariableOrDefault("a", 2), 1);
        assert Objects.equals(RuleFieldThreadLocal.getVariableOrDefault("b", 2), 2);
    }

    @Test
    public void get() {
        Map<String, Object> map = new HashMap<>();
        map.put("1", 1);
        RuleFieldThreadLocal.set(map);

        assert RuleFieldThreadLocal.get().size() == 1;
    }
}