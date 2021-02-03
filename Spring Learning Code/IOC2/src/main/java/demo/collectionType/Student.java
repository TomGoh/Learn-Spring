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

    //学生所学习的课程集合
    private List<Course> courseList;

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

    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }

    public void test(){
        System.out.println(Arrays.toString(courses));
        System.out.println(list);
        System.out.println(map);
        System.out.println(sets);
        System.out.println(courseList.get(0).getcName()+", "+courseList.get(1).getcName());
    }
}
