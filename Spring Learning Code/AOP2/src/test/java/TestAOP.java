import AOP_Annotation.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestAOP {

    @Test
    public void testAOP(){
        ApplicationContext context=new ClassPathXmlApplicationContext("bean1.xml");
        User user=context.getBean("user",User.class);
        user.add();
    }
}
