package com.mybatis.mybatis.plugin.process;

/**
 * @program: mybatis plugin
 * @description: 插件处理器
 * @author: lengrongfu
 * @created: 2020/08/14 23:21
 */
public interface PluginsProcess {

    /**
     * @Description: 插件处理器
     * @return:
     * @Creator: lengrongfu
     * @Date: 2020/8/14 11:23 下午
     */
    String pluginsProcess(String oldSql);
}
