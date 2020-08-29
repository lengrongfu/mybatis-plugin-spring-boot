package com.mybatis.mybatis.plugin.process.field;

import java.util.Date;
import java.util.UUID;
import java.util.function.Function;

/**
 * @program: mybatis plugin
 * @description: 系统内置策略
 * @author: lengrongfu
 * @created: 2020/08/29 12:56
 */
public class SystemPolicyProcess implements FieldValuePolicyProcess {


    private static Function<String, String> function;

    static {
        function = value -> {
            switch (value) {
                case "uuid":
                case "UUID":
                    return UUID.randomUUID().toString();
                case "now":
                case "NOW":
                    return new Date().toString();
            }
            return null;
        };
    }

    /**
     * "uuid"
     * "now"
     *
     * @param value
     * @return
     */
    @Override
    public String processFieldValuePolicy(String value) {
        return function.apply(value);
    }
}
