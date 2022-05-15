package model;

import java.time.LocalDate;
import java.time.LocalTime;

public abstract class Evento {
    
    private String nome;
    private LocalDate data;
    private LocalTime tempo;
    private int id;
    private int creador; // Representa o id do creador deste evento

    public Evento(int id, String nome, int creador, LocalDate data, LocalTime tempo ) {

        this.id = id;
        this.nome = nome;
        this.creador = creador;
        this.data = data;
        this.tempo = tempo;
        
    }

    public int getCreador() {
        return creador;
    }

    public LocalDate getData() {
        return data;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return nome;
    }

}
