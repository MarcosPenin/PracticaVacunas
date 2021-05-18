
package gal.teis.excepciones;

/**
 *
 * @author a20marcosgp
 */
public class YaAutorizadaException extends Exception{
    
    public YaAutorizadaException(){
        super("No se puede realizar esta operación, la vacuna ya ha sido autorizada.");
    }
    
}


