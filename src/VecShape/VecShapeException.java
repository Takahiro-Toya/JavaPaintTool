package VecShape;

/**
 * An exception to be thrown when failing creating an VecShape object
 */
public class VecShapeException extends RuntimeException{

    /**
     * Specify message of the exception
     * @param message message that you want to show
     */
    public VecShapeException(String message){
        super(message);
    }
}
