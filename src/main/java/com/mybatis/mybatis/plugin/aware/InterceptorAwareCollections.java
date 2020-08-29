package com.mybatis.mybatis.plugin.aware;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

/**
 * @program: mybatis plugin
 * @description:
 * @author: lengrongfu
 * @created: 2020/08/14 22:33
 */
public class InterceptorAwareCollections implements InterceptorAwareCollect {

    private List<InterceptorAware> interceptorAwares;

    private static final Logger logger = LoggerFactory.getLogger(InterceptorAwareCollections.class);

    /**
     * @Description: 创建对象
     * @return:
     * @Creator: lengrongfu
     * @Date: 2020/8/14 10:47 下午
     */
    public InterceptorAwareCollections(List<InterceptorAware> interceptorAwares) {
        this.interceptorAwares = interceptorAwares;
    }

    /**
     * @Description: 包装器注册
     * @return:
     * @Creator: lengrongfu
     * @Date: 2020/8/14 10:41 下午
     */
    @Override
    public void addInterceptorAware(InterceptorAware aware) {
        if (Objects.isNull(this.interceptorAwares) || Objects.isNull(aware)) {
            return;
        }
        this.interceptorAwares.add(aware);
        if (logger.isDebugEnabled()) {
            logger.debug("{} addInterceptorAware success,current have {} num interceptors",
                    aware.getClass().getName(),
                    this.interceptorAwares.size());
        }
    }


}
