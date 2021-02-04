package test;

import bean.UserService;
import config.SpringConfig;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class test {

    @Test
    public void testAnnotation1(){
//        ApplicationContext context=new ClassPathXmlApplicationContext("bean1.xml");
//        UserService userService=context.getBean("userService",UserService.class);
//        userService.add();

        ApplicationContext context=new AnnotationConfigApplicationContext(SpringConfig.class);
        UserService userService=context.getBean("userService",UserService.class);
        userService.add();
    }
}
