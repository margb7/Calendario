package calendario;

import utilidades.Funciones;

import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.time.LocalDate;
import java.util.HashMap;

import javax.swing.JDialog;
import javax.swing.JFrame;

import excepcions.CredenciaisIncorrectasException;
import excepcions.UsuarioNonAtopadoException;
import excepcions.UsuarioXaRexistradoException;

import model.Datos;
import model.Evento;
import model.Usuario;

import ui.CalendarioUI;
import ui.ElementoUI;
import ui.ErrorUI;
import ui.LoginUI;
import ui.ModoColorUI;
import ui.SeleccionDataUI;

/**
 * Clase que contén o main do programa. Tamén actúa como controlador do programa
 */
public class Calendario {

    private static HashMap<String, HashMap<String, String>> idiomasDisponibles;
    private static HashMap<String, String> idiomaSeleccionado;
    private static LocalDate dataCalendario;
    private static LocalDate primerDiaMes;
    private static Usuario usuario;

    // Interfaces de usuario
    private static CalendarioUI interfaceCalendario;
    private static LoginUI interfaceLogin;
    private static SeleccionDataUI interfaceSeleccionData;
    private static ErrorUI interfaceErro;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        
        idiomasDisponibles = Datos.cargarIdiomas();

        setIdomaSeleccionado("English");

        // Tema de color para todos os elementos
        ElementoUI.setModoColor(ModoColorUI.MODO_CLARO);

        // Data inicial do calendario
        dataCalendario = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);
        primerDiaMes = dataCalendario;

        // Para activar antialiasing nas fontes cando non están activadas por defecto
        System.setProperty("awt.useSystemAAFontSettings","on");

        // Instancias das diferentes xanelas da interface gráfica
        interfaceCalendario = new CalendarioUI();
        interfaceLogin = new LoginUI();
        interfaceSeleccionData = new SeleccionDataUI();
        interfaceErro = new ErrorUI();
            
        if(Datos.iniciarConexionBBDD() ) {

            // Programa en modo normal
            interfaceLogin.mostrarUI();

        } else {
            
            // Programa en modo limitado (mostra o calendario pero nada mais)
            mostrarErro(getTraduccion("E08", "Non se puido conectar coa base da datos"));
            setUsuario(new Usuario(-1, "Default", ""));     // So serve para cando non hai conexion

            mostrarCalendario();

        }

        
    }

    public static HashMap<String, HashMap<String, String>> getIdiomasDisponibles() {
        return idiomasDisponibles;
    }

    public static LocalDate getDataCalendario() {
        return dataCalendario;
    }

    public static LocalDate getPrimerDiaMes() {
        return primerDiaMes;
    }

    public static Usuario getUsuario() {
        return usuario;
    }

    public static void setPrimerDiaMes(LocalDate primerDiaMes) {
        Calendario.primerDiaMes = primerDiaMes;
    }

    public static void setUsuario(Usuario usuario) {
        Calendario.usuario = usuario;
    }

    public static void setDataCalendario(LocalDate dataCalendario) {
        Calendario.dataCalendario = dataCalendario;
        Calendario.primerDiaMes = dataCalendario;

        interfaceCalendario.actualizarCalendario();
    }

    /**
     * @param idomaSeleccionado the idomaSeleccionado to set
     */
    public static void setIdomaSeleccionado(String idioma) {
        
        if(idiomasDisponibles.containsKey(idioma) ) {

            Calendario.idiomaSeleccionado = idiomasDisponibles.get(idioma);

        }

    }

    public static void avanzarMes() {

        setDataCalendario(primerDiaMes.plusMonths(1));

    }

    public static void retrocederMes() {

        setDataCalendario(primerDiaMes.minusMonths(1));

    }

    public static Usuario logIn(String nome, String passwd ) throws CredenciaisIncorrectasException, UsuarioNonAtopadoException {

        Usuario user = Datos.getUsuarioPorNome(nome);

        if(!user.getContrasinal().equals(passwd) ) {     // Inicio de sesión correcto

            throw new CredenciaisIncorrectasException(CredenciaisIncorrectasException.Tipo.CONTRASINAL_NON_COINCIDE, "");

        }

        return user;
    }

    public static Usuario rexistrarConta(String nome, String contrasinal, String confirmacion ) throws UsuarioXaRexistradoException, CredenciaisIncorrectasException{

        Usuario user;

        if(!Datos.usuarioEstaRexistrado(nome) ) {

            if(Funciones.nomeUsuarioValido(nome) ) {

                if(Funciones.contrasinalValida(contrasinal) ) {

                    if(contrasinal.equals(confirmacion) ) {

                        user = Datos.rexistrarUsuario(nome, contrasinal);
        
                    } else {
        
                        throw new CredenciaisIncorrectasException(CredenciaisIncorrectasException.Tipo.CONTRASINAL_NON_COINCIDE ,"A contrasinal e a confirmación non son correctas");
        
                    }

                } else {

                    throw new CredenciaisIncorrectasException(CredenciaisIncorrectasException.Tipo.CONTRASINAL_NON_VALIDA, "A contrasinal non é válida");

                }

            } else {

                throw new CredenciaisIncorrectasException(CredenciaisIncorrectasException.Tipo.NOME_NON_VALIDO, "Nome de usuario non válido");

            }

        } else {

            throw new UsuarioXaRexistradoException("Para o nome :" + nome);

        }

        return user;

    }


    /**
     * Lanza un diálogo mostrando información dun erro. Tamén bloquea a ventá que chama 
     * ao erro ata que se pecha o erro.
     * @param owner a ventá que lanza o erro e que quedará bloqueada.
     * @param str a mensaxe de erro.
     */
    public static void mostrarErro(JFrame owner, String str ) {

        interfaceErro.getDialog().setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        interfaceErro.getLabel().setText(str);

        // Evento para crear un diálgo de forma que ata que non se peche non 
        // se poda interactuar co "owner"
        interfaceErro.getDialog().addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosed(WindowEvent e) {
                
                owner.setEnabled(true);
                
            }

            @Override
            public void windowOpened(WindowEvent e) {
                
                owner.setEnabled(false);

            }

        });

        interfaceErro.mostrarUI(owner);

    }

    public static void mostrarErro(String str ) {

        interfaceErro.getDialog().setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        interfaceErro.getLabel().setText(str);

        interfaceErro.mostrarUI();

    }

    /**
     * Devolve o valor no idioma seleccionado para un valor de texto do programa.
     * @param codigo o código da traducción
     * @param valorPorDefecto o valor no caso de non atopar a traducción.
     * @return a cadea co valor de texto que corresponde.
     */
    public static String getTraduccion(String codigo, String valorPorDefecto ) {

        String out;

        if(idiomaSeleccionado != null && idiomaSeleccionado.containsKey(codigo) ) {

            out = idiomaSeleccionado.get(codigo);

        } else {

            out = valorPorDefecto;

        }

        return out;
    }

    public static Evento[] obterEventos(LocalDate dataDia) {

        return Datos.getEventosDia(dataDia, usuario);

    }

    public static void pedirData(JFrame owner ) {

        interfaceSeleccionData.getSeleccionData().addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosed(WindowEvent e) {
                
                owner.setEnabled(true);
                
            }

            @Override
            public void windowOpened(WindowEvent e) {
                
                owner.setEnabled(false);

            }

        });

        interfaceSeleccionData.mostrarUI(owner);

    }

    public static void mostrarCalendario() {

        interfaceCalendario.mostrarUI();

    }
    
}