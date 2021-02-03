package test;

import autowire.Employee;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class test {

    @Test
    public void testAutowire(){
        ApplicationContext context=new ClassPathXmlApplicationContext("bean1.xml");

        Employee employee =context.getBean("employee",Employee.class);
        employee.test();
        System.out.println(employee.toString());

        Employee employee1=context.getBean("employee1",Employee.class);
        employee1.test();
        System.out.println(employee1.toString());



    }

}
