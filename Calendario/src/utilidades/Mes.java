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
    OUTUBRO("Outubro"),
    NOVEMBRO("Novembro"),
    DECEMBRO("Decembro");

    private String nome;

    Mes(String nome ) {

        this.nome = nome;

    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return nome;
    }

}
