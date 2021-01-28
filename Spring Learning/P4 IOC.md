# P4-P24 IOC

## IOC概念与底层原理

IOC：控制反转，面向对象编程的设计原则，降低代码耦合度。

最常见方法为**依赖注入（Dependency Injection）**，以及依赖查找（Dependency Lookup）。

具体实现方式为将对象的创建以及对象之间的调用过程交由Spring框架进行管理以降低耦合度。



**底层原理**：

主要用到XML解析、工厂模式以及反射三个过程。

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

      

      



## IOC操作Bean管理（基于注解）

