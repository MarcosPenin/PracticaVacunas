package gal.teis.vacunas;

import gal.teis.miscelanea.ControlData;
import gal.teis.excepciones.NoEncontradaExcepcion;
import gal.teis.excepciones.AlmacenVacioException;
import gal.teis.excepciones.YaExisteException;
import gal.teis.excepciones.YaSuperadaException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * @author Marcos
 */
public class Aplicacion {

    static Scanner sc = new Scanner(System.in);
    static VacAlmacen aux = new VacAlmacen();
    static byte opcion, opcion2, opcion3;
    static Menu miMenu = new Menu(opciones());
    static Menu siNO = new Menu(siNo());
    static Menu autorizacion = new Menu(autorizacion());
    static String codigo;

    public static void main(String[] args) {

        System.out.println("*********************************************************************************************");
        System.out.println("*******************************BIENVENIDO A LA EMA*********************************");

        do {
            System.out.println("*********************************************************************************************");
            System.out.println("¿Qué operación desea realizar?");
            miMenu.printMenu();
            opcion = ControlData.lerByte(sc);
            miMenu.rango(opcion);

            switch (opcion) {
                case 1 ->
                    listar();
                case 2 ->
                    buscar();
                case 3 ->
                    agregar();
                case 4 ->
                    eliminar();
                case 5 ->
                    introducirFases();
                case 6 ->
                    autorizarRechazar();
                case 7 ->
                    verAutorizadas();
                case 8 ->
                    verRechazadas();
                case 9 ->
                    verPendientes();
                case 10 ->
                    ultimaFase();
                case 11 ->
                    cambiarPrecio();
            }
        } while (opcion != 12);
        sc.close();
    }

    /**
     * 1. Imprime la información de todas las vacunas almacenadas
     */
    static void listar() {
        try {
            System.out.println(aux.listar());
        } catch (AlmacenVacioException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 2. Busca las vacunas en función de su código
     */
    static void buscar() {
        System.out.println("Por favor, introduce el código de la vacuna que quieres buscar");
        String vacunaBuscada = ControlData.lerString(sc);
        try {
            System.out.println(aux.buscar(vacunaBuscada));
        } catch (AlmacenVacioException e) {
            System.out.println(e.getMessage());
        } catch (NoEncontradaExcepcion e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 3. Añade una nueva vacuna a la base de datos de la EMA. Aquí controlo el
     * formato del código, que debe introducir el usuario. La expresión regular
     * está añadida como método en la clase ControlData. Antes de introducirlo,
     * le doy la opción al usuario de consultar el formato que debe tener el
     * código, no le dejo avanzar hasta que introduce un código válido.
     */
    static void agregar() {
        boolean codigoCorrecto;
        String codigo;
        do {
            System.out.println("¿Necesita información sobre el sistema de códigos válidos de la EMA?");
            do {
                siNO.printMenu();
                opcion2 = ControlData.lerByte(sc);
            } while (!siNO.rango(opcion2));
            if (opcion2 == 1) {
                System.out.println("Las vacunas de la EMA se almacenan con un código alfanumérico "
                        + "con las siguientes características\n - Deben empezar por una V mayúscula\n - A continuación, "
                        + "irá una vocal en mayúscula\n - Seguidamente, tres o cuatro letras en minúsculas\n - A continuación, dos "
                        + "cifras entre el 4 y el 7, o una cifra que será necesariamente un 8");
            }

            System.out.println("Introduzca un código válido para la vacuna.");
            codigo = ControlData.lerString(sc);
            codigoCorrecto = ControlData.codigoCorrecto(codigo);
            if (!codigoCorrecto) {
                System.out.println("Lo siento, ese código no es válido.");
            }
        } while (!codigoCorrecto);

        System.out.println("Introduzca el nombre de la vacuna");
        String nombre = ControlData.lerString(sc);

        System.out.println("Especifique el principio activo de la vacuna");
        String pActivo = ControlData.lerString(sc);

        System.out.println("Indique la farmacéutica de la vacuna");
        String farmaceutica = ControlData.lerString(sc);

        System.out.println("Introduzca el precio recomendado de la vacuna");
        double pvpr = ControlData.lerDouble(sc);

        try {
            System.out.println(aux.agregar(codigo, nombre, pActivo, farmaceutica, pvpr));
        } catch (YaExisteException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 4. Elimina una de las vacunas almacenadas
     */
    static void eliminar() {
        System.out.println("Introduca el código de la vacuna que desea eliminar");
        codigo = ControlData.lerString(sc);
        try {
            System.out.println(aux.eliminar(codigo));
        } catch (NoEncontradaExcepcion e) {
            System.out.println(e.getMessage());
        } catch (AlmacenVacioException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 5. Introduce el resultado de una o varias fases de investigación de una
     * vacuna. Creo un switch para elegir la fase que queremos evaluar y añado
     * un bucle para que se puedan añadir varias fases de la vacuna sin volver a
     * introducir el código.
     */
    static void introducirFases() {
        System.out.println("Introduca el código de la vacuna con la que desea operar");
        String codigo = ControlData.lerString(sc);

        Menu menuFases = new Menu(elegirFase());
        byte fase;
        boolean coincidencia = false;
        try {
            System.out.println(aux.buscar(codigo));
            coincidencia = true;
        } catch (AlmacenVacioException e) {
            System.out.println(e.getMessage());
        } catch (NoEncontradaExcepcion e) {
            System.out.println(e.getMessage());
        }
        if (coincidencia) {
            do {
                do {
                    menuFases.printMenu();
                    fase = ControlData.lerByte(sc);
                } while (!menuFases.rango(fase));

                System.out.println("¿Ha superado la fase?");

                siNO.printMenu();
                opcion2 = ControlData.lerByte(sc);
                siNO.rango(opcion2);

                boolean veredicto = false;

                if (opcion2 == 1) {
                    veredicto = true;
                }
                switch (fase) {
                    case 1:
                            try {
                        System.out.println(aux.evaluarFase1(codigo, veredicto));
                    } catch (YaSuperadaException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                    case 2:
                        System.out.println(aux.evaluarFase2(codigo, veredicto));
                        break;

                    case 3:
                        System.out.println(aux.evaluarFase3(codigo, veredicto));
                        break;
                }

                System.out.println("¿Desea introducir el resultado de otra fase?");
                do {
                    siNO.printMenu();
                    opcion3 = ControlData.lerByte(sc);
                } while (!siNO.rango(opcion3));
            } while (opcion3 == 1);
        }
    }

    /**
     * 6. Autoriza o rechaza una vacuna
     */
    static void autorizarRechazar() {
        System.out.println("Introduca el código de la vacuna con la que desea operar");
        codigo = ControlData.lerString(sc);
        try {
            System.out.println(aux.buscar(codigo));

            System.out.println("¿Desea autorizar o rechazar esta vacuna?");
            do {
                autorizacion.printMenu();
                opcion2 = ControlData.lerByte(sc);
            } while (!autorizacion.rango(opcion2));
            if (opcion2 == 1) {
                System.out.println(aux.autorizar(codigo));
            } else if (opcion2 == 2) {
                System.out.println(aux.rechazar(codigo));
            }
        } catch (AlmacenVacioException e) {
            System.out.println(e.getMessage());
        } catch (NoEncontradaExcepcion e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 7. Muestra la información de las vacunas que ya han sido aprobadas
     */
    static void verAutorizadas() {
        try {
            System.out.println(aux.verAutorizadas());
        } catch (AlmacenVacioException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 8. Muestra la información de las vacunas que ya han sido rechazadas
     */
    static void verRechazadas() {
        try {
            System.out.println(aux.verRechazadas());
        } catch (AlmacenVacioException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 9. Muestra la información de las vacunas que todavía no han sido
     * aprobadas ni rechazadas
     */
    static void verPendientes() {
        try {
            System.out.println(aux.verPendientes());
        } catch (AlmacenVacioException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 10. Muestra la última fase investigada de cada vacuna y su resultado
     */
    static void ultimaFase() {
        try {
            System.out.println(aux.ultimaFase());
        } catch (AlmacenVacioException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 11. Cambia el precio recomendado de una vacuna
     */
    static void cambiarPrecio() {
        try {
            System.out.println("¿Introduzca el código de la vacuna de la que quiere cambiar el precio?");
            codigo = ControlData.lerString(sc);
            System.out.println("Introduce el nuevo precio recomendado");
            double nuevoPrecio = ControlData.lerDouble(sc);
            System.out.println(aux.cambiarPrecio(codigo, nuevoPrecio));
        } catch (AlmacenVacioException e) {
            System.out.println(e.getMessage());
        } catch (NoEncontradaExcepcion e) {
            System.out.println(e.getMessage());
        }
    }

    //A partir de aquí almaceno las opciones de los menús que he usado. 
    
    static ArrayList<String> opciones() {

        ArrayList<String> opciones = new ArrayList<String>() {
            {
                add("Listar las vacunas registradas y mostrar todos sus datos");
                add("Buscar vacuna");
                add("Agregar vacuna");
                add("Eliminar vacuna");
                add("Introducir resultado de las fases de la vacuna");
                add("Autorizar/rechazar vacuna");
                add("Ver vacunas autorizadas.");
                add("Ver vacunas rechazadas");
                add("Ver vacunas pendientes de autorizar/rechazar");
                add("Ver la última fase investigada de cada vacuna almacenada");
                add("Cambiar el precio recomendado de una vacuna");
                add("Finalizar");
            }
        };
        return opciones;
    }

    static ArrayList<String> elegirFase() {

        ArrayList<String> menuFases = new ArrayList<String>() {
            {
                add("Introducir el resultado de la fase 1");
                add("Introducir el resultado de la fase 2");
                add("Introducir el resultado de la fase 3");
            }
        };
        return menuFases;
    }

    static ArrayList<String> siNo() {

        ArrayList<String> siNO = new ArrayList<String>() {
            {
                add("Sí");
                add("No");
            }
        };
        return siNO;
    }
    
    static ArrayList<String> autorizacion() {

        ArrayList<String> siNO = new ArrayList<String>() {
            {
                add("Autorizar");
                add("Rechazar");
            }
        };
        return siNO;
    }

}
