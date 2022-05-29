package model;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Clase para representar un evento no programa
 */
public abstract class Evento {
    
    private String nome;
    private LocalDate data;
    private LocalTime tempo;
    private int id;
    private int creador; // Representa o id do creador deste evento

    /**
     * Constructor do evento
     * @param id id do evento na base de datos
     * @param nome o nome do evento
     * @param creador o id do usuario creador deste evento
     * @param data a data do evento
     * @param tempo a hora do evento
     */
    public Evento(int id, String nome, int creador, LocalDate data, LocalTime tempo ) {

        this.id = id;
        this.nome = nome;
        this.creador = creador;
        this.data = data;
        this.tempo = tempo;
        
    }

    /**
     * Devolve o id do creador deste evento
     * @return un int co id
     */
    public final int getCreador() {
        return creador;
    }

    /**
     * Devolve a data na que ocorre este evento
     * @return un <code>LocalDate</code> coa data do evento
     */
    public final LocalDate getData() {
        return data;
    }

    /**
     * Devolve a hora na que ocorre este evento
     * @return un <code>LocalTime</code> coa hora do evento
     */
    public final LocalTime getTempo() {
        return tempo;
    }

    /**
     * Devolve o id do evento
     * @return un int co id do evento
     */
    public final int getId() {
        return id;
    }

    /**
     * Devolve o nome od evento
     * @return un <code>String</code> co nome do evento
     */
    public final String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return nome;
    }

}
