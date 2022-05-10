package calendario;

public interface Controller<T> {
    
    default public void crearListeners(T instancia){};
    abstract void crearTraduccions(T instancia);
    abstract T instanciarElemento();
    
}
