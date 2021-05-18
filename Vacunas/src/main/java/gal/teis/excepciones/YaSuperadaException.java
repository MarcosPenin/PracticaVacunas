package gal.teis.excepciones;

/**
 * @author Marcos
 */
public class YaSuperadaException extends Exception {
   
    public YaSuperadaException(){
        super("No se puede volver a someter la vacuna a esta fase, ya se hizo previamente.");
    }
}
