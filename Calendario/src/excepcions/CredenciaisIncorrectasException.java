package excepcions;

public class CredenciaisIncorrectasException extends Exception{
    
    private Tipo tipoErro;

    public CredenciaisIncorrectasException(Tipo tipoErro, String mensaxe ) {
        
        super(mensaxe);
        this.tipoErro = tipoErro;

    }

    public Tipo getTipoErro() {
        return tipoErro;
    }

    public enum Tipo {

        NOME_NON_VALIDO(1),
        CONTRASINAL_NON_VALIDA(2),
        CONTRASINAL_NON_COINCIDE(3);

        private int tipo;

        Tipo(int tipo ) {

            this.tipo = tipo;

        }

        public int getTipo() {
            return tipo;
        }

    }

}
