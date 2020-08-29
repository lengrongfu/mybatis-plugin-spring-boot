package com.mybatis.mybatis.plugin;

import com.mybatis.mybatis.plugin.aware.InterceptorAwareCollections;
import com.mybatis.mybatis.plugin.aware.MybatisInterceptorAware;
import com.mybatis.mybatis.plugin.process.PluginsProcess;
import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.SimpleExecutor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.session.Configuration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Method;
import java.util.ArrayList;

@RunWith(SpringRunner.class)
public class MybatisInterceptorAwareTest {

    @Mock
    private PluginsProcess pluginsProcess;

    private MybatisInterceptorAware mybatisInterceptorAware;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mybatisInterceptorAware = new MybatisInterceptorAware(pluginsProcess);
    }

    @Test
    public void mybatisBeforeExecutor() throws NoSuchMethodException {
        Object target = new SimpleExecutor(null, null);
        Method update = Executor.class.getMethod("update", MappedStatement.class, Object.class);
        Configuration configuration = new Configuration();
        String sql = "update user set name=zs where id=1";
        StaticSqlSource sqlSource = new StaticSqlSource(configuration, sql);
        MappedStatement mappedStatement = new MappedStatement.Builder(configuration,
                "1", sqlSource, SqlCommandType.UPDATE).build();

        Object[] args = new Object[]{mappedStatement,null};

        Invocation invocation = new Invocation(target, update, args);
        mybatisInterceptorAware.mybatisBeforeExecutor(invocation);
    }

    @Test
    public void mybatisAfterExecutor() {
        mybatisInterceptorAware.mybatisAfterExecutor(null);
    }

    @Test
    public void registerInterceptorAware() {
        mybatisInterceptorAware.registerInterceptorAware(new InterceptorAwareCollections(new ArrayList<>()));
    }
}