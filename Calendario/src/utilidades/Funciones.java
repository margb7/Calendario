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

    public static boolean contrasinalValida(String str) {

        boolean out = false;

        if(str.length() >= 3 ) {

            if(!str.contains(" ") ) {

                if(str.matches(".*[0-9].*") ) {

                    out = true;

                }

            }

        } 

        return out;
    }

}
