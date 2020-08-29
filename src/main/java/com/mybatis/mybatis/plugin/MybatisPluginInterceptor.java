package com.mybatis.mybatis.plugin;

import com.mybatis.mybatis.plugin.aware.InterceptorAware;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Properties;

/**
 * @program: mybatis plugin
 * @description: mybatis 插件拦截器
 * @author: lengrongfu
 * @created: 2020/08/14 18:49
 */
@Intercepts({

        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class,
                RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class,
                RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class})
})
public class MybatisPluginInterceptor implements Interceptor {

    private static final Logger logger = LoggerFactory.getLogger(MybatisPluginInterceptor.class);

    private List<InterceptorAware> mybatisInterceptorAwares;

    public MybatisPluginInterceptor(List<InterceptorAware> mybatisInterceptorAwares) {
        this.mybatisInterceptorAwares = mybatisInterceptorAwares;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        if (logger.isDebugEnabled()) {
            logger.debug("mybatis plugin intercept invocation,method name {}", invocation.getMethod().getName());
        }

        if (!CollectionUtils.isEmpty(mybatisInterceptorAwares)) {
            for (InterceptorAware aware : mybatisInterceptorAwares) {
                aware.mybatisBeforeExecutor(invocation);
            }
        }

        Object proceed = invocation.proceed();

        if (!CollectionUtils.isEmpty(mybatisInterceptorAwares)) {
            for (InterceptorAware aware : mybatisInterceptorAwares) {
                aware.mybatisAfterExecutor(proceed);
            }
        }
        return proceed;
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
