package proxy;

import aspect.UserDao;
import aspect.UserDaoImpl;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class JDKProxy {

    public static void main(String[] args) {
        Class[] interfaces={UserDao.class};
//        Proxy.newProxyInstance(JDKProxy.class.getClassLoader(), interfaces, new InvocationHandler() {
//            @Override
//            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//                return null;
//            }
//        });
        UserDao dao=new UserDaoImpl();
        UserDao userDao= (UserDao) Proxy.newProxyInstance(JDKProxy.class.getClassLoader(), interfaces,new UserDaoProxy(dao));
        int result=userDao.add(1,4);
        System.out.println("Add result:"+result);
    }


}

class UserDaoProxy implements InvocationHandler{

    private final Object object;

    public UserDaoProxy(Object o) {
        this.object=o;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("Execute Before. "+"method name: "+method.getName()+". Para: "+ Arrays.toString(args));

        Object res=method.invoke(object,args);//Executing...

        System.out.println("After Execute. "+object);

        return res;
    }
}
