package utilidades;

import model.Evento;

public class Funciones {
    
    public static Evento[] copiarArray(Evento[] arr ) {

        Evento[] lista = new Evento[arr.length];

        for(int i = 0; i < arr.length; i++ ) {

            lista[i] = arr[i];

        }

        return lista;
    }

}
