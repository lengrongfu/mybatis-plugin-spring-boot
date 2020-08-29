package com.mybatis.mybatis.plugin.config;

/**
 * 字段来源策略
 *
 * @author lengrongfu
 * @time 2020-08-14 16:47
 */
public enum RuleFieldPolicyType {
    /**
     * @Description: 字段从线程变量中获取
     * @return:
     * @Creator: lengrongfu
     * @Date: 2020/8/14 5:14 下午
     */

    threadLocal,

    /**
     * @Description: 字段从配置文件中获取
     * @return:
     * @Creator: lengrongfu
     * @Date: 2020/8/14 5:14 下午
     */
    conf,

    /**
     * @Description: 字段通过自定义实现接口获取
     * 1、判断是否是类名称，如果是就先从 IOC 容器中添加通过类型获取对象。
     * 2、如果没有获取到就反射创建对象。
     * 3、如果不是类名称，就通过对象名从 IOC 容器中获取，如果都没有获取到就返回
     * @return: 如果是自定义策略，对应 value 就是类路径，通过反射自动构造对象，或者通过上下文从 IOC 容器中获取
     * @Creator: lengrongfu
     * @Date: 2020/8/14 5:14 下午
     */
    customer;


}
