package VecInterface;

/**
 * This class must be implemented in all the classes that need to know changes in class that implements subject
 * In this Vector paint application, this is implemented in main or canvas class
 */
public interface Observer {

    /**
     * Update changes at specified location
     * @param location -class name in which changes are made
     */
    void update(String location);

}
