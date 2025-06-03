package observer;

import java.util.Vector;

//被观察者
public abstract class Subject {
    //观察者数组
    private Vector<Observer> observers = new Vector<>();

    public void addObserver(Observer o) {
        this.observers.add(o);
    }

    public void removeObserver(Observer observer) {
        this.observers.remove(observer);
    }

    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
}
