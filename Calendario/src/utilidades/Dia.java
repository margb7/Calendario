package utilidades;

public enum Dia {
    
    LUNS("Luns", "lu."),
    MARTES("Martes", "ma."),
    MERCORES("Mércores", "me."),
    XOVES("Xoves", "xo."),
    VENRES("Venres", "ve."),
    SABADO("Sábado", "sá."),
    DOMINGO("Domingo", "do.");

    private String nome;
    private String nomeCurto;

    Dia(String nome, String nomeCurto ) {

        this.nome = nome;
        this.nomeCurto = nomeCurto;

    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @return the nomeCorto
     */
    public String getNomeCurto() {
        return nomeCurto;
    }

    @Override
    public String toString() {
        return nome;
    }

}
