package demo.test;

import demo.collectionType.Student;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class collectionTest {

    @Test
    public void testCollection(){
        ApplicationContext context=new ClassPathXmlApplicationContext("bean1.xml");

        Student student =context.getBean("student",Student.class);
        student.test();
    }

}
