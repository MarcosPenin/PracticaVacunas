package gal.teis.vacunas;

import gal.teis.excepciones.YaRechazadaException;
import gal.teis.excepciones.YaAutorizadaException;
import gal.teis.excepciones.FaltanFasesException;
import gal.teis.excepciones.YaSuperadaException;
import java.time.LocalDate;

/**
 *
 * @author a20marcosgp
 */
public abstract class VacunaAutorizacion implements IAutorizable {

    /**
     * Creo los atributos boolean que me piden la variable para la fecha del
     * resultado de autorización/rechazo
     *
     */
    private boolean fase1Superada;
    private boolean fase2Superada;
    private boolean fase3Superada;
    private boolean autorizada;
    private boolean rechazada;
    private byte fasesCompletadas = 0;
    private LocalDate fechaResultado;

    /**
     * Creo getters para los boolean de autorizada y rechazada, también para
     * fechaResultado. No creo setters, me interesa que solo puedan modificarse
     * desde los métodos correspondientes. No creo nada para los boolean de las
     * fases, para acceder a la información elevante sobre estos atributos
     * tendré posteriormente el método progresoActual
     */
    public boolean getAutorizada() {
        return autorizada;
    }

    public boolean getRechazada() {
        return rechazada;
    }

    public LocalDate getFecha() {
        return fechaResultado;
    }

    /**
     * Implemento un método para someter la vacuna a la primera fase, que se
     * resolverá en función del boolean recibido como parámetro. Si recibe un
     * true, se cambiará a true el valor de fase1Superada.
     *
     * Independientemente de si pasa o no, se aumentará el contador
     * fasesCompletadas, impidiendo que pueda volver a someterse a la prueba con
     * una excepción vinculada a este contador. 
     *
     *
     * @param veredictoFase1
     * @return Mensaje indicando si ha superado la fase 1
     * @throws YaSuperadaException
     */
    public String pasarFase1(boolean veredictoFase1) throws YaSuperadaException {
        if (fasesCompletadas > 0) {
            throw new YaSuperadaException();
        }
        String respuesta = " no ha superado la fase 1.";
        if (veredictoFase1 == true) {
            fase1Superada = true;
            respuesta = " ha superado la fase 1";
        }
        fasesCompletadas++;
        return respuesta;
    }

    /**
     * El método pasarFase2 es semejante al anterior. Implementa a mayores una
     * excepción para el caso de que no se haya superado la fase 1.
     *
     * @param veredictoFase2
     * @return Mensaje indicando si ha superado la fase 1
     * @throws FaltanFasesException
     * @throws YaSuperadaException
     */
    public String pasarFase2(boolean veredictoFase2) throws FaltanFasesException, YaSuperadaException {
        if (fase1Superada == false) {
            throw new FaltanFasesException();
        }
        if (fasesCompletadas > 1) {
            throw new YaSuperadaException();
        }

        String respuesta = " no ha superado la fase 2.";
        if (veredictoFase2 == true) {
            fase2Superada = true;
            respuesta = " ha superado la fase 2";
        }
        fasesCompletadas++;
        return respuesta;
    }

    /**
     * Semejante a pasarFase2
     *
     * @param veredictoFase3
     * @return Mensaje indicando si ha superado la fase 3
     * @throws FaltanFasesException
     * @throws YaSuperadaException
     */
    public String pasarFase3(boolean veredictoFase3) throws FaltanFasesException, YaSuperadaException {
        if (fase2Superada == false) {
            throw new FaltanFasesException();
        }
        if (fasesCompletadas > 2) {
            throw new YaSuperadaException();
        }
        String respuesta = " no ha superado la fase 3.";
        if (veredictoFase3 == true) {
            fase3Superada = true;
            respuesta = " ha superado la fase 3";
        }
        fasesCompletadas++;
        return respuesta;
    }

    /**
     * Este método implementa el método autorizar de la interfaz IAutorizable.
     * Analiza si ha superado las fases en base a los boolean de las fases
     * superadas y al boolean fasescompletadas. Tal y como está escrito el
     * programa, sería suficiente comprobar que la fase está superada, ya que
     * solo deja someterse a ella si ha superado las anteriores. De esta forma,
     * se hace una doble comprobación.
     *
     * Contempla excepciones en el caso de que la vacuna ya haya sido autorizada
     * o rechazada previamente.
     *
     * @return boolean que indica si la vacuna ha sido autorizada
     * @throws YaRechazadaException
     * @throws YaAutorizadaException
     */
    @Override
    public boolean autorizar() throws YaRechazadaException, YaAutorizadaException {
        if (rechazada == true) {
            throw new YaRechazadaException();
        }
        if (autorizada == true) {
            throw new YaAutorizadaException();
        }

        if (fasesCompletadas == 3 && fase1Superada == true && fase2Superada == true && fase3Superada == true) {
            autorizada = true;
            fechaResultado = LocalDate.now();
        }
        return autorizada;
    }

    /**
     * Este método implementa el método rechazar de la interfaz IAutorizable. En
     * este caso, no tiene que analizar nada, si se le indica que una vacuna
     * debe ser rechazada la rechaza. Contempla excepciones en el caso de que la
     * vacuna ya haya sido autorizada o rechazada previamente.
     *
     * @return boolean que indica si la vacuna ha sido rechazada
     * @throws YaRechazadaException
     * @throws YaAutorizadaException
     */
    @Override
    public boolean rechazar() throws YaAutorizadaException, YaRechazadaException {
        if (autorizada == true) {
            throw new YaAutorizadaException();
        }
        if (rechazada == true) {
            throw new YaRechazadaException();
        }
        rechazada = true;
        fechaResultado = LocalDate.now();
        return rechazada;
    }

    /**
     * Este método analiza la última fase que ha superado una vacuna e informa
     * al usuario del progreso actual de la misma. He entendido que lo relevante
     * es saber cual ha sido la última fase completada.
     *
     * @return String que indica las fases que ha superado una vacuna
     */
    public String progresoActual() {
        String respuesta = "";
        switch (fasesCompletadas) {
            case 0:
                respuesta = " no se ha sometido a la evaluación de ninguna fase.";
                break;
            case 1:
                respuesta = " se ha sometido a la evaluación de la primera fase";
                if (fase1Superada) {
                    respuesta += " y la ha superado.";
                } else {
                    respuesta += ", pero no la ha superado.";
                }
                break;
            case 2:
                respuesta = " se ha sometido a la evaluación de la primera y la segunda fase";
                if (fase2Superada) {
                    respuesta += " y las ha superado.";
                } else {
                    respuesta += ", pero no ha superado la segunda.";
                }
                break;
            case 3:
                respuesta = " se ha sometido a la evaluación de las tres fases";
                if (fase3Superada) {
                    respuesta += " y las ha superado.";
                } else {
                    respuesta += ", pero no ha superado la tercera.";
                }
                break;
        }
        return respuesta;
    }
}
