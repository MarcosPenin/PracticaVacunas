package gal.teis.excepciones;

/**
 *
 * @author a20marcosgp
 */
public class YaRechazadaException extends Exception {

    public YaRechazadaException() {
        super("No se puede realizar esta operación, la vacuna ya ha sido rechazada.");
    }

}
