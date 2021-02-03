package demo.factoryBean;

import demo.collectionType.Course;
import org.springframework.beans.factory.FactoryBean;

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
