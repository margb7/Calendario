package model;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Representa un evento público no programa
 */
public class EventoPublico extends Evento {
    
    /**
     * Constructor do evento
     * @param id id do evento na base de datos
     * @param nome o nome do evento
     * @param creador o id do usuario creador deste evento
     * @param data a data do evento
     * @param tempo a hora do evento
     */
    public EventoPublico(int id, String nome, int creador, LocalDate data, LocalTime tempo) {
        super(id, nome, creador, data, tempo);
    }

    @Override
    public String toString() {
        return getNome() + "   - " + getTempo().toString();
    }

}
