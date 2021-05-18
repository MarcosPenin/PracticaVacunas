
package gal.teis.excepciones;

/**
 *
 * @author a20marcosgp
 */
public class NoEncontradaExcepcion extends Exception {
    
    public NoEncontradaExcepcion() {
        super("No se ha encontrado ninguna vacuna con ese código.");
    }

    
}
