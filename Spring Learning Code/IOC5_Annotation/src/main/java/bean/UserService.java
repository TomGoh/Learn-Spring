package bean;

import org.springframework.stereotype.Component;

@Component(value = "userService")
//Value可以不写，默认为首字母小写的类名
public class UserService {

    public void add(){
        System.out.println("Service added.");
    }

}
