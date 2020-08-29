package com.mybatis.mybatis.plugin.config;

/**
 * 插件级别类型值
 *
 * @author lengrongfu
 */
public enum PluginLevelType {
    /**
     * @Description: 数据库级别，整个库的 sql 都会执行
     * @return:
     * @Creator: lengrongfu
     * @Date: 2020/8/14 5:25 下午
     */
    databases,
    /**
     * @Description: 表级别配置，只针对对应表执行
     * @return:
     * @Creator: lengrongfu
     * @Date: 2020/8/14 5:26 下午
     */
    table,
    /**
     * @Description: dml 对应的 select、update、delete、insert 级别
     * @return:
     * @Creator: lengrongfu
     * @Date: 2020/8/14 5:26 下午
     */
    dml;

}
