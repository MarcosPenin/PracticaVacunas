package gal.teis.pais2;

import java.util.ArrayList;
import java.util.Scanner;

public class Principal {

    public static void main(String[] args) {

        /*Comienzo declarando las variables que necesitaré, entre ellas varios ArrayList: uno para las opciones del 
        menú, otro de objetos para almacenar los países creados por el usuario y otro para almacenar las 
        lenguas oficiales
         */
        byte opcion, opcion2, opcion3;
        int opcion4, opcion6;
        String nombre, capital;
        Scanner sc = new Scanner(System.in);
        Boolean republica = null;
        ArrayList<String> lenguas = new ArrayList<String>();
        ArrayList<Pais> paises = new ArrayList<Pais>();

        ArrayList<String> opciones = new ArrayList<String>() {
            {
                add("Introducir un país");
                add("Mostrar los países introducidos");
                add("Añadir datos de un país ya introducido");
                add("Eliminar un país");
                add("Finalizar");
            }
        };
        Pais miPais;

        /*Creo un menú con las opciones del ArrayLista, doy la bienvenida al usuario e inicializo un bucle
        con las diferentes operaciones que puede realizar
         */
        Menu miMenu = new Menu((ArrayList) opciones);
        System.out.println("Bienvenido al sistema. Aquí podrá crear su propia lista de países y consultarla cuando lo desee");

        do {
            System.out.println("\nIntroduzca la operación que desea realizar");
            miMenu.printMenu();
            opcion = ControlData.lerByte(sc);
            miMenu.rango(opcion);

            /*La primera opción es la que permite al usuario introducir nuevos países. Le saco un menú, cada una de sus 
            opciones lleva a uno de los contructores que proponía el ejercicio (introducir solo el nombre, el 
            nombre y la capital o el nombre, capital y sistema de gobierno. Cada nuevo país introducido crea 
            un objeto que se añade al ArrayList previamente creado.
             */
            switch (opcion) {
                case 1: {
                    System.out.println("¿Qué datos desea aportar del nuevo país?");
                    ArrayList<String> submenu = new ArrayList<String>() {
                        {
                            add("Introducir solo el nombre");
                            add("Introducir el nombre y la capital");
                            add("Introducir el nombre, la capital y el sistema de gobierno");
                        }
                    };
                    Menu subMenu = new Menu((ArrayList) submenu);
                    subMenu.printMenu();
                    opcion2 = ControlData.lerByte(sc);
                    subMenu.rango(opcion2);
                    switch (opcion2) {
                        case 1:
                            System.out.println("¿Cuál es el nombre del país?");
                            nombre = ControlData.lerString(sc);
                            miPais = new Pais(nombre);
                            paises.add(miPais);
                            break;

                        case 2:
                            System.out.println("¿Cuál es el nombre del país?");
                            nombre = ControlData.lerString(sc);
                            System.out.println("¿Cuál es su capital?");
                            capital = ControlData.lerString(sc);
                            miPais = new Pais(nombre, capital);
                            paises.add(miPais);
                            break;

                        /*La opción 3 es un poco más compleja, pues se pedía que el sistema de gobierno
                            fuese un Boolean. Le saco una pregunta a modo de menú, para asegurarme de que 
                            introduce una de las respuestas vinculadas al true y al false
                         */
                        case 3: {
                            System.out.println("¿Cuál es el nombre del país?");
                            nombre = ControlData.lerString(sc);
                            System.out.println("¿Cuál es su capital?");
                            capital = ControlData.lerString(sc);
                            System.out.println("¿Su país es una república?");

                            ArrayList<String> menuGobierno = new ArrayList<String>() {
                                {
                                    add("Sí");
                                    add("No");
                                }
                            };

                            Menu Gobierno = new Menu(menuGobierno);

                            Gobierno.printMenu();
                            opcion3 = ControlData.lerByte(sc);
                            Gobierno.rango(opcion3);
                            switch (opcion3) {
                                case 1:
                                    republica = true;
                                    miPais = new Pais(nombre, capital, republica);
                                    paises.add(miPais);
                                    break;

                                case 2:
                                    republica = false;
                                    miPais = new Pais(nombre, capital, republica);
                                    paises.add(miPais);
                                    break;
                            }
                        }
                        break;
                    }
                }
                break;

                /*La segunda opción del menú principal imprime los países introducidos. Antes de nada, saco 
                el número de países introducido accediento al método apropiado. A continuación, recorro el Arraylist de 
                países varias veces, uno por cada propiedad, ya que no tienen las mismas características (por ejemplo,
                algunas son obligatorias para que se registre un país y otras no).                  
                 */
                case 2: {
                    System.out.println("Ha introducido un total de " + Pais.getTotalPaises() + " países.");
                    for (int i = 0; i < paises.size(); ++i) {
                        System.out.println("\nNombre: " + (paises.get(i)).getNombre());
                        if (paises.get(i).getCapital() == null) {
                            System.out.println("Capital: no especificada");
                        } else {
                            System.out.println("Capital: " + (paises.get(i)).getCapital());
                        }
                        if (paises.get(i).getRepublica() == null) {
                            System.out.println("Sistema de gobierno: no especificado");
                        } else if (paises.get(i).getRepublica() == true) {
                            System.out.println("Sistema de gobierno: República");
                        } else {
                            System.out.println("Sistema de gobierno: Monarquía");
                        }
                        if (paises.get(i).getHabitantes() == 0) {
                            System.out.println("No se ha introducido el número de habitantes");
                        } else {
                            System.out.println("Número de habitantes: " + (paises.get(i)).getHabitantes());
                        }
                        if ((paises.get(i)).getLenguas() == null) {
                            System.out.println("No se han introducido lenguas oficiales");
                        } else {
                            System.out.println("Lenguas oficiales: " + (paises.get(i)).getLenguas());
                        }
                    }
                    break;
                }

                /*La opción 3 permite introducir cualquiera de los atributos para los países creados, estén o no 
                definidos con anterioridad. Primero le pregunto al usuario que dato desea modificar, después 
                le saco un menú con la lista de países introducidos para que escoja. Registro el input con el tipo
                adecuado y lo añado al array de países con el set correspondiente.
                 */
                case 3: {

                    if (paises.isEmpty()) {
                        System.out.println("Primero debe introducir algún país");
                    } else {
                        System.out.println("¿Qué dato deseas añadir o modificar?");

                        ArrayList<String> editar = new ArrayList<String>() {
                            {
                                add("Modificar un nombre");
                                add("Añadir o modificar una capital");
                                add("Añadir o modificar el sistema de gobierno");
                                add("Añadir lenguas oficiales");
                                add("Añadir o modificar el número de habitantes");
                                add("Cancelar");
                            }
                        };

                        Menu editorPaises = new Menu(editar);

                        editorPaises.printMenu();
                        opcion4 = ControlData.lerInt(sc);

                        switch (opcion4) {

                            case 1:
                                System.out.println("Qué país desea modificar?");
                                for (int i = 0; i < paises.size(); ++i) {
                                    System.out.println("\n" + (i + 1) + ": " + (paises.get(i)).getNombre());
                                }
                                opcion6 = -1;
                                do {
                                    opcion6 = ControlData.lerInt(sc);
                                    ControlData.rango(0, paises.size(), opcion6);
                                } while (opcion6 < 0 || opcion6 > paises.size());

//                                        ControlData.rango(0, paises.size() - 1, (ControlData.lerInt(sc) - 1));
                                System.out.println("Introduzca el nuevo nombre del país");
                                String nuevoNombre = ControlData.lerString(sc);
                                paises.get(opcion6 - 1).setNombre(nuevoNombre);
                                break;
                            case 2:
                                System.out.println("Qué país desea modificar?");
                                for (int i = 0; i < paises.size(); ++i) {
                                    System.out.println("\n" + (i + 1) + ": " + (paises.get(i)).getNombre());
                                }
                                opcion6 = -1;
                                do {
                                    opcion6 = ControlData.lerInt(sc);
                                    ControlData.rango(0, paises.size(), opcion6);
                                } while (opcion6 < 0 || opcion6 > paises.size());

                                System.out.println("Introduzca la capital del país");
                                String nuevaCapital = ControlData.lerString(sc);
                                paises.get(opcion6 - 1).setCapital(nuevaCapital);
                                break;
                            case 3:
                                System.out.println("Qué país desea modificar?");
                                for (int i = 0; i < paises.size(); ++i) {
                                    System.out.println("\n" + (i + 1) + ": " + (paises.get(i)).getNombre());
                                }
                                opcion6 = -1;
                                do {
                                    opcion6 = ControlData.lerInt(sc);
                                    ControlData.rango(0, paises.size(), opcion6);
                                } while (opcion6 < 0 || opcion6 > paises.size());

                                System.out.println("¿Su país es una república?");

                                ArrayList<String> menuGobierno = new ArrayList<String>() {
                                    {
                                        add("Sí");
                                        add("No");
                                    }
                                };

                                Menu Gobierno = new Menu(menuGobierno);

                                Gobierno.printMenu();
                                opcion3 = ControlData.lerByte(sc);
                                Gobierno.rango(opcion3);
                                switch (opcion3) {
                                    case 1:
                                        republica = true;
                                        paises.get(opcion6 - 1).setRepublica(true);
                                        break;

                                    case 2:
                                        republica = false;
                                        paises.get(opcion6 - 1).setRepublica(false);
                                        break;

                                }
                                break;

                            case 4:
                                System.out.println("Qué país desea modificar?");
                                for (int i = 0; i < paises.size(); ++i) {
                                    System.out.println("\n" + (i + 1) + ": " + (paises.get(i)).getNombre());
                                }
                                opcion6 = -1;
                                do {
                                    opcion6 = ControlData.lerInt(sc);
                                    ControlData.rango(0, paises.size(), opcion6);
                                } while (opcion6 < 0 || opcion6 > paises.size());
                                System.out.println("¿Cuántas lenguas oficiales desea introducir?");
                                int numLenguas = ControlData.lerInt(sc);
                                System.out.println("Introduzca las lenguas oficiales");
                                for (int i = 0; i < numLenguas; ++i) {
                                    lenguas.add(i, ControlData.lerString(sc));

                                }
                                ((Pais) paises.get(opcion6 - 1)).setLenguas(lenguas);
                                break;
                            case 5:
                                System.out.println("Qué país desea modificar?");
                                for (int i = 0; i < paises.size(); ++i) {
                                    System.out.println("\n" + (i + 1) + ": " + (paises.get(i)).getNombre());
                                }
                                opcion6 = -1;
                                do {
                                    opcion6 = ControlData.lerInt(sc);
                                    ControlData.rango(0, paises.size(), opcion6);
                                } while (opcion6 < 0 || opcion6 > paises.size());

                                System.out.println("Introduzca el número de habitantes del país del país");
                                long habitantes = ControlData.lerLong(sc);
                                paises.get(opcion6 - 1).setHabitantes(habitantes);
                                break;
                        }
                    }
                }
                break;

                /*La opción4 permite borrar uno de los países introducidos, eliminándolo su registro del Arraylist
                 */
                case 4:
                    if (paises.isEmpty()) {
                        System.out.println("Primero debe introducir algún país");
                    } else {
                        System.out.println("¿Qué país desea eliminar?");
                        for (int i = 0; i < paises.size(); ++i) {
                            System.out.println("\n" + (i + 1) + ": " + (paises.get(i)).getNombre());
                        }
                        opcion6 = -1;
                        do {
                            opcion6 = ControlData.lerInt(sc);
                            ControlData.rango(0, paises.size(), opcion6);
                        } while (opcion6 < 0 || opcion6 > paises.size());

                        System.out.println("Ha eliminado el país " + paises.get(opcion6 - 1).getNombre());
                        Pais.setTotalPaises(Pais.getTotalPaises() - 1);
                        paises.remove(opcion6 - 1);
                    }
                    break;

                case 5:
                    System.out.println("Ha abandonado el programa. Que tenga un buen día");
                    break;
            }

        } while (opcion != 5);
        sc.close();
    }
}
