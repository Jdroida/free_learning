package adapter;

//场景类
public class Client {
    public static void main(String[] args) {
        Target target = new ConcreteTarget();
        target.request();
        //适配器角色
        Target target1 = new Adapter();
        target1.request();
    }
}
