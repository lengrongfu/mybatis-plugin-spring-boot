package com.mybatis.mybatis.plugin.process.field;

import com.mybatis.mybatis.plugin.RuleFieldCustomer;
import com.mybatis.mybatis.plugin.RuleFieldValueCustomer;
import org.assertj.core.util.VisibleForTesting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: mybatis plugin
 * @description: 自定义策略实现类
 * @author: lengrongfu
 * @created: 2020/08/29 11:11
 */
public class CustomerPolicyProcess implements FieldPolicyProcess, FieldValuePolicyProcess, BeanFactoryAware {


    private static final Logger logger = LoggerFactory.getLogger(CustomerPolicyProcess.class);

    /**
     * 缓存反射创建出来的字段规则实例，避免多次反射
     */
    @VisibleForTesting
    protected static final Map<String, RuleFieldCustomer> fieldInstance = new ConcurrentHashMap<>();

    /**
     * 缓存反射创建出来的字段值实例，避免多次反射
     */
    @VisibleForTesting
    protected static final Map<String, RuleFieldValueCustomer> fieldValueInstance = new ConcurrentHashMap<>();

    /**
     * 可以通过 bean 名字获取
     */
    private BeanFactory beanFactory;

    @Override
    public String processFieldPolicy(String value) {
        RuleFieldCustomer instance = fieldInstance.get(value);
        if (Objects.isNull(instance)) {
            instance = getInstance(value, RuleFieldCustomer.class);
            logger.info("RuleFieldCustomer getInstance: {}", instance == null ? null : instance.fieldKey());
        }
        if (Objects.isNull(instance)) {
            return null;
        }
        return instance.fieldKey();
    }

    @Override
    public String processFieldValuePolicy(String value) {
        RuleFieldValueCustomer instance = fieldValueInstance.get(value);
        if (Objects.isNull(instance)) {
            instance = getInstance(value, RuleFieldValueCustomer.class);
            logger.info("RuleFieldValueCustomer getInstance: {}", instance == null ? null : instance.fieldValueKey());
        }
        if (Objects.isNull(instance)) {
            return null;
        }
        return instance.fieldValueKey();
    }


    /**
     * 先检查缓存中是否存在，双重检查
     *
     * @param value
     * @param c
     * @param <T>
     * @return
     */
    @VisibleForTesting
    protected <T> T checkCache(String value, Class<T> c) {
        /**
         * 避免重复创建
         */
        if (RuleFieldCustomer.class.equals(c)) {
            RuleFieldCustomer ruleFieldCustomer = fieldInstance.get(value);
            if (Objects.nonNull(ruleFieldCustomer)) {
                return (T) ruleFieldCustomer;
            }
        }

        if (RuleFieldValueCustomer.class.equals(c)) {
            RuleFieldValueCustomer ruleFieldValueCustomer = fieldValueInstance.get(value);
            if (Objects.nonNull(ruleFieldValueCustomer)) {
                return (T) ruleFieldValueCustomer;
            }
        }
        return null;
    }

    /**
     * 获取对象实例，获取流程如下
     *
     * @param value 用户配置的值
     * @param c     获取的类型
     * @param <T>   返回的实例
     * @return
     */
    @VisibleForTesting
    protected synchronized <T> T getInstance(String value, Class<T> c) {
        T cache = checkCache(value, c);
        if (Objects.nonNull(cache)) {
            return cache;
        }

        /**
         * 1、直接通过泛型类型获取
         */
        if (Objects.nonNull(beanFactory)) {
            T bean = beanFactory.getBean(c);
            if (Objects.nonNull(bean)) {
                return bean;
            }
        }

        /**
         * 2、通过 value 从 beanFactory 中获取，如果获取到就返回。
         * 这种用于用户自动注入对象到 IOC 容器中的场景
         */
        if (Objects.nonNull(beanFactory)) {
            Object bean = beanFactory.getBean(value);
            if (Objects.nonNull(bean) && Arrays.asList(bean.getClass().getInterfaces()).contains(c)) {
                return (T) (bean);
            }
        }

        /**
         * 3、通过 value 获取 class,然后从 beanFactory 中获取，
         * 避免用户不知道注入到 IOC 容器中的名字
         */
        if (Objects.nonNull(beanFactory)) {
            try {
                ClassLoader classLoader = getClass().getClassLoader();
                Class<?> loadClass = classLoader.loadClass(value);
                Object bean = beanFactory.getBean(loadClass);
                if (Objects.nonNull(bean) && Arrays.asList(bean.getClass().getInterfaces()).contains(c)) {
                    return (T) (bean);
                }
            } catch (ClassNotFoundException e) {

            }
        }

        /**
         * 4、通过 value 获取 class，然后反射创建对象，解决没有使用 IOC 容器的场景
         */
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            Class<?> loadClass = classLoader.loadClass(value);
            // 内部类不能被反射实例化
            Object bean = loadClass.newInstance();
            if (Objects.nonNull(bean) && Arrays.asList(bean.getClass().getInterfaces()).contains(c)) {
                return (T) (bean);
            }
        } catch (ClassNotFoundException e) {

        } catch (IllegalAccessException | InstantiationException e) {
            logger.error("{} class newInstance exception {}", value, e.getStackTrace());
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
