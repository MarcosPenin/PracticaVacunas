
package gal.teis.vacunas;

import gal.teis.excepciones.YaRechazadaException;
import gal.teis.excepciones.YaAutorizadaException;

/**
 *
 * @author a20marcosgp
 */
public interface IAutorizable {
    
    /*Añado excepciones a la interfaz para que la vacuna no pueda ser autorizada o rechazada si ya ha sido 
    autorizada o rechazada previamente
    */
    
    public boolean autorizar()throws YaRechazadaException, YaAutorizadaException;
    public boolean rechazar() throws YaAutorizadaException, YaRechazadaException;
    
}
