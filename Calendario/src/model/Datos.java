package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Clase para consultas de datos (BBDD / ficheros). 
 */
public class Datos {
    
    private static LocalDate data;
    private static LocalTime tempo;

    /**
     * Constructor privado para evitar instancias
     */
    private Datos() {}

    /**
     * Constructor estático para inicializar as datas
     */
    static {

        // TODO : borrar constructor estático, borrar "data" y borrar "tempo" 

        data = LocalDate.now();
        tempo = LocalTime.now();

    }

    /**
     * Obtén da base de datos todos os eventos dun usuario e dunha data en concreto
     * @param dataEvento a data para buscar eventos
     * @return a lista de eventos con todos os eventos ou unha lista vacía de eventos
     */
    public static Evento[] getEventosDia(LocalDate dataEvento) {    // Añadir usuario como parámetro

        Evento[] listaEventos = new Evento[0];

        // getEventosPublicos(dia);
        // getEventosPrivados(dia,user);
        // getEventosGrupales(dia, user);

        // TODO: gardar contido nun array de eventos

        if(dataEvento.equals(LocalDate.of(2022, Month.JUNE, 6)) ) {

            listaEventos = new Evento[2];
            listaEventos[0] = new EventoPrivado(0, "Vacaciones", data, tempo);
            listaEventos[1] = new EventoPrivado(0, "No más examenes", data, tempo);

        } else {

            listaEventos = new Evento[2];
            listaEventos[0] = new EventoPrivado(0, "Acabar el modelo", data, tempo);
            listaEventos[1] = new EventoPrivado(0, "Tomar cafe", data, tempo);

        }

        return listaEventos;
    }

    public static String[] leerFichero(String path ) {
        
        Scanner sc;
        ArrayList<String> lineas = new ArrayList<>();

        try {

            sc = new Scanner(new File(path));
            
            while(sc.hasNextLine() ) {

                lineas.add(sc.nextLine());

            }

        } catch(FileNotFoundException e) {

            System.out.println("Arquivo non atopado : " + path);

        }

        return (String[]) lineas.toArray();
    }

    private static Evento[] getEventosPrivados(LocalDate dia, Usuario user ) {

        Evento[] out = null;
        String[] consulta = null;       // consulta = pedirDatos() 
        
        /*
        
        out = new String[consulta.length];

        for(int i = 0; i < consulta.length; i++ ) {

            out[i] = EventoPrivado.parse(consulta[i]);

        }

        */

        return out;
    }

    private static Evento[] getEventosPublicos(LocalDate dia ) {

        Evento[] out = null;
        String[] consulta = null;       // consulta = pedirDatos() 
        
        /*
        
        out = new String[consulta.length];

        for(int i = 0; i < consulta.length; i++ ) {

            out[i] = EventoPublico.parse(consulta[i]);

        }

        */

        return out;
    }

    private static Evento[] getEventosGrupales(LocalDate dia, Usuario user ) {

        Evento[] out = null;
        String[] consulta = null;       // consulta = pedirDatos() 
        
        /*
        
        out = new String[consulta.length];

        for(int i = 0; i < consulta.length; i++ ) {

            out[i] = EventoGrupal.parse(consulta[i]);

        }

        */

        return out;
    }

}
