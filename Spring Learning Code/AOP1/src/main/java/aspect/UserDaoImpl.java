package aspect;

public class UserDaoImpl implements UserDao{

    @Override
    public int add(int a, int b) {
        return a+b;
    }

    @Override
    public String  update(String id) {
        System.out.println("Updating"+id);
        return id;
    }
}
