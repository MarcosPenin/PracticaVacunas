package gal.teis.pais2;

import java.util.ArrayList;

public class Pais {

    private static int totalPaises;
    private String nombre;
    private long habitantes;
    private String capital;
    private ArrayList<String> lenguas;
    private Boolean republica;

    /*Defino los tres constructores, añadiendo al primero (que utilizan los demás) un contador para registrar el número
    de países introducidos
     */
    public Pais(final String nombre) {
        this.nombre = nombre;
        ++Pais.totalPaises;
    }

    public Pais(final String nombre, final String capital) {
        this(nombre);
        this.capital = capital;
    }

    public Pais(final String nombre, final String capital, final Boolean republica) {
        this(nombre, capital);
        this.republica = republica;
    }
    
/*A partir de aquí, métodos set y get para los atributos y el total de países*/
    
    public static int getTotalPaises() {
        return Pais.totalPaises;
    }

    public String getNombre() {
        return this.nombre;
    }

    public long getHabitantes() {
        return this.habitantes;
    }

    public String getCapital() {
        return this.capital;
    }

    public ArrayList<String> getLenguas() {
        return this.lenguas;
    }

    public Boolean getRepublica() {
        return this.republica;
    }

    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }

    public void setHabitantes(final long habitantes) {
        this.habitantes = habitantes;
    }

    public void setCapital(final String capital) {
        this.capital = capital;
    }

    public void setLenguas(final ArrayList<String> lenguas) {
        this.lenguas = lenguas;
    }

    public void setRepublica(final Boolean republica) {
        this.republica = republica;
    }

    public static void setTotalPaises(int nuevoTotal) {
        totalPaises = nuevoTotal;
    }

}
