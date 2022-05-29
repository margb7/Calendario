package excepcions;

/**
 * Excepción para indicar que un usuario que foi buscado non
 * se atopa na base de datos
 */
public class UsuarioNonAtopadoException extends Exception {
    
    /**
     * Constructor da excepción
     * @param mensaxe a mensaxe de erro
     */
    public UsuarioNonAtopadoException(String mensaxe) {
        super(mensaxe);
    }

}
