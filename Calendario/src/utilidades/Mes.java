package utilidades;

public enum Mes {
    
    XANEIRO("Xaneiro"),
    FEBREIRO("Febreiro"),
    MARZO("Marzo"),
    ABRIL("Abril"),
    MAIO("Maio"),
    XUNIO("Xuño"),
    XULIO("Xullo"),
    AGOSTO("Agosto"),
    SETEMBRO("Setembro"),
    OUTUBRO("Outubre"),
    NOVEMBRE("Novembre"),
    DECEMBRE("Decembro");

    private String nome;

    Mes(String nome ) {

        this.nome = nome;

    }

    @Override
    public String toString() {
        return nome;
    }

}
