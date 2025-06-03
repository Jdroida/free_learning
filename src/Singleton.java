public class Singleton {
    //这是一般写法 其实更推荐枚举实现
    private static final Singleton singleton = new Singleton();

    //限制产生多个对象
    private Singleton() {
    }

    public static Singleton getInstance() {
        return singleton;
    }

    public static void doSomething() {

    }
}
