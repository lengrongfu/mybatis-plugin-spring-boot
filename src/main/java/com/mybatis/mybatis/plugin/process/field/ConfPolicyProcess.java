package com.mybatis.mybatis.plugin.process.field;

/**
 * @program: mybatis plugin
 * @description: 从配置文件中获取策略处理
 * @author: lengrongfu
 * @created: 2020/08/15 09:10
 */
public class ConfPolicyProcess implements FieldPolicyProcess, FieldValuePolicyProcess {


    /**
     * @Description: 从配置文件中获取字段策略，策略中的值就是配置的值
     * @return:
     * @Creator: lengrongfu
     * @Date: 2020/8/15 9:16 上午
     */
    @Override
    public String processFieldPolicy(String value) {
        return value;
    }

    /**
     * @Description: 从配置文件中获取字段值策略，策略中的值就是配置的值
     * @return:
     * @Creator: lengrongfu
     * @Date: 2020/8/15 9:16 上午
     */
    @Override
    public String processFieldValuePolicy(String value) {
        return value;
    }
}
