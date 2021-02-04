package bean;

import dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
//Value可以不写，默认为首字母小写的类名
public class UserService {

    @Autowired
    @Qualifier(value = "userDaoImplName")
    private UserDao userDao1;

    @Resource(name = "userDaoImplName")
    private UserDao userDao2;

    @Value("User Service Added")
    String serviceName;

    public void add(){
        System.out.println("Service added.");
        userDao1.added();
        userDao2.added();
        System.out.println(this.serviceName);
    }

}
