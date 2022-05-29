package excepcions;

/**
 * Excepción para indicar que xa existe un evento
 */
public class EventoXaExisteException extends Exception {
    
    /**
     * Constructor da excepción
     * @param mensaxe a mensaxe de erro
     */
    public EventoXaExisteException(String mensaxe) {
        super(mensaxe);
    }

}
