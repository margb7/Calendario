package model;

/**
 * Representa un usuario no programa
 */
public class Usuario {
    
    private String nome;
    private String contrasinal;
    private final int ID;

    /**
     * Constructor do usuario con todos os parámetros
     * @param id o id do usuario na base de datos
     * @param nome o nome deste usuario
     * @param contrasinal a contrasinal do usuario
     */
    public Usuario(int id, String nome, String contrasinal ) {

        this.ID = id;
        this.nome = nome;
        this.contrasinal = contrasinal;

    }

    /**
     * Constructor do usuario co id e nome
     * @param id o id do usuario na base de datos
     * @param nome o nome deste usuario
     */
    public Usuario(int id, String nome ) {
        this(id, nome, "");
    }

    /**
     * Devolve o id deste usuario
     * @return un int co id do usuario
     */
    public int getId() {
        return ID;
    }

    /**
     * Devolve o nome do usuario
     * @return o nome do usuario
     */
    public String getNome() {
        return nome;
    }

    /**
     * Devolve a contrasinal do usuario
     * @return un string coa contrasinal do usuario
     */
    public String getContrasinal() {
        return contrasinal;
    }

}