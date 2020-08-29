package com.mybatis.mybatis.plugin.aware;

import org.apache.ibatis.plugin.Invocation;

/**
 * 拦截器装饰器
 *
 * @author lengrongfu
 * @date 2020-08-14 22:22
 */
public interface InterceptorAware {

    /**
     * @Description: sql执行前拦截, exception 由使用方自己 cover，如果使用方抛出异常，异常会往上层抛出
     * @return:
     * @Creator: lengrongfu
     * @Date: 2020/8/14 10:22 下午
     */
    void mybatisBeforeExecutor(Invocation invocation);

    /**
     * @Description: Sql 执行后拦截
     * @return:
     * @Creator: lengrongfu
     * @Date: 2020/8/14 10:22 下午
     */
    void mybatisAfterExecutor(Object result);
}
