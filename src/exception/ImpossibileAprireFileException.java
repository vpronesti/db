package exception;

public class ImpossibileAprireFileException extends Exception {
    public ImpossibileAprireFileException(String msg) {
        super(msg);
        System.err.println(msg);
    }
    
    public ImpossibileAprireFileException(Throwable cause) {
        super(cause);
    }
    
    public ImpossibileAprireFileException(String msg, Throwable cause) {
        super(msg, cause);
    }    
}
