
package gal.teis.excepciones;

public class FaltanFasesException extends Exception {
      
    public FaltanFasesException(){
         super("No se puede superar esta fase, primero debe superar la anterior.");
  
    }
    
    
    
}
