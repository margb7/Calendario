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
    private String nomeCorto;

    Dia(String nome, String nomeCorto ) {

        this.nome = nome;
        this.nomeCorto = nomeCorto;

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
    public String getNomeCorto() {
        return nomeCorto;
    }

    @Override
    public String toString() {
        return nome;
    }

}
