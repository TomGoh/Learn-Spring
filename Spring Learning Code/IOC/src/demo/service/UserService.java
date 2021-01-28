package demo.service;

import demo.dao.UserDao;
import demo.dao.UserDaoImpl;

public class UserService {

    //创建UserDao属性，生成setter方法
    private UserDao userDao;
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void add(){
        System.out.println("Service add...");
//        非Spring方式： 创建UserDao对象
//        UserDao userDao=new UserDaoImpl();
//        userDao.update();

        //Spring 配置实现
        userDao.update();


    }

}
