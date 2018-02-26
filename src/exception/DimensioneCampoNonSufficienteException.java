package exception;


/**
 * La dimensione del campo inserito e' inferiore a 6 caratteri
 */
public class DimensioneCampoNonSufficienteException extends Exception {
    public DimensioneCampoNonSufficienteException(String msg) {
        super(msg);
    }
    
    public DimensioneCampoNonSufficienteException(Throwable cause) {
        super(cause);
    }
    
    public DimensioneCampoNonSufficienteException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
