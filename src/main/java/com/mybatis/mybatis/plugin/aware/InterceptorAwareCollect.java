package com.mybatis.mybatis.plugin.aware;

/**
 * @program: mybatis plugin
 * @description: 包装器收集
 * @author: lengrongfu
 * @created: 2020/08/14 22:44
 */
public interface InterceptorAwareCollect {

    /**
     * @Description: 收集包装器类
     * @return:
     * @Creator: lengrongfu
     * @Date: 2020/8/14 10:45 下午
     */
    void addInterceptorAware(InterceptorAware aware);
}
