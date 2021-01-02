package il.ac.shenkar.project.costmanager.model;

/**
 * CostManagerException is generic exception class
 */
public class CostManagerException extends Exception {
    public CostManagerException(){ }

    public CostManagerException(String message){
        super(message);
    }
    public CostManagerException(String message, Throwable cause){
        super(message, cause);
    }

}
