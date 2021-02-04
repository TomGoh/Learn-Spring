# IOC操作Bean管理（基于注解）

## 注解 Annotation

注解是代码的特殊标记，格式为：

```Java
@注解名称(属性名称=属性值，属性名称=属性值...)
```

在类上、方法上以及属性上均可使用注解。

使用注解的目的为简化bean的配置。

## Spring中针对Bean管理中创建对象提供的注解

### @Component

普适性Bean对象

### @Service

业务层

### @Controller

Web层

### @Repository

DAO层

注解功能一致，均可用来创建Bean实例，不同之处仅为方便区分。

## 基于注解实现对象创建

首先引入依赖：

```xml
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-aop</artifactId>
            <version>5.2.6.RELEASE</version>
        </dependency>
```

其次在Spring的配置文件中开启组件扫描：

```XML
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">

    <!--如需扫描多个包，在base-package中使用逗号分隔不同的包即可-->
    <context:component-scan base-package="bean"/>

</beans>
```

使用context这一namespace。

而后在需要被实例化的Bean中添加注解：

```Java
package bean;

import org.springframework.stereotype.Component;

@Component(value = "userService")
//Value可以不写，默认为首字母小写的类名
public class UserService {

    public void add(){
        System.out.println("Service added.");
    }

}
```

测试：

```Java
    @Test
    public void testAnnotation1(){
        ApplicationContext context=new ClassPathXmlApplicationContext("bean1.xml");
        UserService userService=context.getBean("userService",UserService.class);
        userService.add();
    }
```

## 开启组件扫描的细节配置

```XML
    <context:component-scan base-package="bean" use-default-filters="false">
        <context:include-filter type="annotation" expression="org.springframework.stereotype.Component"/>
    </context:component-scan>
```

`use-default-filters`属性为false时不使用Spring的组件扫描规则，使用context内部自定义的过滤方法，该过滤方法如下定义：

```XML
        <context:include-filter type="annotation" expression="org.springframework.stereotype.Component"/>
        <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
```

include-filter为需要包括的类别，此处为Component，exclude-filter为需要排除的类别，此处为Controller

## 基于注解的属性注入

1. `@Autowired`

   根据属性类型进行自动装配

   首先创建对象并添加注解

   而后在对象中注入，在类中添加属性并注解

2. `@Qualifier`

   根据属性名进行注入

   需要结合Autowired一同使用，可以指定多个相同类对象中的某一个

3. `@Rescource`

   可以根据属性名也可以根据类型注入
   
4. `@Value`

   注入普通类型属性

## 完全注解开发

摆脱配置文件，完全使用注解：创建配置类，替代XML配置文件

使用`@Configuration`注解声明类为配置类

```Java
package config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"dao","bean"})
public class SpringConfig {
}
```

而后使用的context也需要改变：

```Java
        ApplicationContext context=new AnnotationConfigApplicationContext(SpringConfig.class);
        UserService userService=context.getBean("userService",UserService.class);
        userService.add();
```

