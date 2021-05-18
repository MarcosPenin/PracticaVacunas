package gal.teis.vacunas;

import java.time.format.DateTimeFormatter;

/**
 *
 * @author a20marcosgp
 */
public class Vacuna extends VacunaAutorizacion {

    /**
     * Almaceno los atributos que se me indican para la clase vacuna. Convierto
     * los cuatro primeros en constantes, una vez establecidos en el constructor
     * no podrán modificarse. El precio recomendado lo dejo variable, me tiene
     * más sentido que pueda cambiar.
     *
     * IMPORTANTE: Usando un HashMap para almacenar las vacunas, tendría sentido
     * que el código fuese ajeno a esta clase. Pero el ejercicio indica que la
     * clase Vacuna tiene que almacenar un código, además es necesario para
     * implementar la comparación del equals, así que opté por meterlo
     * igualmente como atributo y en su momento crear un duplicado para la key
     * del HashMap
     *
     */
    private final String CODIGO;
    private final String NOMBRE;
    private final String PACTIVO;
    private final String FARMACEUTICA;
    private double pvpr;

    /**
     * Constructor que introduce todos los atributos. No necesito ninguno más.
     *
     * @param codigo
     * @param nombre
     * @param pActivo
     * @param farmaceutica
     * @param pvpr
     */
    public Vacuna(String codigo, String nombre, String pActivo, String farmaceutica, double pvpr) {
        this.CODIGO = codigo;
        this.NOMBRE = nombre;
        this.PACTIVO = pActivo;
        this.FARMACEUTICA = farmaceutica;
        this.pvpr = pvpr;
    }

    /**
     * Solo implemento getter y setter para el precio, los demás elementos me
     * interesa que solo puedan introducirse en el momento de crear la vacuna,
     * pero el precio veo más probable que sea necesario cambiarlo.
     *
     * @return int con el precio recomendado
     */
    public double getPvpr() {
        return pvpr;
    }

    public void setPvpr(double pvpr) {
        this.pvpr = pvpr;
    }

    /**
     * Sobreescribo toString para que devuelva la información de la vacuna. Los
     * datos concretos de cada vacuna cambian en función de si está autorizada,
     * rechazada o pendiente. Me pareció oportuno añadir también la fecha del
     * resultado, a la que aprovecho para cambiar el formato para que coincida
     * con la que estamos más acostumbrados en España
     *
     * @return String con la información de la vacuna
     */
    @Override
    public String toString() {
        String respuesta;
        DateTimeFormatter formatoEspana = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        if (this.getAutorizada() == true) {
            respuesta = "Datos de la vacuna: \n\tCódigo\t\t\t" + CODIGO + "\n\tNombre\t\t\t" + NOMBRE + "\n\tP. activo\t\t" + PACTIVO
                    + "\n\tFarmacéutica\t\t" + FARMACEUTICA + "\n\tPrecio\t\t\t" + pvpr + " €\nEsta vacuna fue autorizada el "
                    + formatoEspana.format(this.getFecha());
        } else if (this.getRechazada() == true) {
            respuesta = "Datos de la vacuna: \n\tCódigo\t\t\t" + CODIGO + "\n\tNombre\t\t\t" + NOMBRE + "\n\tP. activo\t\t" + PACTIVO
                    + "\n\tFarmacéutica\t\t" + FARMACEUTICA + "\n\tPrecio\t\t\t" + pvpr + " €\nEsta vacuna fue rechazada el "
                    + formatoEspana.format(this.getFecha());
        } else {
            respuesta = "Datos de la vacuna:\n\tCódigo\t\t\t" + CODIGO + "\n\tNombre\t\t\t" + NOMBRE + "\n\tFarmacéutica\t\t" + FARMACEUTICA
                    + "\nAVISO: Esta vacuna aún no ha sido autorizada por la EMA";
        }
        return respuesta;
    }

    /**
     * Sobreescribo el método equals para determinar que dos vacunas son iguales
     * si comparten el mismo código
     *
     * @param o
     * @return boolean que indica si dos vacunas son iguales
     */
    @Override
    public boolean equals(Object o) {
        boolean respuesta = false;
        if (o instanceof Vacuna) {
            Vacuna v = (Vacuna) o;
            respuesta = v.CODIGO.equals(this.CODIGO);
        }
        return respuesta;
    }

    /**
     * Sobreescribo el método HashCode para determinar que se genere en función
     * del código de una vacuna
     *
     * @return Hashcode de la vacuna obtenido a partir de su código
     */
    @Override
    public int hashCode() {
        return CODIGO.hashCode();
    }

}
