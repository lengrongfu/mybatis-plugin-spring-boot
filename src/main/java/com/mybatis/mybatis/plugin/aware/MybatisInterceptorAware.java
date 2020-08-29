package com.mybatis.mybatis.plugin.aware;

import com.mybatis.mybatis.plugin.process.PluginsProcess;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program: mybatis plugin
 * @description:
 * @author: lengrongfu
 * @created: 2020/08/14 23:05
 */
public class MybatisInterceptorAware implements InterceptorAware, InterceptorAwareRegister {

    private static final Logger logger = LoggerFactory.getLogger(MybatisInterceptorAware.class);

    private PluginsProcess pluginsProcess;

    public MybatisInterceptorAware(PluginsProcess pluginsProcess) {
        this.pluginsProcess = pluginsProcess;
    }

    @Override
    public void mybatisBeforeExecutor(Invocation invocation) {
        if (invocation.getTarget() instanceof Executor) {
            Object[] args = invocation.getArgs();
            MappedStatement statement = (MappedStatement) args[0];
            BoundSql boundSql;
            if (args.length == 4 || args.length == 2) {
                boundSql = statement.getBoundSql(args[1]);
            } else {
                // 几乎不可能走进这里面,除非使用Executor的代理对象调用query[args[6]]
                boundSql = (BoundSql) args[5];
            }
            String sql = boundSql.getSql();

            if (logger.isDebugEnabled()) {
                logger.debug("mybatisBeforeExecutor old sql {}", sql);
            }

            String newSql = pluginsProcess.pluginsProcess(sql);

            if (logger.isDebugEnabled()) {
                logger.debug("mybatisBeforeExecutor new sql {}", sql);
            }
            
            MappedStatement newStatement = newMappedStatement(statement, new BoundSqlSqlSource(boundSql));
            MetaObject msObject = MetaObject.forObject(newStatement, new DefaultObjectFactory(),
                    new DefaultObjectWrapperFactory(), new DefaultReflectorFactory());
            msObject.setValue("sqlSource.boundSql.sql", newSql);
            args[0] = newStatement;
        }
    }

    @Override
    public void mybatisAfterExecutor(Object result) {

    }

    @Override
    public void registerInterceptorAware(InterceptorAwareCollect interceptorAwareCollect) {
        interceptorAwareCollect.addInterceptorAware(this);
    }

    private MappedStatement newMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
        MappedStatement.Builder builder =
                new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());
        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        if (ms.getKeyProperties() != null && ms.getKeyProperties().length != 0) {
            StringBuilder keyProperties = new StringBuilder();
            for (String keyProperty : ms.getKeyProperties()) {
                keyProperties.append(keyProperty).append(",");
            }
            keyProperties.delete(keyProperties.length() - 1, keyProperties.length());
            builder.keyProperty(keyProperties.toString());
        }
        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        builder.resultMaps(ms.getResultMaps());
        builder.resultSetType(ms.getResultSetType());
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());

        return builder.build();
    }

    //    定义一个内部辅助类，作用是包装sq
    class BoundSqlSqlSource implements SqlSource {
        private BoundSql boundSql;

        public BoundSqlSqlSource(BoundSql boundSql) {
            this.boundSql = boundSql;
        }

        @Override
        public BoundSql getBoundSql(Object parameterObject) {
            return boundSql;
        }
    }
}
