package model;

public class Usuario {
    
    private String nome;
    private String contrasinal;
    private final int ID;

    public Usuario(int id, String nome, String contrasinal ) {

        this.ID = id;
        this.nome = nome;
        this.contrasinal = contrasinal;

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

    public String getContrasinal() {
        return contrasinal;
    }

}
