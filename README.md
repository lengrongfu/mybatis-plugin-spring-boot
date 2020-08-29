# 基于配置增强 SQL 组件设计概述

![思维导图](https://github.com/lengrongfu/images/blob/master/mybatis-plugin-spring-boot/页面-2.png)

## 一、概述

​		`SQL`增强插件可以做到一些统一的`SQL`处理逻辑，比如现在比较流行的基于多租户的`PAAS`平台，都会涉及到在数据库层的资源隔离，就需要在所有的执行语句中加入一个统一的`WHERE`条件，就可以使用这个插件来实现，免去重复书写`SQL`。

​		引入`POM`依赖之后还需要配置`Enable`为`true`才能开启使用。

### 使用场景

​		使用场景

## 二、校验规则

​     校验规则是用于校验当前的`SQL`是否符合当前的规则，当有多个校验规则时，会顺序的执行校验，直到校验完为止。

### 校验规则级别

​		校验规则目前支持如下的级别：

- `databases`：对应数据库明字段；如果值是`databases`是依赖写的`SQL`中表前面有库的值，否则取不到库名来校验。
- `table`：对应执行的表明，内置了一个关键字`all`,意味着执行所有的表。
- `dml`：那么对应的值如果不是`select`、`insert`、`update`、`delete`那规则就会校验失败。

## 三、执行规则

### 1、规则策略

​		内置的规则策略有如下几种，每种规则策略都有对应的实现，并且可以扩充自定义的规则实现。

- `add_where_field`: 符合次规则的 sql 语句都会加上统一的 where 条件。
- `add_insert_field`: 在插入语句中添加如下的字段进行插入。
- `add_update_field`: 在更新语句中添加如下的字段进行插入。
- `add_field`: 添加字段，在插入时加入这个字段，更新时加入这个字段。

### 2、字段获取策略

​			执行规则获取字段策略，就是此字段可以从多个地方获取，目前支持如下几种策略。

- `conf`: 从配置文件中获取。
- `threadLocal`: 从本地线程变量中获取。
- `customer`: 自定义类，从自定义类中获取。

### 3、字段值获取策略

​		执行规则获取字段值策略，就是次字段值可以从多个地方获取，目前支持如下几种策略。

- `conf`: 从配置文件中获取。
- `threadLocal`: 从本地线程变量中获取。
- `customer`: 自定义类，从自定义类中获取。

### 4、获取失败后的执行策略

​	 就是上面从各种策略中获取字段值失败后的执行策略；目前支持如下两种策略.

- `run` : `sql`会继续运行，执行`SQL`时不会加入预期的字段。
- `stop`: `sql`会抛出异常，终止当前运行的`SQL`。

## 四、SQL 拦截原理

​	`SQL`拦截是基于[`Mybais`](https://mybatis.org/mybatis-3/apidocs/reference/org/apache/ibatis/plugin/Interceptor.html)拦截器来实现的。`Mybatis Plugin`  使用责任链模式与代理模式实现。

### 责任链模式

​		责任链模式的实现包含处理器接口`Handler`或者抽象处理器类`AbsHandler`, 还包括一个处理器链的类`HandlerChain`,这个处理器链类一般包括三个方法，一个是注册处理器的方法`void addHandler(Handler handler)`,还有一个遍历执行处理器的方法`void handlerAll()`，以及一个返回所有注册了处理器对象的方法`List<Handler> getHandlers()`，此方法一般需要放回不可修改的数据类，使用`Collections.unmodifiableList`进行包装。

​		对应到`Mybatis`中的责任链模式的处理器接口就是`Interceptor`,处理器链就是`InterceptorChain`，处理器链中的处理器注册是在解析`XML`配置时进行执行的。对应到源码为`XMLConfigBuilder parseConfiguration() `方法中的`pluginElement(root.evalNode("plugins"));` 处理器接口中暴露了三个方法，实际使用的处理器方法是`Object plugin(Object target);`。

​		然后处理器链 `InterceptorChain pluginAll` 方法是分别被`Executor`、`ParameterHandler`、`ResultSetHandler`、`StatementHandler`这四个对象调用的。



### 代理模式

​		代理模式有被代理对象和代理人两个角色，对应到`Mybatis`的插件模式中就是`Plugin`是代理人的角色，`Plugin`中的`private Object target`属性就是被代理对象，这个代理对象的创建是通过`Plugin wrap()`方法来创建的；这个方法是在责任链调用的时候通过处理接口中的`Object plugin(Object target);`方法进行调用的。

​         

### 为什么Mybatis的插件功能使用了责任链和动态代理来实现？



## 五、SQL 解析原理

​	`SQL`解析是基于[`jsqlparser`](http://jsqlparser.sourceforge.net/)组件来实现的。

