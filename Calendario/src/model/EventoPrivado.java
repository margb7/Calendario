package model;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * 
 */
public class EventoPrivado extends Evento {

    public EventoPrivado(int id, String nome, int creador, LocalDate data, LocalTime tempo) {
        super(id, nome, creador, data, tempo);
    }

    /*public static EventoPrivado parse(String str) {

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
    */
}
