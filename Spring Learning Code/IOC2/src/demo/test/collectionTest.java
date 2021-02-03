package demo.test;

import demo.bean.Order;
import demo.collectionType.Book;
import demo.collectionType.Course;
import demo.collectionType.Student;
import demo.factoryBean.MyBean;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class collectionTest {

    @Test
    public void testCollection(){
        ApplicationContext context=new ClassPathXmlApplicationContext("bean1.xml");

        Student student =context.getBean("student",Student.class);
        student.test();

        context=new ClassPathXmlApplicationContext("bean2.xml");
        Book book=context.getBean("book",Book.class);
        for(String s:book.getBookList()){
            System.out.println(s);
        }

        context =new ClassPathXmlApplicationContext("bean3.xml");
        Course myBean=context.getBean("myBean",Course.class);
        System.out.println(myBean.getcName());

    }

    @Test
    public void testLifeCycle(){
//        ApplicationContext context=new ClassPathXmlApplicationContext("bean4.xml");
        ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("bean4.xml");
        Order order=context.getBean("order",Order.class);
        System.out.println("Bean got.");
        System.out.println("Order name:"+order.getoName());

        //手动销毁Bean实例
//        ((ClassPathXmlApplicationContext)context).close();
        context.close();
        System.out.println("Bean destroyed.");

    }

}
