package sm.services;

public class SwimMasterException extends Exception{
    public SwimMasterException(){}

    public SwimMasterException(String message){
        super(message);
    }

    public SwimMasterException(String message, Throwable cause){
        super(message, cause);
    }
}
