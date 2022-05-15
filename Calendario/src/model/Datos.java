package model;

import java.io.File;
import java.io.FileNotFoundException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

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
    private static final String CONEXION_BASE = "jdbc:mysql://localhost/calendario";
    private static final String USUARIO_CONEXION = "root";
    private static final String PASSWD_CONEXION = "root";

    private static Connection conexionBase;

    /**
     * Constructor privado para evitar instancias
     */
    private Datos() {}

    /**
     * Constructor estático para inicializar as datas
     */

    public static void init() {

        iniciarConexionBBDD();

    }

    private static void iniciarConexionBBDD() {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            try {

                conexionBase = DriverManager.getConnection(CONEXION_BASE, USUARIO_CONEXION, PASSWD_CONEXION);

            } catch (SQLException e) {

                System.out.println("Non se puido conectar coa base de datos");
                
                conexionBase = null;

            }
            
        } catch (ClassNotFoundException e) {
            
            System.out.println("Non se puido atopar o driver de jdbc");
            conexionBase = null;

        }

    }

    public static Connection getConexionBase() {
        return conexionBase;
    }

    /**
     * Obtén da base de datos todos os eventos dun usuario e dunha data en concreto
     * @param dataEvento a data para buscar eventos.
     * @param user o usuario dos eventos.
     * @return a lista de eventos con todos os eventos ou unha lista vacía de eventos
     */
    public static Evento[] getEventosDia(LocalDate dataEvento, Usuario user) {

        Evento[] out = new Evento[0];
        ArrayList<Evento> eventos = new ArrayList<>();
        
        getEventosPrivados(dataEvento, user).forEach(el -> {

            eventos.add(el);

        });

        getEventosPublicos(dataEvento).forEach(el -> {

            eventos.add(el);
            
        });

        getEventosGrupales(dataEvento, user).forEach(el -> {

            eventos.add(el);
            
        });

        out = eventos.toArray(out);

        return out;
    }

    /**
     * Devolve un usuario da base de datos. 
     * @param nome o nome de usuario
     * @return o usuario
     * @throws NameNotFoundException se non se atopa o usuario
     */
    public static Usuario getUsuarioPorNome(String nome ) throws UsuarioNonAtopadoException{

        Usuario out = null;
        
        try {

            PreparedStatement statement = conexionBase.prepareStatement("SELECT ID_USUARIO, PASSWD FROM USUARIOS WHERE NOME = ? ");

            statement.setString(1, nome);

            ResultSet set = sentenciaLectura(statement);

            if (set.next() ) {

                int id = set.getInt(1);
                String passwd = set.getString(2);

                out = new Usuario(id, nome, passwd);

            } else {

                throw new UsuarioNonAtopadoException("Para o usuario con nome: " + nome);

            }


        } catch (SQLException e) {

            System.out.println("Error coa consulta para obter o usuario por nome");

        }

        return out;
    }

    public static boolean usuarioEstaRexistrado(String nome ) {

        boolean out = false;

        try {

            PreparedStatement statement = conexionBase.prepareStatement("SELECT ID_USUARIO FROM USUARIOS WHERE NOME = ? ");

            statement.setString(1, nome);

            ResultSet set = sentenciaLectura(statement);

            if (set.next() ) {

                out = true;

            }

        } catch (SQLException e) {

            System.out.println("Error coa consulta para saber se o usuario está rexistrado");

        }

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
    public static HashMap<String, HashMap<String, String>> cargarIdiomas() {

        File carpetaIdiomas = new File(CARPETA_IDIOMAS);
        String[] lines;
        String nomeIdioma;  // O nome de cada idioma correspóndese co nome do ficheiro sen a extensión ".txt"
        String codigo;
        String significado;
        HashMap<String, String> trad;   // Hash map que corresponde o código co seu valor  
        HashMap<String, HashMap<String, String>> idiomas;

        idiomas = new HashMap<>();

        if(carpetaIdiomas.exists() ) {

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

        } else {

            System.out.println("Non se atopou a carpeta de idiomas -> valores por defecto en galego");

        }
        
        return idiomas;
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

    private static ArrayList<Evento> getEventosPrivados(LocalDate dia, Usuario user ) {

        ArrayList<Evento> eventos = new ArrayList<>();

        try {

            PreparedStatement statement = conexionBase.prepareStatement("SELECT ID_EVENTO, NOME, HORA FROM VISTA_EVENTOS_PRIVADOS WHERE CREADOR = ? AND DATA_EVENTO = ? ");

            statement.setInt(1, user.getId());
            statement.setString(2, dia.toString());

            ResultSet set = sentenciaLectura(statement);

            if(set != null ) {

                while(set.next() ) {

                    eventos.add(new EventoPrivado(set.getInt(1), set.getString(2), user.getId(), dia, set.getTime(3).toLocalTime() ));
    
                }

            }

        } catch (SQLException e) {

            System.out.println("Error coa consulta para obter eventos privados");
            e.printStackTrace();

        }

        return eventos;
    }

    private static ArrayList<Evento> getEventosPublicos(LocalDate dia ) {

        ArrayList<Evento> eventos = new ArrayList<>();

        try {

            PreparedStatement statement = conexionBase.prepareStatement("SELECT ID_EVENTO, NOME, CREADOR, HORA FROM VISTA_EVENTOS_PUBLICOS WHERE DATA_EVENTO = ? ");

            statement.setString(1, dia.toString());

            ResultSet set = sentenciaLectura(statement);

            if(set != null ) {

                while(set.next() ) {

                    eventos.add(new EventoPublico(set.getInt(1), set.getString(2), set.getInt(3), dia, set.getTime(4).toLocalTime() ));
    
                }

            }
            

        } catch (SQLException e) {

            System.out.println("Error coa consulta para obter eventos públicos");
            e.printStackTrace();

        }

        return eventos;
    }

    private static ArrayList<Evento> getEventosGrupales(LocalDate dia, Usuario user ) {

        ArrayList<Evento> eventos = new ArrayList<>();

        try {

            PreparedStatement statement = conexionBase.prepareStatement("SELECT ID_EVENTO, NOME, CREADOR, HORA FROM VISTA_EVENTOS_GRUPAIS WHERE USUARIO = ? AND DATA_EVENTO = ?");

            statement.setInt(1, user.getId());
            statement.setString(2, dia.toString());

            ResultSet set = sentenciaLectura(statement);

            if(set != null ) {

                while(set.next() ) {

                    eventos.add(new EventoGrupal(set.getInt(1), set.getString(2), set.getInt(3), dia, set.getTime(4).toLocalTime() ));
    
                }

            }
            
        } catch (SQLException e) {

            System.out.println("Error coa consulta para obter eventos grupais");
            e.printStackTrace();

        }

        return eventos;
    }

    public static ResultSet sentenciaLectura(PreparedStatement st) {

        ResultSet out;

        try {

            out = st.executeQuery();

        } catch (SQLException e) {
            
            out = null;

        }

        return out;

    }

}
