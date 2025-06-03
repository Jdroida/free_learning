package observer;

public class ConcreteObserver implements Observer {
    public String name;

    public ConcreteObserver(String name) {
        this.name = name;
    }

    @Override
    public void update() {
        System.out.println("Im " + name + " and I saw message updated");
    }
}
