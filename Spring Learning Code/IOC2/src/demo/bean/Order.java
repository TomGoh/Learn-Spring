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
