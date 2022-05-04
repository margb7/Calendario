package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class EventoPrivado extends Evento {

    public EventoPrivado(int id, String nome, LocalDate data, LocalTime tempo) {
        super(id, nome, data, tempo);
    }

    public static EventoPrivado parse(String str) {

        EventoPrivado out = null;



        return out;
    }
    
}
