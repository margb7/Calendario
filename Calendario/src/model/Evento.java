package model;

import java.time.LocalDate;
import java.time.LocalTime;

public abstract class Evento {
    
    private String nome;
    private LocalDate data;
    private LocalTime tempo;
    private int id;

    public Evento(int id, String nome, LocalDate data, LocalTime tempo ) {

        this.id = id;
        this.nome = nome;
        this.data = data;
        this.tempo = tempo;
        
    }

    @Override
    public String toString() {
        return nome;
    }

}
