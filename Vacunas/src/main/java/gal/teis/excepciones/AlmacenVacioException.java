package gal.teis.excepciones;

/**
 * @author Marcos
 */

    public class AlmacenVacioException extends Exception {
    
    public AlmacenVacioException() {
        super("Todavía no se ha introducido ninguna vacuna.");
    }
    
    
}
