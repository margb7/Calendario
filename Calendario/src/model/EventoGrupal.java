package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class EventoGrupal extends Evento {
 
    public EventoGrupal(int id, String nome, int creador, LocalDate data, LocalTime tempo) {
        super(id, nome, creador,data, tempo);
    }

}
