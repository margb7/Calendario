package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
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

    public static boolean iniciarConexionBBDD() {

        boolean out = true;

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            try {

                conexionBase = DriverManager.getConnection(CONEXION_BASE, USUARIO_CONEXION, PASSWD_CONEXION);

            } catch (SQLException e) {

                System.out.println("Non se puido conectar coa base de datos");
                conexionBase = null;
                out = false;
                e.printStackTrace();
            }
            
        } catch (ClassNotFoundException e) {
            
            System.out.println("Non se puido atopar o driver de jdbc");
            conexionBase = null;
            out = false;

        }
        
        return out;
    }

    public static Connection getConexionBase() {
        return conexionBase;
    }

    public static boolean existeEventoEnDia(String nome, LocalDate data, Usuario creador ) {

        boolean out = false;

        try {
            
            CallableStatement cs = conexionBase.prepareCall("CALL BUSCAR_EVENTO(?,?,?)");
            ResultSet rs;

            cs.setString(1, nome);
            cs.setDate(2, Date.valueOf(data));
            cs.setInt(3, creador.getId());

            rs = cs.executeQuery();
            
            if(rs.next() && rs.getInt(1) != 0) {

                out = true;

            }

        } catch (SQLException e) {
            
            System.out.println("Erro ao buscar un evento por día");

        }
        
        return out;
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
        
        try {

            getEventosPrivados(dataEvento, user).forEach(el -> {

                eventos.add(el);
    
            });
    
            getEventosPublicos(dataEvento).forEach(el -> {
    
                eventos.add(el);
                
            });
    
            getEventosGrupales(dataEvento, user).forEach(el -> {
    
                eventos.add(el);
                
            });

        } catch(NullPointerException e ) {

            // Para cando non hai conexión coa base de datos (a conexión do jdbc é null)

        }

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

            PreparedStatement statement = conexionBase.prepareStatement("CALL BUSCAR_USUARIO_POR_NOME(?)");

            statement.setString(1, nome);

            ResultSet set = statement.executeQuery();

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

            CallableStatement cs = conexionBase.prepareCall("CALL ESTA_USUARIO_REXISTRADO(?)");
            ResultSet rs;

            cs.setString(1, nome);

            rs = cs.executeQuery();
            
            if(rs.next() && rs.getInt(1) != 0 ) {

                out = true;

            }

        } catch (SQLException e) {

            System.out.println("Error coa consulta para saber se o usuario está rexistrado");

        }

        return out;
    }

    @Deprecated
    public static Usuario getUsuarioPorId(int id) throws UsuarioNonAtopadoException {

        Usuario out = null;

        try {

            PreparedStatement statement = conexionBase.prepareStatement("SELECT NOME FROM USUARIOS WHERE ID_USUARIO = ? ");

            statement.setInt(1, id);

            ResultSet set = statement.executeQuery();

            if (set.next() ) {

                out = new Usuario(id, set.getString(1));

            } else {

                throw new UsuarioNonAtopadoException("Para o id: " + id);

            }

        } catch (SQLException e) {

            System.out.println("Error coa consulta para saber se o usuario está rexistrado");

        }

        return out;
    }

    /**
     * Rexistra o usuario na base de datos. Se non se pode rexistrar este método devolverá un usuario
     * co nome e contrasinal pasados como argumentos pero con <code>id = -1</code>
     * @param nome o nome do usuario a rexistrar. Se xa está rexistrado saltará unha excepciṕn
     * @param contrasinal a contrasinal do usuario.
     * @return un <code>Usuario</code> co nome e contrasinal especificados
     * @throws UsuarioXaRexistradoException se o usuario xa está rexistrado
     */
    public static Usuario rexistrarUsuario(String nome, String contrasinal ) throws UsuarioXaRexistradoException{

        Usuario out;
        int id = -1;

        if(usuarioEstaRexistrado(nome) ) {

            throw new UsuarioXaRexistradoException("Para o usuario: " + nome);

        } else {

            try {

                CallableStatement cs = conexionBase.prepareCall("CALL REXISTRAR_USUARIO(?, ?)");
                ResultSet rs;

                cs.setString(1, nome);
                cs.setString(2, contrasinal);

                rs = cs.executeQuery();
                
                if(rs.next() ) {

                    id = rs.getInt(1);

                }

            } catch(SQLException e) {

                // Chegado aquí devólvese un usuario que so funciona como local (id = -1)
                System.out.println("Erro na consulta para rexistrar usuario");

            }

        }

        out = new Usuario(id, nome, contrasinal);

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
    
                    if(!s.isEmpty() && !s.startsWith("--") && s.matches("^[A-Z][0-9]{2}[-].*")) {
    
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

        return lineas.toArray(out);
    }

    public static ArrayList<Evento> getEventosPrivados(LocalDate dia, Usuario user ) {

        ArrayList<Evento> eventos = new ArrayList<>();

        try {

            PreparedStatement statement = conexionBase.prepareStatement("CALL OBTER_EVENTOS_PRIVADOS(?, ?)");

            statement.setInt(1, user.getId());
            statement.setString(2, dia.toString());

            ResultSet set = statement.executeQuery();

            if(set != null ) {

                while(set.next() ) {
                    
                    eventos.add(new EventoPrivado(set.getInt(1), set.getString(2), user.getId(), dia, set.getTime(4).toLocalTime() ));
    
                }

            }

        } catch (SQLException e) {

            System.out.println("Error coa consulta para obter eventos privados");
            e.printStackTrace();

        }

        return eventos;
    }

    public static ArrayList<Evento> getEventosPublicos(LocalDate dia ) {

        ArrayList<Evento> eventos = new ArrayList<>();

        try {

            PreparedStatement statement = conexionBase.prepareStatement("CALL OBTER_EVENTOS_PUBLICOS(?)");

            statement.setString(1, dia.toString());

            ResultSet set = statement.executeQuery();

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

    public static ArrayList<Evento> getEventosGrupales(LocalDate dia, Usuario user ) {

        ArrayList<Evento> eventos = new ArrayList<>();

        try {

            PreparedStatement statement = conexionBase.prepareStatement("CALL OBTER_EVENTOS_GRUPAIS(?, ?)");

            statement.setInt(1, user.getId());
            statement.setString(2, dia.toString());

            ResultSet set = statement.executeQuery();

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

    /**
     * Crea un evento grupal cos parámetros dados. Non rexistra ningún usuario extra pero sí que rexistra 
     * ao usuario creador.
     * @param nome nome do evento.
     * @param creador o usuario creador do evento.
     * @param data a data do evento.
     * @param hora a hora do evento.
     * @param usuarios un <code>ArrayList</code> con id de usuarios que participan no evento
     * @return o evento xa creado.
     */
    public static EventoGrupal crearEventoGrupal(String nome, Usuario creador, LocalDate data, LocalTime hora, ArrayList<Integer> usuarios ) {

        EventoGrupal out;
        int id = -1;

        try (CallableStatement cs = conexionBase.prepareCall("CALL CREAR_EVENTO_GRUPAL(?,?,?,?)") ) {   // Esta operación será realizada nunha transacción
            
            ResultSet rs;
            CallableStatement rexistro;

            conexionBase.setAutoCommit(false);
            rexistro = conexionBase.prepareCall("CALL REXISTRAR_EN_EVENTO_GRUPAL(?,?)");

            cs.setString(1, nome);
            cs.setInt(2, creador.getId());
            cs.setDate(3, Date.valueOf(data));
            cs.setTime(4, Time.valueOf(hora));

            rs = cs.executeQuery();
            
            if(rs.next() ) {

                id = rs.getInt(1);

            }

            // Comezo da transacción
            

            rexistro.setInt(1, id);

            for(int idUser : usuarios ) {

                rexistro.setInt(2, idUser);

                rexistro.executeUpdate();

            }

            conexionBase.commit();

            out = new EventoGrupal(id, nome, creador.getId(), data, hora);

            conexionBase.setAutoCommit(true);

        } catch (SQLException e) {
            
            out = null;

            try {

                conexionBase.rollback();
                conexionBase.setAutoCommit(true);

            } catch (SQLException e1) {

            }

            System.out.println("Erro ao intentar crear un evento grupal");
            e.printStackTrace();

        }

        return out;
    }

    public static EventoPrivado crearEventoPrivado(String nome, Usuario creador, LocalDate data, LocalTime hora ) {

        EventoPrivado out;
        int id = -1;

        try {

            CallableStatement cs = conexionBase.prepareCall("CALL CREAR_EVENTO_PRIVADO(?,?,?,?)");
            ResultSet rs;

            cs.setString(1, nome);
            cs.setInt(2, creador.getId());
            cs.setDate(3, Date.valueOf(data));
            cs.setTime(4, Time.valueOf(hora));

            rs = cs.executeQuery();
            
            if(rs.next() ) {

                id = rs.getInt(1);

            }
            
            out = new EventoPrivado(id, nome, creador.getId(), data, hora);
            
        } catch (SQLException e) {
            
            out = null;
            System.out.println("Erro ao intentar crear un evento ");

        }

        return out;
    }

    public static EventoPublico crearEventoPublicoPrivado(String nome, Usuario creador, LocalDate data, LocalTime hora ) {

        EventoPublico out;
        int id = -1;

        try {

            CallableStatement cs = conexionBase.prepareCall("CALL CREAR_EVENTO_PUBLICO(?,?,?,?)");
            ResultSet rs;

            cs.setString(1, nome);
            cs.setInt(2, creador.getId());
            cs.setDate(3, Date.valueOf(data));
            cs.setTime(4, Time.valueOf(hora));

            rs = cs.executeQuery();
            
            if(rs.next() ) {

                id = rs.getInt(1);

            }
            
            out = new EventoPublico(id, nome, creador.getId(), data, hora);
            
        } catch (SQLException e) {
            
            out = null;
            System.out.println("Erro ao intentar crear un evento publico");
            e.printStackTrace();

        }

        return out;
    }

    /**
     * Borra un evento da base de datos
     * @param ev o evento que se quere borrar
     */
    public static void borrarEvento(Evento ev ) {

        try {

            CallableStatement cs = conexionBase.prepareCall("CALL BORRAR_EVENTO(?)");

            cs.setInt(1, ev.getId());
            cs.execute();
            
        } catch (SQLException e) {

            System.out.println("Erro ao intentar borrar un evento");

        }

    }

}