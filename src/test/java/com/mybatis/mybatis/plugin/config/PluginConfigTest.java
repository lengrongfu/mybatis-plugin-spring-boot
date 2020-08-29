package com.mybatis.mybatis.plugin.config;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RunWith(SpringRunner.class)
public class PluginConfigTest {

    @Before
    public void setUp() {
    }

    @Test
    public void compareToNoOrder() {

        List<PluginConfig> pluginConfigs = new ArrayList<>();
        PluginConfig o1 = new PluginConfig();
        o1.setName("o1");
        pluginConfigs.add(o1);

        PluginConfig o2 = new PluginConfig();
        o2.setName("o2");
        pluginConfigs.add(o2);
        Collections.sort(pluginConfigs);

        assert pluginConfigs.get(0).getName().equals("o1");
    }

    @Test
    public void compareToO1NoOrder() {
        List<PluginConfig> pluginConfigs = new ArrayList<>();
        PluginConfig o1 = new PluginConfig();
        o1.setName("o1");
        pluginConfigs.add(o1);

        PluginConfig o2 = new PluginConfig();
        o2.setName("o2");
        o2.setOrder(1);
        pluginConfigs.add(o2);
        Collections.sort(pluginConfigs);

        assert pluginConfigs.get(0).getName().equals("o2");
    }

    @Test
    public void compareToO2NoOrder() {
        List<PluginConfig> pluginConfigs = new ArrayList<>();
        PluginConfig o1 = new PluginConfig();
        o1.setName("o1");
        o1.setOrder(1);
        pluginConfigs.add(o1);

        PluginConfig o2 = new PluginConfig();
        o2.setName("o2");
        pluginConfigs.add(o2);
        Collections.sort(pluginConfigs);

        assert pluginConfigs.get(0).getName().equals("o1");
    }

    @Test
    public void compareToO1EQO2Order() {
        List<PluginConfig> pluginConfigs = new ArrayList<>();
        PluginConfig o1 = new PluginConfig();
        o1.setName("o1");
        o1.setOrder(1);
        pluginConfigs.add(o1);

        PluginConfig o2 = new PluginConfig();
        o2.setName("o2");
        o2.setOrder(1);
        pluginConfigs.add(o2);
        Collections.sort(pluginConfigs);

        assert pluginConfigs.get(0).getName().equals("o1");
    }

    @Test
    public void compareToO1LTO2Order() {
        List<PluginConfig> pluginConfigs = new ArrayList<>();
        PluginConfig o1 = new PluginConfig();
        o1.setName("o1");
        o1.setOrder(1);
        pluginConfigs.add(o1);

        PluginConfig o2 = new PluginConfig();
        o2.setName("o2");
        o2.setOrder(2);
        pluginConfigs.add(o2);
        Collections.sort(pluginConfigs);

        assert pluginConfigs.get(0).getName().equals("o1");
    }

    @Test
    public void compareToO1GTO2Order() {
        List<PluginConfig> pluginConfigs = new ArrayList<>();
        PluginConfig o1 = new PluginConfig();
        o1.setName("o1");
        o1.setOrder(2);
        pluginConfigs.add(o1);

        PluginConfig o2 = new PluginConfig();
        o2.setName("o2");
        o2.setOrder(1);
        pluginConfigs.add(o2);
        Collections.sort(pluginConfigs);

        assert pluginConfigs.get(0).getName().equals("o2");
    }
}