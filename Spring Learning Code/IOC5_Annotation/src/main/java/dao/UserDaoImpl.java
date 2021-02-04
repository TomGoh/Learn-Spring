package dao;

import org.springframework.stereotype.Repository;

@Repository(value = "userDaoImplName")
public class UserDaoImpl implements UserDao{

    @Override
    public void added() {
        System.out.println("DAO Added...");
    }
}
