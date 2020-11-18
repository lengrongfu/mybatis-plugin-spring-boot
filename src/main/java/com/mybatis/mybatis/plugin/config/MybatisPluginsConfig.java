package com.mybatis.mybatis.plugin.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "guarder")
public class MybatisPluginsConfig {

    private Boolean enable = false;

    private List<PluginConfig> plugins;

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public List<PluginConfig> getPlugins() {
        return plugins;
    }

    public void setPlugins(List<PluginConfig> plugins) {
        this.plugins = plugins;
    }
}
