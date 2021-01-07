package com.mybatis.mybatis.plugin;


import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Filtered {
    boolean filter() default false;
}
