package model;

public class Usuario {
    
    private String nome;
    private final int ID;

    public Usuario(int id, String nome ) {

        this.ID = id;
        this.nome = nome;

    }

    /**
     * @return the id
     */
    public int getId() {
        return ID;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

}
