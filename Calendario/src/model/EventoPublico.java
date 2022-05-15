package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class EventoPublico extends Evento {
    
    public EventoPublico(int id, String nome, int creador, LocalDate data, LocalTime tempo) {
        super(id, nome, creador, data, tempo);
    }

    public static EventoPublico parse(String str) {

        EventoPublico out = null;



        return out;
    }
}
