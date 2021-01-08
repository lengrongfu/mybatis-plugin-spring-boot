package com.mybatis.mybatis.plugin.config;

/**
 * 插件规则值，写了几种通用的插件规则值处理
 *
 * @author lengrongfu
 */
public enum PluginRuleValueType {

    /**
     * @Description: 添加where字段值插件
     * @return:
     * @Creator: lengrongfu
     * @Date: 2020/8/14 5:28 下午
     */
    add_where_field,

    /**
     * @Description: 添加插入字段
     * @return:
     * @Creator: lengrongfu
     * @Date: 2020/8/14 5:28 下午
     */
    add_insert_field,

    /**
     * @Description: 添加更新字段
     * @return:
     * @Creator: lengrongfu
     * @Date: 2020/8/14 5:28 下午
     */
    add_update_field,

    /**
     * @Description: 添加插入和更新字段
     * @return:
     * @Creator: lengrongfu
     * @Date: 2020/8/29 1:56 下午
     */
    add_field,

    /**
     * @Description: 删除 sql 中的字段策略
     * @return:
     * @Creator: lengrongfu
     * @Date: 2020/8/29 2:12 下午
     */
    delete_field,
    /**
     * @Description: 修改表名
     * @return:
     * @Creator: 何胜
     * @Date: 2021/1/7 18:17 下午
     */
    change_tableName,
}
