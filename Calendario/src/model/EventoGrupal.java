package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class EventoGrupal extends Evento {
 
    public EventoGrupal(int id, String nome, LocalDate data, LocalTime tempo) {
        super(id, nome, data, tempo);
    }

    public static EventoGrupal parse(String str) {

        EventoGrupal out = null;



        return out;
    }

}
