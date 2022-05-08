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

    /**
     * Comproba que o nome é sintácticamente válido para evitar rexistrar nomes con espacios por exemplo.
     * Non comproba que o usuario esté xa rexistrado na base de datos.
     * @param str a cadea co nome de usuario.
     * @return true se é válido. Un nome válido contén como mínimo 3 caracteres e ningún espacio.
     */
    public static boolean nomeUsuarioValido(String str ) {

        boolean out = false;

        if(str.length() >= 3 ) {

            if(!str.contains(" ") ) {

                out = true;

            }

        } 

        return out;

    }

}
