package excepcions;

/**
 * Excepción que salta cando algún campo non é correcto
 */
public class CredenciaisIncorrectasException extends Exception{
    
    private Tipo tipoErro;

    /**
     * Constructor da excepción
     * @param tipoErro a razón pola que salta
     * @param mensaxe a mensaxe de erro
     */
    public CredenciaisIncorrectasException(Tipo tipoErro, String mensaxe ) {
        
        super(mensaxe);
        this.tipoErro = tipoErro;

    }

    /**
     * Obtén o tipo de erro polo que esta excepción foi lanzada
     * @return o tipo de erro
     */
    public Tipo getTipoErro() {
        return tipoErro;
    }
    
    /**
     * Enumeración cos motivos polos que pode saltar a excepción de <code>CredenciaisIncorrectasException</code>
     */
    public enum Tipo {

        NOME_NON_VALIDO(1),
        CONTRASINAL_NON_VALIDA(2),
        CONTRASINAL_NON_COINCIDE(3);

        private int tipo;

        /**
         * O constructor desta enumeración
         * @param tipo
         */
        Tipo(int tipo ) {

            this.tipo = tipo;

        }

        /**
         * Devolve o tipo
         * @return un int co tipo
         */
        public int getTipo() {
            return tipo;
        }

    }

}
