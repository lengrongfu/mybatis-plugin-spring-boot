package com.mybatis.mybatis.plugin.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Description: 单测
 * @Creator: lengrongfu
 * @Date: 2020/8/15 8:13 上午
 */
@RunWith(SpringRunner.class)
public class PluginRuleTest {

    @Test
    public void compareToNoOrder() {
        List<PluginRule> rules = new ArrayList<>();
        PluginRule o1 = new PluginRule();
        o1.setName("o1");
        rules.add(o1);

        PluginRule o2 = new PluginRule();
        o2.setName("o2");
        rules.add(o2);
        Collections.sort(rules);

        assert rules.get(0).getName().equals("o1");
    }

    @Test
    public void compareToO1NoOrder() {
        List<PluginRule> rules = new ArrayList<>();
        PluginRule o1 = new PluginRule();
        o1.setName("o1");
        rules.add(o1);

        PluginRule o2 = new PluginRule();
        o2.setName("o2");
        o2.setOrder(1);
        rules.add(o2);
        Collections.sort(rules);

        assert rules.get(0).getName().equals("o2");
    }

    @Test
    public void compareToO2NoOrder() {
        List<PluginRule> rules = new ArrayList<>();
        PluginRule o1 = new PluginRule();
        o1.setName("o1");
        o1.setOrder(1);
        rules.add(o1);

        PluginRule o2 = new PluginRule();
        o2.setName("o2");
        rules.add(o2);
        Collections.sort(rules);

        assert rules.get(0).getName().equals("o1");
    }

    @Test
    public void compareToO1EQO2Order() {
        List<PluginRule> rules = new ArrayList<>();
        PluginRule o1 = new PluginRule();
        o1.setName("o1");
        o1.setOrder(1);
        rules.add(o1);

        PluginRule o2 = new PluginRule();
        o2.setName("o2");
        o2.setOrder(1);
        rules.add(o2);
        Collections.sort(rules);

        assert rules.get(0).getName().equals("o1");
    }

    @Test
    public void compareToO1LTO2Order() {
        List<PluginRule> rules = new ArrayList<>();
        PluginRule o1 = new PluginRule();
        o1.setName("o1");
        o1.setOrder(1);
        rules.add(o1);

        PluginRule o2 = new PluginRule();
        o2.setName("o2");
        o2.setOrder(2);
        rules.add(o2);
        Collections.sort(rules);

        assert rules.get(0).getName().equals("o1");
    }

    @Test
    public void compareToO1GTO2Order() {
        List<PluginRule> rules = new ArrayList<>();
        PluginRule o1 = new PluginRule();
        o1.setName("o1");
        o1.setOrder(2);
        rules.add(o1);

        PluginRule o2 = new PluginRule();
        o2.setName("o2");
        o2.setOrder(1);
        rules.add(o2);
        Collections.sort(rules);

        assert rules.get(0).getName().equals("o2");
    }
}