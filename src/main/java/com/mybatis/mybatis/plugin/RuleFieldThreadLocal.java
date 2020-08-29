package com.mybatis.mybatis.plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @program: mybatis plugin
 * @description: 规则字段线程变量存放
 * @author: lengrongfu
 * @created: 2020/08/15 08:51
 */
public class RuleFieldThreadLocal {

    private static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<>();

    public static void setVariable(String key, Object value) {
        Map<String, Object> map = threadLocal.get();
        if (Objects.isNull(map)) {
            map = new HashMap<>();
        }
        map.put(key, value);
        threadLocal.set(map);
    }

    public static void set(Map<String, Object> map) {
        threadLocal.set(map);
    }

    public static Object getVariable(String key) {
        if (Objects.isNull(key)) {
            return null;
        }
        Map<String, Object> map = threadLocal.get();
        if (Objects.isNull(map) || map.size() == 0) {
            return null;
        }
        return map.get(key);
    }


    public static Object getVariableOrDefault(String key, Object defaultValue) {
        if (Objects.isNull(key)) {
            return defaultValue;
        }
        Map<String, Object> map = threadLocal.get();
        if (Objects.isNull(map) || map.size() == 0) {
            return defaultValue;
        }
        return map.getOrDefault(key, defaultValue);
    }

    public static Map<String, Object> get() {
        return threadLocal.get();
    }


    public static void remove() {
        threadLocal.remove();
    }
}
