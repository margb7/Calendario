package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.naming.NameNotFoundException;

import excepcions.UsuarioNonAtopadoException;
import excepcions.UsuarioXaRexistradoException;

/**
 * Clase para consultas de datos (BBDD / ficheros). 
 */
public class Datos {
    
    private static final String CARPETA_IDIOMAS = "Idiomas";
    private static LocalDate data;
    private static LocalTime tempo;
    private static HashMap<String, String> idomaSeleccionado; 
    private static HashMap<String, HashMap<String, String>> idiomas;

    /**
     * Constructor privado para evitar instancias
     */
    private Datos() {}

    /**
     * Constructor estático para inicializar as datas
     */
    static {

        data = LocalDate.now();
        tempo = LocalTime.now();
        idiomas = new HashMap<>();
        cargarIdiomas();
        
        if(idiomas.containsKey("Galego") ) {

            Datos.idomaSeleccionado = idiomas.get("Galego");

        }

    }

    /**
     * @return the idiomas
     */
    public static HashMap<String, HashMap<String, String>> getIdiomas() {
        return idiomas;
    }

    /**
     * @param idomaSeleccionado the idomaSeleccionado to set
     */
    public static void setIdomaSeleccionado(String idioma) {
        
        if(idiomas.containsKey(idioma) ) {

            Datos.idomaSeleccionado = idiomas.get(idioma);

        }

    }

    /**
     * Obtén da base de datos todos os eventos dun usuario e dunha data en concreto
     * @param dataEvento a data para buscar eventos.
     * @param user o usuario dos eventos.
     * @return a lista de eventos con todos os eventos ou unha lista vacía de eventos
     */
    public static Evento[] getEventosDia(LocalDate dataEvento, Usuario user) {    // Añadir usuario como parámetro

        Evento[] listaEventos = new Evento[0];

        // getEventosPublicos(dia);
        // getEventosPrivados(dia,user);
        // getEventosGrupales(dia, user);

        // TODO: gardar contido nun array de eventos

        if(dataEvento.equals(LocalDate.of(2022, Month.JUNE, 2)) ) {

            listaEventos = new Evento[2];
            listaEventos[0] = new EventoPrivado(0, "Vacaciones", data, tempo);
            listaEventos[1] = new EventoPrivado(0, "examenes = null", data, tempo);

        } else if(dataEvento.getMonthValue() == 12 && dataEvento.getDayOfMonth() == 25 ) {
        
            listaEventos = new Evento[1];
            listaEventos[0] = new EventoPrivado(0, "Navidad", data, tempo);

        } else {

            listaEventos = getEventosPrivados(dataEvento, user);

        }

        return listaEventos;
    }

    /**
     * Devolve un usuario da base de datos. 
     * @param nome o nome de usuario
     * @return o usuario
     * @throws NameNotFoundException se non se atopa o usuario
     */
    public static Usuario getUsuarioPorNome(String nome ) throws UsuarioNonAtopadoException{

        Usuario out = null;

        if(nome.equals("administrador") ) {

            out = new Usuario(0, "administrador", "renaido");  // TODO: valor temporal para probas

        } else {

            throw new UsuarioNonAtopadoException("Para o nome : " + nome);

        }

        return out;
    }

    public static boolean usuarioEstaRexistrado(String nome ) {

        boolean out = false;

        out = nome.equals("administrador");

        return out;
    } 

    public static Usuario rexistrarUsuario(String nome, String contrasinal ) throws UsuarioXaRexistradoException{

        Usuario out;

        if(usuarioEstaRexistrado(nome) ) {

            throw new UsuarioXaRexistradoException("Para o usuario: " + nome);

        }

        // TODO: id proporcionado pola base de datos
        out = new Usuario(0, nome, contrasinal);

        return out;
    }

    /**
     * Carga os idiomas da carpeta de idiomas e os garda para o seu uso.
     */
    public static void cargarIdiomas() {

        File carpetaIdiomas = new File(CARPETA_IDIOMAS);
        String[] lines;
        String nomeIdioma;  // O nome de cada idioma correspóndese co nome do ficheiro sen a extensión ".txt"
        String codigo;
        String significado;
        HashMap<String, String> trad;   // Hash map que corresponde o código co seu valor 

        for(File f : carpetaIdiomas.listFiles() ) {

            lines = leerFichero(f.getAbsolutePath());
            nomeIdioma = f.getName().substring(0, f.getName().length() - 4);
            trad = new HashMap<>();

            for(String s : lines ) {

                if(!s.isEmpty() && !s.startsWith("--") ) {

                    codigo = s.substring(0, 3);
                    significado = s.substring(4, s.length());

                    trad.put(codigo, significado);

                }

            }

            idiomas.put(nomeIdioma, trad);

        }
        
    }

    /**
     * Devolve o valor no idioma seleccionado para un valor de texto do programa.
     * @param codigo o código da traducción
     * @param valorPorDefecto o valor no caso de non atopar a traducción.
     * @return a cadea co valor de texto que corresponde.
     */
    public static String getTraduccion(String codigo, String valorPorDefecto ) {

        String out;

        if(idomaSeleccionado.containsKey(codigo) ) {

            out = idomaSeleccionado.get(codigo);

        } else {

            out = valorPorDefecto;

        }

        return out;
    }

    public static String[] leerFichero(String path ) {
        
        Scanner sc;
        ArrayList<String> lineas = new ArrayList<>();
        String[] out;

        try {

            sc = new Scanner(new File(path), "UTF-8");
            
            while(sc.hasNextLine() ) {

                lineas.add(sc.nextLine());

            }

        } catch(FileNotFoundException e) {

            System.out.println("Arquivo non atopado : " + path);

        }

        out = new String[lineas.size()];

        for (int i = 0; i < out.length; i++) {
            
            out[i] = lineas.get(i);

        }

        return out;
    }

    private static Evento[] getEventosPrivados(LocalDate dia, Usuario user ) {

        Evento[] out = null;
        String[] consulta = null;
        
        consulta = leerFichero(Path.of("Test", "EjemplosEventos.txt").toString());    // = pedirDatosBBDD() 

        out = new Evento[consulta.length];

        for(int i = 0; i < consulta.length; i++ ) {

            out[i] = EventoPrivado.parse(consulta[i]);

        }

        return out;
    }

    private static Evento[] getEventosPublicos(LocalDate dia ) {

        Evento[] out = null;
        String[] consulta = null;       // consulta = pedirDatos() 
        
        /*
        
        out = new String[consulta.length];

        for(int i = 0; i < consulta.length; i++ ) {

            out[i] = EventoPublico.parse(consulta[i]);

        }

        */

        return out;
    }

    private static Evento[] getEventosGrupales(LocalDate dia, Usuario user ) {

        Evento[] out = null;
        String[] consulta = null;       // consulta = pedirDatos() 
        
        /*
        
        out = new String[consulta.length];

        for(int i = 0; i < consulta.length; i++ ) {

            out[i] = EventoGrupal.parse(consulta[i]);

        }

        */

        return out;
    }

}
