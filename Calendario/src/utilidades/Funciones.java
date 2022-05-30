package utilidades;

/**
 * Funcions que controlan que a entrada sexa válida
 */
public class Funciones {
    
    /**
     * Comproba que a cadea proporcionada sexa unha contrasinal válida
     * @param str a cadea da contrasinal
     * @return true se é unha contrasinal válida
     */
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

    /**
     * Purifica unha cadea de texto para que a hora de introducila na base de datos non de problemas 
     * (por exemplo 'hola' para a base de datos é igual a 'hola ').
     * @param str a cadea para purificar
     * @return o resultado
     */
    public static String purificarString(String str ) {

        String out = str;
        
        out = out.trim();
        out = out.replaceAll("( )+", " ");

        return out;
    }

}