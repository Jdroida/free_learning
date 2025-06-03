package observer;

//场景类
public class Client {
    public static void main(String[] args) {
        //被观察者
        ConcreteSubject subject = new ConcreteSubject();
        //观察者
        Observer observer = new ConcreteObserver("aaa");
        Observer observer2 = new ConcreteObserver("bbb");
        //开始观察
        subject.addObserver(observer);
        subject.addObserver(observer2);
        //开始报告
        subject.method();
    }
}
