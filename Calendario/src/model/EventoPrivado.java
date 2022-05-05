package model;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * 
 */
public class EventoPrivado extends Evento {

    // -> -> -> La principal razón por la que no se registra el usuario 
            // es que los eventos privados se instancian cuando se pide a la base de datos
            // los eventos privados de x usuario que siempre será el usuario
            // que usa la aplicación

    public EventoPrivado(int id, String nome, LocalDate data, LocalTime tempo) {
        super(id, nome, data, tempo);
    }

    public static EventoPrivado parse(String str) {

        String[] lines = str.split(",");
        int id;
        String nome;
        LocalDate data;
        LocalTime time;

        id = Integer.parseInt(lines[0]);
        nome = lines[1];
        data = LocalDate.parse(lines[2]);
        time = LocalTime.parse(lines[3]);
        
        return new EventoPrivado(id, nome, data, time);
    }
    
}
