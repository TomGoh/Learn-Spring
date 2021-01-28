package demo;

import demo.bean.Employee;
import demo.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class test {

    @Test
    public void testAdd(){
        //加载Spring配置文件
        ApplicationContext context=new ClassPathXmlApplicationContext("bean1.xml");

        //获取配置文件中的对象并创建
        book book=context.getBean("book",book.class);
        System.out.println(book.toString());


    }

    @Test
    public void testRef1(){
        ApplicationContext context=new ClassPathXmlApplicationContext("bean2.xml");

        UserService userService=context.getBean("userService",UserService.class);
        userService.add();
    }

    @Test
    public void testInner(){
        ApplicationContext context=new ClassPathXmlApplicationContext("bean3.xml");

        Employee employee =context.getBean("employee",Employee.class);
        System.out.println(employee.toString());
    }

    @Test
    public void testCascade(){
        ApplicationContext context=new ClassPathXmlApplicationContext("bean4.xml");

        Employee employee =context.getBean("employee",Employee.class);
        System.out.println(employee.toString());
    }

}
