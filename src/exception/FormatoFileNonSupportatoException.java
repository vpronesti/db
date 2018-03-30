package exception;

/**
 * l'eccezione viene lanciata quando l'intestazione del file non 
 * corrisponde al tipo di file di cui si e' richiesto l'import 
 * 
 * ad esempio, la seguente instestazione viene accettata solo per file di tipo filamento
 * IDFIL,NAME,TOTAL_FLUX,MEAN_DENS,MEAN_TEMP,ELLIPTICITY,CONTRAST,SATELLITE,INSTRUMENT
 * @author vi
 */
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
