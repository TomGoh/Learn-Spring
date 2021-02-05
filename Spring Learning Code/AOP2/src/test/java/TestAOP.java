import AOP_Annotation.User;
import AOP_XML.Book;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestAOP {

    @Test
    public void testAOP_Annotation(){
        ApplicationContext context=new ClassPathXmlApplicationContext("bean1.xml");
        User user=context.getBean("user",User.class);
        user.add();
    }

    @Test
    public void testAOP_XML(){
        ApplicationContext context=new ClassPathXmlApplicationContext("bean2.xml");
        Book book=context.getBean("book",Book.class);
        book.buy();
    }
}
