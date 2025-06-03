package adapter;

public class ConcreteTarget implements Target {
    @Override
    public void request() {
        System.out.println("im concrete target.");
    }
}
