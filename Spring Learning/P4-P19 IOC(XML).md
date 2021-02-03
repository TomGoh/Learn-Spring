# P4-P19 IOC

## IOC概念与底层原理

IOC：控制反转，面向对象编程的设计原则，降低代码耦合度。

最常见方法为**依赖注入（Dependency Injection）**，以及依赖查找（Dependency Lookup）。

具体实现方式为将对象的创建以及对象之间的调用过程交由Spring框架进行管理以降低耦合度。



**底层原理**：

主要用到**XML解析**、**工厂模式**以及**反射**三个过程。

XML解析即为编写专门的Spring配置文件，在其中声明对象以及属性值。

工厂模式：将类之间的调用关系交由单独的类进行中转。

E.g.

```Java
class UserService{
    execute(){
        UserDao dao=UserFactory().getDao();
        dao.add();
    }
}

class UserFactory{
    public static UserDao getDao(){
        return new UserDao;
    }
}

class UserDao{
    add(){
        ......
    }
}
```

在案例中，`UserDactory`充当工厂类，实现从`UserDao`到`UserService`的中转。

**IOC过程**

1. 编写XML配置文件，配置创建的对象

   ```XML
   <bean id="..." class="..."></bean>
   ```

2. 创建工厂类，其中包括具体操作：

   ```Java
   class UserFactory{
       
       public static UserDao getDao(){
           //使用XML解析+反射获取对象
           String classValue=class属性值;    //XML解析
           //通过反射获得对象
           Class clazz=Class.forName(classValue);
           return (UserDao)clazz.newInstance();	//创建对象并返回
       }
       
   }
   ```

   进一步降低了耦合度，如：

   在`UserDao`的包发生改变，仅需要在XML文件中重新配置新的路径即可。

   

## IOC接口（BeanFactory）

IOC思想基于IOC容器完成，IOC容器底层就是对象工厂。

Spring提供两个接口实现IOC：

1. `BeanFactory`

   IOC容器基本实现，Spring内部方式，开发过程中一般不使用。

   特点：

   - 在加载配置文件时，`BeanFactory`并不创建对象，而是在使用或获取对象时才创建对象。

     ```Java
     //加载Spring配置文件，其中的对象未被创建
     BeanFactory factory=new ClassPathXmlApplicationContext("bean1.xml");
     
     //获取配置文件中的对象，此时对象才被创建
     User user=factory.getBean("user",User.class);
     ```

2. `ApplicationContext`

   `BeanFactory`接口的子接口，提供更多更强大的功能，面向开发人员使用。

   特点：

   - 在加载配置文件时`ApplicationFactory`即会创建其中的对象：

     ```Java
     //加载Spring配置文件，此时该配置文件中的对象已被创建
     ApplicationContext context=new ClassPathXmlApplicationContext("bean1.xml");
     
     //对象在前一步已被创建，将直接获取
     User user=context.getBean("user",User.class);
     ```

   在`ApplicaionContext`中有实现类：

   - `FileSystemXmlApplicationContext`

     处理硬盘路径中的配置文件

   - `ClassPathXmlApplicationContext`

     处理在`src`内的相对路径的配置文件

在服务器启动时加载配置文件并创建对象，服务器运行过程中直接使用启动时创建的对象即可。该处理方法更优。



## Bean管理

指的是两个操作：

- 由Spring创建对象

- 由Spring属性注入

  ```Java
  class User{
      
      String userName;
      
      add(){
          ...
      }
  }
  ```

  其中的`userName`即为属性。

管理操作的两种方式：

- XML配置文件

- 注解方式

## IOC操作Bean管理（基于XML）

1. **基于XML创建对象**

   ```XML
   <bean id="..." class="..."></bean>
   ```

   在Spring配置文件中使用bean标签，标签中添加对应属性以实现对象创建。

   `Bean标签`的常见属性：

   - `id`

     对象的唯一标识，通过该标识获得对象。

   - `class`

     对象所在的类的全路径，包括包名与类名。

   - `name`

     与`id`类似，区别在于`id`中不含有特殊符号但`name`中可以。使用较少。

   在创建对象时默认执行无参构造方法。

   

2. **基于XML方式注入属性**

   **DI**：依赖注入，即注入属性，是IOC的具体实现，在创建对象的基础之上操作。

   - 使用`setter`注入

     创建类，设置属性与`setter`

     ```Java
     public class Book{
         
         private String bName;
         private String bAuthor;
         
         public void setBName(String bname){
             this.bName=bname;
         }
         
         public void setBAuthor(String bauthor){
             this.bAuthor=bauthor;
         }
         
     }
     ```

     在Spring配置文件中完成对象配置，基于`setter`：

     ```XML
     <bean id="book" class="com.tom.SpringTest.Book">
         
         <!--使用标签 property 完成属性注入
     		name属性为属性名称
     		value为注入的值-->
         <property name="bName"  value="庄子"></property>
         <property name="bAuthor"  value="庄周"></property>
         
     </bean>
     ```

     或使用简化的方式：p名称空间注入

     ```XML
     <?xml version="1.0" encoding="UTF-8"?>
     <!--添加p引用-->
     <beans xmlns="http://www.springframework.org/schema/beans"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xmlns:p="http://www.springframework.org/schema/p"
            xsi:schemaLocation="http://www.springframework.org/schema/beans 											http://www.springframework.org/schema/beans/spring-beans.xsd">
     
         <bean id="book" class="demo.book" p:bName="bookName" p:bAuthor="None"> </bean>
         
     </beans>
     ```

     

   - 使用有参构造方法设置

     创建类、类的属性以及有参构造方法

     ```Java
     public class Order{
         
         private String oName;
         private String oAddress;
         
         public Order(String oname,String oaddress){
             this.oName=oname;
             this.oAddress=oaddress;
         }
         
     }
     ```

     在Spring配置文件中配置：

     ```XML
     <bean id="order" class="com.tom.SpringTest.Order">
         <!--name为属性的名称，value为属性的值-->
         <!--也可以使用index-->
         <constructor-arg name="oName" value="Rog Zephyrus"></constructor-arg>
         <constructor-arg name="oAddress" value="19#, 150 Tiyu Street"></constructor-arg>
     </bean>
     ```

     实例化对象：

     ```Java
     ApplicationContext context=new ClassPathXmlApplicationContext("bean1.xml");
     Order order=context.getBean("order",Order.class);
     ```

   

   注入其他属性类型：

   1. 字面量

      设置在类中的属性的固定值。

      - 设置null值

        ```XML
        <!--使用null标签-->
        <property name="bAuthor" >
            <null/>
        </property>
        ```

      - 设置包含特殊符号的属性值

        ```XML
        <!--属性值中包含“<>”，使用转义-->
        <property name="bAuthor" value="&lt Tom &gt"></property>
        
        <!--属性值中包含“<>”，使用CDATA-->
        <property name="bAuthor">
            <value>
                <![CDATA[<Tom>]]>
            </value>
        </property>
        ```

        

   2. 注入属性——外部Bean

      外部Bean：

      在实际操作中，如Web项目往往由三层结构组成：Servlet-Service-Dao，通过Service调用Dao即为引入外部Bean
      
      E.g.
      
      创建两个类：Service类与Dao类
      
      ```Java
      //Dao
      package demo.dao;
      
      public interface UserDao {
      
          public void update();
      
      }
      
      //DaoImpl
      package demo.dao;
      
      public class UserDaoImpl implements UserDao{
      
          @Override
          public void update() {
              System.out.println("DAO Update...");
          }
      }
      
      //Service
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
      
              //Spring 配置实现，结合后文的配置
              userDao.update();
          }
      
      }
      ```
      
      在Spring配置文件中进行类的配置
      
      ```Java
      <?xml version="1.0" encoding="UTF-8"?>
      <beans xmlns="http://www.springframework.org/schema/beans"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
      
          <!--创建service与Dao对象-->
          <bean id="userService" class="demo.service.UserService">
              <!--注入userDao对象
                  name:类的属性名称
                  ref:被注入的对象的bean标签id值
              -->
              <property name="userDao" ref="userDaoImpl"></property>
          </bean>
          
          <bean id="userDaoImpl" class="demo.dao.UserDaoImpl"></bean>
      </beans>
      ```
      
      测试
      
      ```Java
      @Test
      public void testRef1(){
      	ApplicationContext context=new ClassPathXmlApplicationContext("bean2.xml");
      
      	UserService userService=context.getBean("userService",UserService.class);
      	userService.add();
      }
      ```
      
      
      
   3. 注入属性——内部Bean与级联赋值

      内部Bean注入即为在Spring配置文件中声明一个bean对象时，该对象包含另外一个对象，此时可以在bean的内部进行bean的声明，相互嵌套。

      

      E.g. 一对多关系——部门与员工

      一个部门雇佣多个员工而一个员工属于一个部门

      使用实体类表示这种关系：

      ```Java
      //Department
      package demo.bean;
      
      public class Department {
      
          private String departmentName;
      
          public String getDepartmentName() {
              return departmentName;
          }
      
          public void setDepartmentName(String departmentName) {
              this.departmentName = departmentName;
          }
      }
      
      
      //Employee
      package demo.bean;
      
      public class Employee {
      
          private String empName;
          private String empGender;
      
          //员工属于的部门
          private Department empDept;
      
          public String getEmpName() {
              return empName;
          }
      
          public void setEmpName(String empName) {
              this.empName = empName;
          }
      
          public String getEmpGender() {
              return empGender;
          }
      
          public void setEmpGender(String empGender) {
              this.empGender = empGender;
          }
      
          public Department getEmpDept() {
              return empDept;
          }
      
          public void setEmpDept(Department empDept) {
              this.empDept = empDept;
          }
      
          @Override
          public String toString() {
              return "Employee{" +
                      "empName='" + empName + '\'' +
                      ", empGender='" + empGender + '\'' +
                      ", empDept=" + empDept.getDepartmentName() +
                      '}';
          }
      }
      
      ```

      编写配置文件：

      ```XML
      <?xml version="1.0" encoding="UTF-8"?>
      <beans xmlns="http://www.springframework.org/schema/beans"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
      
      
          <bean id="employee" class="demo.bean.Employee">
              <property name="empGender" value="Female"/>
              <property name="empName" value="Lucy"/>
              <property name="empDept">
                  <!--内部bean写法，嵌套声明-->
                  <bean id="dept" class="demo.bean.Department">
                      <property name="departmentName" value="Security"/>
                  </bean>
              </property>
          </bean>
      
      </beans>
      ```

      测试：

      ```Java
      @Test
      public void testInner(){
      	ApplicationContext context=new ClassPathXmlApplicationContext("bean3.xml");
      
      	Employee employee =context.getBean("employee",Employee.class);
      	System.out.println(employee.toString());
      }
      ```

      

   4. 注入属性——级联赋值

      基于上述案例，修改Spring配置文件，使用`Ref`进行级联赋值：

      ```XML
      <beans xmlns="http://www.springframework.org/schema/beans"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
      
          <bean id="employee" class="demo.bean.Employee">
              <property name="empGender" value="Female"/>
              <property name="empName" value="Cindy"/>
              <!--级联赋值，使用Ref-->
              <property name="empDept" ref="dept"/>
          </bean>
          
          <bean id="dept" class="demo.bean.Department">
              <property name="departmentName" value="Finance"/>
          </bean>
          
      </beans>
      ```

      或者，使用直接针对对象的属性赋值的方式：

      ```XML
      <beans xmlns="http://www.springframework.org/schema/beans"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
      
          <bean id="employee" class="demo.bean.Employee">
              <property name="empGender" value="Female"/>
              <property name="empName" value="Cindy"/>
              <!--级联赋值，直接使用“.”针对对象的属性赋值-->
              <property name="empDept" ref="dept"/>
              <property name="empDept.departmentName" value="Technology"/>
          </bean>
      
          <bean id="dept" class="demo.bean.Department"></bean>
      
      </beans>
      ```

      测试：

      ```Java
      @Test
      public void testCascade(){
      	ApplicationContext context=new ClassPathXmlApplicationContext("bean4.xml");
      
      	Employee employee =context.getBean("employee",Employee.class);
      	System.out.println(employee.toString());
      }
      ```

      

   5. 注入集合属性

      注入数组、List、Map等属性的集合。

      编写`Student`类：

      ```Java
      package demo.collectionType;
      
      import java.util.Arrays;
      import java.util.List;
      import java.util.Map;
      import java.util.Set;
      
      public class Student {
      
          //数组类型属性
          private String[] courses;
      
          //List类型属性
          private List<String> list;
      
          //Map类型的属性
          private Map<String,String> map;
      
          //Set类型的属性
          private Set<String> sets;
      
          public Student() {
          }
      
          public String[] getCourses() {
              return courses;
          }
      
          public void setCourses(String[] courses) {
              this.courses = courses;
          }
      
          public List<String> getList() {
              return list;
          }
      
          public void setList(List<String> list) {
              this.list = list;
          }
      
          public Map<String, String> getMap() {
              return map;
          }
      
          public void setMap(Map<String, String> map) {
              this.map = map;
          }
      
          public Set<String> getSets() {
              return sets;
          }
      
          public void setSets(Set<String> sets) {
              this.sets = sets;
          }
      
          public void test(){
              System.out.println(Arrays.toString(courses));
              System.out.println(list);
              System.out.println(map);
              System.out.println(sets);
          }
      }
      
      ```

      编写bean配置文件:

      ```XML
      <?xml version="1.0" encoding="UTF-8"?>
      <beans xmlns="http://www.springframework.org/schema/beans"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
      
          <bean id="student" class="demo.collectionType.Student">
      
              <property name="courses">
                  <array>
                      <value>Java</value>
                      <value>Python</value>
                      <value>C Sharp</value>
                      <value>C++</value>
                  </array>
              </property>
      
              <property name="list">
                  <list>
                      <value>100</value>
                      <value>99</value>
                      <value>60</value>
                      <value>99</value>
                  </list>
              </property>
      
              <property name="map">
                  <map>
                      <entry key="name" value="Tom"/>
                      <entry key="gender" value="Male"/>
                  </map>
              </property>
      
              <property name="sets">
                  <set>
                      <value>MySQl</value>
                      <value>Oracle</value>
                      <value>Redis</value>
                  </set>
              </property>
          </bean>
      
      </beans>
      ```

      编写测试类：

      ```Java
      package demo.test;
      
      import demo.collectionType.Student;
      import org.junit.Test;
      import org.springframework.context.ApplicationContext;
      import org.springframework.context.support.ClassPathXmlApplicationContext;
      
      public class collectionTest {
      
          @Test
          public void testCollection(){
              ApplicationContext context=new ClassPathXmlApplicationContext("bean1.xml");
      
              Student student =context.getBean("student",Student.class);
              student.test();
          }
      
      }
      ```

      

      在集合之中设置对象类型的值：
      
      ```XML
              <!--使用ref标签设置对象列表中的对象引用，ref的值为需要填充的bean的id-->
              <property name="courseList">
                  <list>
                      <ref bean="course1"></ref>
                      <ref bean="course2"></ref>
                  </list>
              </property>
          </bean>
      
          <bean id="course1" class="demo.collectionType.Course">
              <property name="cName" value="Spring 5 Framework Course"/>
          </bean>
      
          <bean id="course2" class="demo.collectionType.Course">
              <property name="cName" value="MyBaits Course"/>
          </bean>
      ```
      
      
      
      提取List的注入部分：
      
      在Spring配置文件中引入名称空间util：
      
      ```XML
      <?xml version="1.0" encoding="UTF-8"?>
      <beans xmlns="http://www.springframework.org/schema/beans"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xmlns:p="http://www.springframework.org/schema/p"
             <!--增加一个名为util的引用-->
             xmlns:util="http://www.springframework.org/schema/util"
             xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
             <!--模仿xsi中写法针对util进行相同的配置-->
              http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util.xsd">
      
      	<!--针对util进行配置，配置为list类型，而后即可通过id值进行引用-->
          <util:list id="bookList">
              <value>Book 1</value>
              <value>Book 2</value>
              <value>Book 3</value>
          </util:list>
      
      	<!--使用声明的id为util的配置对book进行property的赋值-->
          <bean id="book" class="demo.collectionType.Book">
              <property name="bookList" ref="bookList"/>
          </bean>
      
      </beans>
      ```
      
      使用`ApplicationContext`进行`Book`对象的实例化与测试：
      
      ```Java
      ApplicationContext context=new ClassPathXmlApplicationContext("bean2.xml");
      Book book=context.getBean("book",Book.class);
      for(String s:book.getBookList()){
          System.out.println(s);
      }
      ```
   
3. `FactoryBean`

   在Spring有两种Bean，一种即为前文使用的普通Bean，另一种即为工厂Bean `FactoryBean`。

   普通的Bean在XML文件中使用`id`与`class`对于Bean的引用与类型进行约束，返回的类型即为`class`中定义的类型。

   `FactoryBean`可以返回`class`定义类型之外的类型。

   `FactoryBean`示例：

   创建类实现`FactoryBean`接口，并实现接口的方法：

   ```Java
   package demo.factoryBean;
   
   import demo.collectionType.Course;
   import org.springframework.beans.factory.FactoryBean;
   
   //FactoryBean<>中定义返回的对象类型
   public class MyBean implements FactoryBean<Course> {
       
       //是否为单例
       @Override
       public boolean isSingleton() {
           return false;
       }
   
       //定义返回的Bean的对象
       @Override
       public Course getObject() throws Exception {
           Course course=new Course();
           course.setcName("MVC");
           return course;
       }
   
       @Override
       public Class<?> getObjectType() {
           return null;
       }
   }
   
   ```

   配置XML文件：

   ```XML
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
   
       <bean id="myBean" class="demo.factoryBean.MyBean">
   
       </bean>
   </beans>
   ```

   测试：

   ```Java
           context =new ClassPathXmlApplicationContext("bean3.xml");
           Course myBean=context.getBean("myBean",Course.class);
           System.out.println(myBean.getcName());
   ```



## IOC操作Bean管理（作用域与生命周期）

**作用域**：创建的Bean对象可以为单例或多例，可以设置其为单例或者多例。单例或多例的属性即为其作用域。

在Spring中默认为单例。

以Book为例，在默认情况下不同的Book对象对应的地址是一样的。

设置其作用域属性：

`Bean`标签中有属性`scope`用于配置作用域：

- `scope="singlton"`表示单实例对象，默认为此值
- `scope="prototype"`表示多实例对象

除了作用域区别，上述两个属性的区别还有：

在`scope`值为`singleton`时，加载配置文件时单实例对象已被创建：

```Java
ApplicationContext context=new ClassPathXmlApplicationContext("bean1.xml");
```

此时`bean.xml`中的对象已被创建。

在`scope`值为`prototype`时，对象的创建在调用`getBean`方法时创建多实例对象。

- `scope="request"`创建的对象会被放入request中
- `scope="session"`创建的对象会被放入session中



**生命周期**：对象从创建到销毁的过程

Bean的生命周期：

起始于Bean实例的创建（通过构造器也就是无参方法构造），
而后可以设置Bean的属性值以及对于其他Bean的引用（即调用setter），
继而调用Bean的初始化方法（需要进行配置），
Bean可以在这三步后使用。

当容器关闭时，Bean调用销毁方法销毁（需要进行配置）

E.g.

编写`Orders`Bean，自定义`setter`，`无参构造方法`，`初始化方法`以及`销毁方法`：

```Java
package demo.bean;

public class Order {
    private String oName;

    //setter
    public void setoName(String oName) {
        this.oName = oName;
        System.out.println("Executing Setter.");
    }

    public String getoName() {
        return oName;
    }

    //无参数构造方法
    public Order() {
        System.out.println("Executing Constructor.");
    }

    //初始化方法
    public void initMethod(){
        System.out.println("Executing Init-Method.");
    }

    //销毁方法
    public void destroyMethod(){
        System.out.println("Executing Destroy-method.");
    }

}
```

对于`Order`在XML中进行配置，包括初始化方法和销毁方法的链接：

```XML
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="order" class="demo.bean.Order" init-method="initMethod" destroy-method="destroyMethod">
        <property name="oName" value="Tablet"/>
    </bean>
</beans>
```

执行测试方法，手动实例化以及销毁：

```Java
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
```

在上述流程之中还有两个步骤可以执行（Bena的后置处理器）：

在初始化之前与之后可以分别调用两个方法：

- 在初始化之前可以将Bean的实例传递给后置处理器的方法
- 在初始化之后可以将Bean的实例传递给后置处理器的方法

E.g.

创建类实现接口`BeanPostProcessor`:

```Java
package demo.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class MyBeanPost implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("初始化之前执行的");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("在初始化之后执行的");
        return bean;
    }
    
}
```

在XML中配置后置处理器类：

```XML
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="order" class="demo.bean.Order" init-method="initMethod" destroy-method="destroyMethod" >
        <property name="oName" value="Tablet"/>
    </bean>

    <!--配置后置处理器-->
    <bean id="myBeanPost" class="demo.bean.MyBeanPost"/>
</beans>
```

其余参照上方代码，效果如下：

```

Executing Constructor.
Executing Setter.
初始化之前执行的
Executing Init-Method.
初始化之后执行的
Bean got.
Order name:Tablet
Executing Destroy-method.
Bean destroyed.

Process finished with exit code 0

```





## IOC操作Bean管理（XML 自动装配）

在前文中的属性注入均为使用XML文件中的`value`以及`ref`进行**手动装配**，**自动装配**为根据指定的装配规则以及属性的类型与名称自动完成注入。

E.g.

编写Bean

```Java
package autowire;

public class Employee {

    private Department department;

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "department=" + department +
                '}';
    }

    public void test(){
        System.out.println(department);
    }
}


package autowire;

public class Department {
}

```

在配置文件中，针对bean可以使用`autowire`标签实现自动注入：

- `autowire="byName"`根据名称注入，被注入的值需要和类中的属性名称一致
- `sutowire="byType"` 根据类型注入，被注入的值不能有多个，否则报错

```XML
    <bean id="employee" class="autowire.Employee" autowire="byName" scope="prototype">
    </bean>

    <bean id="employee1" class="autowire.Employee" autowire="byType" scope="prototype">
    </bean>

    <bean id="department" class="autowire.Department" scope="prototype">
    </bean>
```

测试：

```Java
    @Test
    public void testAutowire(){
        ApplicationContext context=new ClassPathXmlApplicationContext("bean1.xml");

        Employee employee =context.getBean("employee",Employee.class);
        employee.test();
        System.out.println(employee.toString());

        Employee employee1=context.getBean("employee1",Employee.class);
        employee1.test();
        System.out.println(employee1.toString());
    }
```

基于XML的自动装配使用较少，一般使用注解实现自动装配。



## IOC操作Bean管理（基于XML引入外部属性文件）

引入外部的property文件来操作Bean，在XML文件中读取。

以外部数据库为例

1. 配置Druid连接池

   直接在Spring的XML配置文件中编写内容：

   ```XML
       <!--直接链接连接池-->
       <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
           <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
           <property name="url" value="jdbc:mysql://localhost:3306/userDb"/>
           <property name="username" value="root"/>
           <property name="password" value="root"/>
       </bean>
   ```

   

2. 引入外部属性文件配置连接池：

   首先编写专门的property文件：

   ```properties
   pro.driverClass=com.mysql.jdbc.Driver
   pro.url=jdbc:mysql://localhost:3306/userDb
   pro.username=root
   pro.password=root
   ```

   而后在Spring配置文件中使用表达式进行配置：

   ```XML
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xmlns:context="http://www.springframework.org/schema/context"
          xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
          http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">
   
       <!--引入外部属性文件-->
       <context:property-placeholder location="jdbc.properties"/>
       <!--使用外部文件中的定义内容配置连接池-->
       <bean id="dataSource1" class="com.alibaba.druid.pool.DruidDataSource">
           <property name="driverClassName" value="${pro.driverClass}"/>
           <property name="url" value="${pro.url}"/>
           <property name="username" value="${pro.username}"/>
           <property name="password" value="${pro.password}"/>
       </bean>
   
   </beans>
   ```

   其中专门配置了名为`context`的namespace，并使用namespace对于链接进行配置。



