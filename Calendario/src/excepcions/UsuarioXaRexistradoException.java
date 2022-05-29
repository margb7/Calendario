package excepcions;

/**
 * Excepción para indicar que xa existe un usuario cando
 * se intenta crear outro igual
 */
public class UsuarioXaRexistradoException extends Exception{
    
    /**
     * Constructor da excepción
     * @param mensaxe a mensaxe de erro
     */
    public UsuarioXaRexistradoException(String mensaxe ) {

        super(mensaxe);

    }

}
