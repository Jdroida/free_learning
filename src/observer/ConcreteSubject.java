package observer;

public class ConcreteSubject extends Subject {
    public void method() {
        super.notifyObservers();
    }
}
