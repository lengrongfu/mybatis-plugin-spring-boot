package com.mybatis.mybatis.plugin.aware;

/**
 * @program: mybatis plugin
 * @description: 拦截器的包装器注入接口
 * @author: lengrongfu
 * @created: 2020/08/14 22:29
 */
public interface InterceptorAwareRegister {

    /**
     * @Description: 用户实现次接口，进行注册
     * @return:
     * @Creator: lengrongfu
     * @Date: 2020/8/14 10:42 下午
     */
    void registerInterceptorAware(InterceptorAwareCollect interceptorAwareCollect);
}
