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
