package com.mybatis.mybatis.plugin;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
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
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Method;

@RunWith(SpringRunner.class)
public class MybatisPluginInterceptorTest {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(MybatisPluginInterceptorTest.class);

    @InjectMocks
    private MybatisPluginInterceptor mybatisPluginInterceptor;

    @Before
    public void setUp() throws Exception {
        logger.setLevel(Level.DEBUG);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void intercept() throws Throwable {
        Object target = new SimpleExecutor(null, null);
        Method update = Executor.class.getMethod("update", MappedStatement.class, Object.class);
        Configuration configuration = new Configuration();
        String sql = "update user set name=zs where id=1";
        StaticSqlSource sqlSource = new StaticSqlSource(configuration, sql);
        MappedStatement mappedStatement = new MappedStatement.Builder(configuration,
                "1", sqlSource, SqlCommandType.UPDATE).build();

        Object[] args = new Object[]{mappedStatement, null};

        Invocation invocation = new Invocation(target, update, args);
//        mybatisPluginInterceptor.intercept(invocation);
        assert true;
    }

    @Test
    public void plugin() {
        mybatisPluginInterceptor.plugin(new SimpleExecutor(null, null));
    }

    @Test
    public void setProperties() {
        mybatisPluginInterceptor.setProperties(null);
    }
}