package gal.teis.excepciones;

/**
 * @author Marcos
 */
public class YaExisteException extends Exception {

    public YaExisteException(){
        super("Lo siento, ya existe una vacuna con ese mismo código. Debe borrarla si quiere autorizarla de nuevo.");
    }
    
}
