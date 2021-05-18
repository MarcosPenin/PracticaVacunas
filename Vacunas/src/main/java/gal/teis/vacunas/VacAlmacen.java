package gal.teis.vacunas;

import gal.teis.excepciones.YaRechazadaException;
import gal.teis.excepciones.YaAutorizadaException;
import gal.teis.excepciones.NoEncontradaExcepcion;
import gal.teis.excepciones.FaltanFasesException;
import gal.teis.excepciones.AlmacenVacioException;
import gal.teis.excepciones.YaExisteException;
import gal.teis.excepciones.YaSuperadaException;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author a20marcosgp
 */
public class VacAlmacen {

    /**
     * He decidido utilizar un map porque en muchos métodos necesitaré acceder a
     * elementos concretos utilizando una clave, esta estructura de
     * almacenamiento me permite hacerlo eficientemente. En concreto, me decanto
     * por HashMap porque a priori no necesito que los elementos del map
     * mantengan ningún tipo de orden concreto (al menos no se especifica).
     *
     */
    public HashMap<String, Vacuna> vacunasCreadas = new HashMap<>();

    /**
     * Este método recorre el HashMap y devuelve la información de las vacunas
     * almacenadas. Lanza una excepción si no se ha almacenado ninguna vacuna.
     *
     * @return String con la información de las vacunas almacenadas
     * @throws AlmacenVacioException
     */
    public String listar() throws AlmacenVacioException {
        if (vacunasCreadas.isEmpty()) {
            throw new AlmacenVacioException();
        }
        String respuesta = "";
        for (String key : vacunasCreadas.keySet()) {
            respuesta = respuesta + vacunasCreadas.get(key).toString()
                    + "\n------------------------------------------------------------------------------\n";
        }
        return respuesta;
    }

    /**
     * Recorre el HashMap e imprime la información de la vacuna buscada.
     * Contempla excepciones por si no se ha almacenado ninguna vacuna o si no
     * se encuentra ninguna coincidencia
     *
     * @param vacunaBuscada
     * @return String con la información de la vacuna buscada
     * @throws NoEncontradaExcepcion
     * @throws AlmacenVacioException
     */
    public String buscar(String vacunaBuscada) throws NoEncontradaExcepcion, AlmacenVacioException {
        String respuesta = "";
        boolean coincidencia = false;
        if (vacunasCreadas.isEmpty()) {
            throw new AlmacenVacioException();
        }
        for (String key : vacunasCreadas.keySet()) {
            if (key.equals(vacunaBuscada)) {
                respuesta = vacunasCreadas.get(key).toString();
                coincidencia = true;
            }
        }
        if (coincidencia == false) {
            throw new NoEncontradaExcepcion();
        }
        return respuesta;
    }

    /**
     * Añade una nueva vacuna al almacén con los atributos indicados por
     * parámetro. Contempla una excepción por si ya existe una vacuna con el
     * mismo código: como el map no admite duplicados, si no estuviera en este
     * caso se sobreescribiría la entrada existente.
     *
     * @param codigo
     * @param nombre
     * @param pActivo
     * @param farmaceutica
     * @param pvpr
     * @return String informando al usuario de que se ha añadido la vacuna
     * @throws YaExisteException
     */
    public String agregar(String codigo, String nombre, String pActivo, String farmaceutica, double pvpr) throws YaExisteException {
        for (String key : vacunasCreadas.keySet()) {
            if (key.equals(codigo)) {
                throw new YaExisteException();
            }
        }
        Vacuna auxVacuna = new Vacuna(codigo, nombre, pActivo, farmaceutica, pvpr);
        vacunasCreadas.put(codigo, auxVacuna);
        return "Su vacuna se ha almacenado con éxito.";
    }

    /**
     * Localiza una vacuna por su código y la elimina. Como necesito recorrer el
     * HashMap y eliminar al mismo tiempo no puedo usar la misma técnica que en
     * el método buscar, necesito un iterator.
     *
     * @param vacunaEliminada
     * @return String informando al usuario de que se ha eliminado con éxito la
     * vacuna
     * @throws AlmacenVacioException
     * @throws NoEncontradaExcepcion
     */
    public String eliminar(String vacunaEliminada) throws AlmacenVacioException, NoEncontradaExcepcion {
        if (vacunasCreadas.isEmpty()) {
            throw new AlmacenVacioException();
        }
        boolean coincidencia = false;

        Iterator<String> it = vacunasCreadas.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            if (key.equals(vacunaEliminada)) {
                it.remove();
                coincidencia = true;
            }
        }
        if (!coincidencia) {
            throw new NoEncontradaExcepcion();
        }
        return "La vacuna con el código " + vacunaEliminada + " se ha eliminado con éxito.";
    }

    /**
     * Busca una vacuna concreta y se somete al método pasarFase1 de
     * VacunaAutorizacion, pasándole el parámetro indicado por el usuario
     *
     * @param codigo
     * @param veredictoFase1
     * @return String con información sobre si se ha superado la fase
     * @throws YaSuperadaException
     */
    public String evaluarFase1(String codigo, boolean veredictoFase1) throws YaSuperadaException {
        String respuesta = "";
        for (String key : vacunasCreadas.keySet()) {
            if (key.equals(codigo)) {
                try {
                    respuesta = "La vacuna con el código " + codigo
                            + vacunasCreadas.get(key).pasarFase1(veredictoFase1);
                } catch (YaSuperadaException e) {
                    respuesta = e.getMessage();
                }
            }
        }
        return respuesta;
    }

    /**
     * Busca una vacuna concreta y se somete al método pasarFase2 de
     * VacunaAutorizacion, pasándole el parámetro indicado por el usuario
     *
     * @param codigo
     * @param veredictoFase2
     * @return String con información sobre si se ha superado la fase
     */
    public String evaluarFase2(String codigo, boolean veredictoFase2) {
        String respuesta = "";
        for (String key : vacunasCreadas.keySet()) {
            if (key.equals(codigo)) {
                try {
                    respuesta = "La vacuna con el código " + codigo
                            + vacunasCreadas.get(key).pasarFase2(veredictoFase2);
                } catch (FaltanFasesException e) {
                    respuesta = e.getMessage();

                } catch (YaSuperadaException e) {
                    respuesta = e.getMessage();
                }
            }
        }
        return respuesta;
    }

    /**
     * Busca una vacuna concreta y se somete al método pasarFase3 de
     * VacunaAutorizacion, pasándole el parámetro indicado por el usuario
     *
     * @param codigo
     * @param veredictoFase3
     * @return String con información sobre si se ha superado la fase
     */
    public String evaluarFase3(String codigo, boolean veredictoFase3) {
        String respuesta = "";
        for (String key : vacunasCreadas.keySet()) {

            if (key.equals(codigo)) {
                try {
                    respuesta = "La vacuna con el código " + codigo
                            + vacunasCreadas.get(key).pasarFase3(veredictoFase3);
                } catch (FaltanFasesException e) {
                    respuesta = e.getMessage();
                } catch (YaSuperadaException e) {
                    respuesta = e.getMessage();
                }
            }
        }
        return respuesta;
    }

    /**
     * Busca una vacuna concreta y se somete al método autorizar de la clase
     * VacunaAutorización
     *
     * @param codigo
     * @return String con información sobre si se ha autorizado la vacuna
     */
    public String autorizar(String codigo) {
        String respuesta = "";
        for (String key : vacunasCreadas.keySet()) {
            if (key.equals(codigo)) {
                try {
                    vacunasCreadas.get(key).autorizar();
                    if (vacunasCreadas.get(key).getAutorizada() == true) {
                        respuesta = "Se ha autorizado la vacuna.";
                    } else {
                        respuesta = "No se ha podido autorizar la vacuna, no ha superado las fases necesarias.";
                    }
                } catch (YaRechazadaException e) {
                    respuesta = e.getMessage();
                } catch (YaAutorizadaException e) {
                    respuesta = e.getMessage();
                }
            }
        }
        return respuesta;
    }

    /**
     * Busca una vacuna concreta y se somete al método rechazar de la clase
     * VacunaAutorización
     *
     * @param codigo
     * @return String con información sobre si se ha rechazado la vacuna
     */
    public String rechazar(String codigo) {
        String respuesta = "";
        boolean coincidencia = false;
        for (String key : vacunasCreadas.keySet()) {
            if (key.equals(codigo)) {
                try {
                    vacunasCreadas.get(key).rechazar();
                    respuesta = "Se ha rechazado la vacuna.";
                    coincidencia = true;
                } catch (YaAutorizadaException e) {
                    respuesta = e.getMessage();
                    coincidencia = true;
                } catch (YaRechazadaException e) {
                    respuesta = e.getMessage();
                }
            }
        }
        return respuesta;
    }

    /**
     * Recorre el Hashmap mostrando la información de las vacunas que hayan sido
     * autorizadas. Contempla una exepción por si no se ha introducido ninguna
     * vacuna.
     *
     * @return String con la información de las vacunas que hayan sido
     * autorizadas.
     * @throws AlmacenVacioException
     */
    public String verAutorizadas() throws AlmacenVacioException {
        String respuesta = "";
        if (vacunasCreadas.isEmpty()) {
            throw new AlmacenVacioException();
        }
        boolean coincidencia = false;
        for (String key : vacunasCreadas.keySet()) {
            if (vacunasCreadas.get(key).getAutorizada() == true) {
                respuesta = respuesta + vacunasCreadas.get(key).toString()
                        + "\n------------------------------------------------------------------------------\n";
                coincidencia = true;
            }
        }
        if (!coincidencia) {
            respuesta = "Todavía no se ha aprobado ninguna vacuna.";
        }
        return respuesta;
    }

    /**
     * Recorre el Hashmap mostrando la información de las vacunas que hayan sido
     * rechazadas. Contempla una exepción por si no se ha introducido ninguna
     * vacuna.
     *
     * @return String con la información de las vacunas que hayan sido
     * rechazadas.
     * @throws AlmacenVacioException
     */
    public String verRechazadas() throws AlmacenVacioException {
        String respuesta = "";
        if (vacunasCreadas.isEmpty()) {
            throw new AlmacenVacioException();
        }
        boolean coincidencia = false;

        for (String key : vacunasCreadas.keySet()) {
            if (vacunasCreadas.get(key).getRechazada() == true) {
                respuesta = respuesta + vacunasCreadas.get(key).toString()
                        + "\n------------------------------------------------------------------------------\n";
                coincidencia = true;
            }
        }
        if (!coincidencia) {
            respuesta = "Todavía no se ha rechazado ninguna vacuna.";
        }

        return respuesta;
    }

    /**
     * Recorre el Hashmap mostrando la información de las vacunas que haún no
     * han sido autorizadas ni rechazadas. Contempla una exepción por si no se
     * ha introducido ninguna vacuna.
     *
     * @return String con la información de las vacunas que aún no han sido
     * autorizadas ni rechazadas
     * @throws AlmacenVacioException
     */
    public String verPendientes() throws AlmacenVacioException {
        String respuesta = "";
        if (vacunasCreadas.isEmpty()) {
            throw new AlmacenVacioException();
        }
        boolean coincidencia = false;
        for (String key : vacunasCreadas.keySet()) {
            if (vacunasCreadas.get(key).getRechazada() == false
                    && vacunasCreadas.get(key).getAutorizada() == false) {
                respuesta = respuesta + vacunasCreadas.get(key).toString()
                        + "\n------------------------------------------------------------------------------\n";
                coincidencia = true;
            }
        }
        if (!coincidencia) {
            respuesta = "No hay ninguna vacuna pendiente de aprobar/rechazar.";
        }
        return respuesta;
    }

    /**
     * Busca una vacuna concreta y llama al método progresoActual de
     * VacunaAutorizacion para saber las fases a las que se ha sometido una
     * vacuna y si las ha superado.
     *
     * @return String con la información de las fases que ha superado cada
     * vacuna
     * @throws AlmacenVacioException
     */
    public String ultimaFase() throws AlmacenVacioException {
        if (vacunasCreadas.isEmpty()) {
            throw new AlmacenVacioException();
        }
        String respuesta = "";
        for (String key : vacunasCreadas.keySet()) {
            respuesta = respuesta + "La vacuna con el código " + key + vacunasCreadas.get(key).progresoActual()
                    + "\n------------------------------------------------------------------------------\n";
        }
        return respuesta;
    }

    /**
     * He decidido añadir un último método para cambiar el precio de una vacuna
     * almacenada, parecía el atributo más susceptible de necesitar
     * actualizaciones
     *
     * @param codigo
     * @param nuevoPrecio
     * @return String informando del nuevo precio
     * @throws AlmacenVacioException
     * @throws NoEncontradaExcepcion
     */
    public String cambiarPrecio(String codigo, double nuevoPrecio) throws AlmacenVacioException, NoEncontradaExcepcion {
        if (vacunasCreadas.isEmpty()) {
            throw new AlmacenVacioException();
        }
        String respuesta = "";
        boolean coincidencia = false;
        for (String key : vacunasCreadas.keySet()) {
            if (key.equals(codigo)) {
                vacunasCreadas.get(key).setPvpr(nuevoPrecio);
                respuesta = "El precio se ha actualizado a " + nuevoPrecio + " euros.";
                coincidencia = true;
            }
        }
        if (!coincidencia) {
            throw new NoEncontradaExcepcion();
        }
        return respuesta;

    }

}
