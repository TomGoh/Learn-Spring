package AOP_Annotation;

import org.springframework.stereotype.Component;

@Component
public class User {

    public void add(){
        System.out.println("User Added.");
        //int i=10/0;
    }

}
