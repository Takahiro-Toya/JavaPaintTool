public interface Subject {

    void attachObservers(Observer observer);

    void notifyObservers();
}
