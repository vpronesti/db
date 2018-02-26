package exception;

public class FormatoFileNonSupportatoException extends Exception {
    public FormatoFileNonSupportatoException(String msg) {
        super(msg);
        System.err.println(msg);
    }
    
    public FormatoFileNonSupportatoException(Throwable cause) {
        super(cause);
    }
    
    public FormatoFileNonSupportatoException(String msg, Throwable cause) {
        super(msg, cause);
    }    
}
