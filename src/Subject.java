/**
 * This class must be implemented in all the classes that need to communicate with observers
 */
public interface Subject {

    /**
     * register observer
     * @param observer new observer to be registered
     */
    void attachObserver(Observer observer);

    /**
     * tell changes to observer
     * @param location -need to specify location as this application have multiple subject and one observer
     */
    void notifyObservers(String location);
}
