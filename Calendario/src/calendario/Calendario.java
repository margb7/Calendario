package calendario;

import utilidades.Funciones;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;

import excepcions.CredenciaisIncorrectasException;
import excepcions.EventoXaExisteException;
import excepcions.UsuarioNonAtopadoException;
import excepcions.UsuarioXaRexistradoException;

import model.Datos;
import model.Evento;
import model.Usuario;

import ui.CalendarioUI;
import ui.CreacionEventoPrivadoUI;
import ui.CreacionEventoPublicoUI;
import ui.CreacionEventoGrupalUI;
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
    private static boolean conectado;

    // Interfaces de usuario
    private static CalendarioUI interfaceCalendario;
    private static LoginUI interfaceLogin;
    private static SeleccionDataUI interfaceSeleccionData;
    private static ErrorUI interfaceErro;
    private static CreacionEventoPrivadoUI interfaceCreacionEventoPrivado;
    private static CreacionEventoPublicoUI interfaceCreacionEventoPublico;
    private static CreacionEventoGrupalUI interfaCreacionEventoGrupalUI;

    /**
     * Método main do programa.
     * @param args argumentos para main.
     */
    public static void main(String[] args) {
        
        // Iniciar a base de datos
        conectado = Datos.iniciarConexionBBDD();

        // Cargar os idiomas e por por defecto o galego
        idiomasDisponibles = Datos.cargarIdiomas();
        setIdomaSeleccionado("Galego");

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
        interfaceCreacionEventoPrivado = new CreacionEventoPrivadoUI();
        interfaceCreacionEventoPublico = new CreacionEventoPublicoUI();
        interfaCreacionEventoGrupalUI = new CreacionEventoGrupalUI();

        // Mostrar interfaz
        if(conectado ) {  // Programa en modo normal

            interfaceLogin.mostrarUI();

        } else {    // Programa en modo limitado (mostra o calendario pero nada mais)
            
            mostrarErro(getTraduccion("E08", "Non se puido conectar coa base da datos"));
            setUsuario(new Usuario(-1, "Default", ""));     // usuario para cando non hai conexion

            mostrarCalendario();

        }

    }

    /**
     * Devolve os idiomas dispoñibles cargados no programa
     * @return un HashMap que conténn <nome_idioma, <código_traducción, texto_traducción>>
     */
    public static HashMap<String, HashMap<String, String>> getIdiomasDisponibles() {
        return idiomasDisponibles;
    }

    /**
     * Obtén a data do calendario
     * @return a data do calendario
     */
    public static LocalDate getDataCalendario() {
        return dataCalendario;
    }

    /**
     * Obtén o primer día do mes
     * @return o primer día do mes
     */
    public static LocalDate getPrimerDiaMes() {
        return primerDiaMes;
    }

    /**
     * Devolve o obxecto do usuario que está a manexar o calendario
     * @return o usuario do calendario
     */
    public static Usuario getUsuario() {
        return usuario;
    }

    /**
     * Método para saber se o programa está conectado ou non a base de datos
     * @return
     */
    public static boolean isConectado() {
        return conectado;
    }

    /**
     * Setter para colocar o primer día do mes
     * @param primerDiaMes o novo primer día do mes
     */
    public static void setPrimerDiaMes(LocalDate primerDiaMes) {
        Calendario.primerDiaMes = primerDiaMes;
    }

    /**
     * Setter para colocar o usuario que manexa o calendario
     * @param usuario o usuario
     */
    public static void setUsuario(Usuario usuario) {
        Calendario.usuario = usuario;
    }

    /**
     * Setter para colocar a data do calendario
     * @param dataCalendario a nova data do calendario
     */
    public static void setDataCalendario(LocalDate dataCalendario) {
        Calendario.dataCalendario = dataCalendario;
        Calendario.primerDiaMes = dataCalendario;

        interfaceCalendario.actualizarCalendario();
    }

    /**
     * Selecciona un idioma de entre os idiomas dispoñibles. O nome de cada idioma
     * se corresponde co nome do ficheiro na carpeta de idiomas sen a extensión .txt. Se se intenta seleccionar
     * un idioma que non está dispoñible non fará ningún cambio.
     * @param idomaSeleccionado o idioma 
     */
    public static void setIdomaSeleccionado(String idioma) {
        
        if(idiomasDisponibles.containsKey(idioma) ) {

            Calendario.idiomaSeleccionado = idiomasDisponibles.get(idioma);

        }

    }

    /**
     * Avanza un mes no calendario
     */
    public static void avanzarMes() {
        setDataCalendario(primerDiaMes.plusMonths(1));
    }

    /**
     * Retrocede un mes no calendario
     */
    public static void retrocederMes() {
        setDataCalendario(primerDiaMes.minusMonths(1));
    }

    /**
     * Inicia sesión como un usuario. Antes de devolver o obxecto do usuario encárgase de comprobar que o 
     * usuario esté rexistrado e tamén que a contrasinal sexa a correcta.
     * @param nome o nome de login do usuario.
     * @param passwd a contrasinal do usuario.
     * @return un obxecto <code>Usuario</code> que está rexistrado.
     * @throws CredenciaisIncorrectasException se a contrasinal non é correcta.
     * @throws UsuarioNonAtopadoException se o usuario non está rexistrado.
     */
    public static Usuario logIn(String nome, String passwd ) throws CredenciaisIncorrectasException, UsuarioNonAtopadoException {

        Usuario user = Datos.getUsuarioPorNome(nome);   // Xa lanza automáticamente a excepción de 
                                                        // usuario non atopado

        if(!user.getContrasinal().equals(passwd) ) {     // Inicio de sesión correcto

            throw new CredenciaisIncorrectasException(CredenciaisIncorrectasException.Tipo.CONTRASINAL_NON_COINCIDE, "");

        }

        return user;
    }

    /**
     * Rexistra un novo usuario. Antes de rexistralo realiza as comprobacións para que o nome sexa válido, que a 
     * contrasinal sexa válida e que coincida coa confirmación.
     * @param nome o nome de login do usuario
     * @param contrasinal a contrasinal do usuario
     * @param confirmacion a confirmación da contrasinal
     * @return un obxecto de usuario rexistrado
     * @throws UsuarioXaRexistradoException se o usuario xa está rexistrado
     * @throws CredenciaisIncorrectasException nas seguintes situacións:
     * <ul>
     *   <li>No caso de que o nome non sexa válido (neste caso o tipo da excepción será <code>Tipo.NOME_NON_VALIDO</code>)</li>
     *   <li>No caso de que o contrasinal non sexa válido (neste caso o tipo da excepción será <code>Tipo.CONTRASINAL_NON_VALIDA</code>)</li>
     *   <li>No caso de que a contrasinal e a confirmación non coincidan (neste caso o tipo da excepción será <code>Tipo.CONTRASINAL_NON_VALIDA</code>)</li>
     * </ul>
     */
    public static Usuario rexistrarConta(String nome, String contrasinal, String confirmacion ) throws UsuarioXaRexistradoException, CredenciaisIncorrectasException{

        Usuario user;

        if(Datos.usuarioEstaRexistrado(nome) ) {

            throw new UsuarioXaRexistradoException("Para o nome :" + nome);

        }

        if(!Funciones.nomeUsuarioValido(nome) ) {

            throw new CredenciaisIncorrectasException(CredenciaisIncorrectasException.Tipo.NOME_NON_VALIDO, "Nome de usuario non válido");

        }

        if(!Funciones.contrasinalValida(contrasinal) ) {
            
            throw new CredenciaisIncorrectasException(CredenciaisIncorrectasException.Tipo.CONTRASINAL_NON_VALIDA, "A contrasinal non é válida");

        }

        if(!contrasinal.equals(confirmacion) ) {

            throw new CredenciaisIncorrectasException(CredenciaisIncorrectasException.Tipo.CONTRASINAL_NON_COINCIDE ,"A contrasinal e a confirmación non son correctas");

        } 

        user = Datos.rexistrarUsuario(nome, contrasinal);

        return user;
    }


    /**
     * Lanza un diálogo mostrando información dun erro. Tamén bloquea a ventá que chama 
     * ao erro ata que se pecha o erro.
     * @param owner a ventá que lanza o erro e que quedará bloqueada.
     * @param str a mensaxe de erro.
     */
    public static void mostrarErro(JFrame owner, String str ) {

        interfaceErro.getLabel().setText(str);
        interfaceErro.mostrarUI(owner);

    }

    /**
     * Lanza un diálogo mostrando información dun erro.
     * @param str a mensaxe de erro.
     */
    public static void mostrarErro(String str ) {

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

    /**
     * Obtén todos os eventos para un día determinado e para o usuario que está a usar o calendario.
     * @param dataDia a data da cal se queren buscar os eventos.
     * @return un array con todos os eventos (grupais, públicos e privados).
     */
    public static Evento[] obterEventos(LocalDate dataDia) {
        return Datos.getEventosDia(dataDia, usuario);
    }

    /**
     * Crea un evento privado e o rexistra na base de datos. O creador será o usuario do calendario
     * @param nome o nome do evento
     * @param data a data do evento
     * @param hora a hora do evento
     * @throws EventoXaExisteException se o evento xa existe
     * @throws CredenciaisIncorrectasException se algún dato non é correcto
     */
    public static void crearEventoPrivado(String nome, LocalDate data, LocalTime hora ) throws EventoXaExisteException, CredenciaisIncorrectasException {

        if(Datos.existeEventoEnDia(nome, data, usuario) ) {

            throw new EventoXaExisteException("Para nom: " + nome + " - data: " + data + " hora: " + hora);

        }

        if(nome.isEmpty() ) {

            throw new CredenciaisIncorrectasException(CredenciaisIncorrectasException.Tipo.NOME_NON_VALIDO, "O nome non pode ser unha cadea vacía");

        }

        Datos.crearEventoPrivado(nome, usuario, data, hora);

    }

    /**
     * Crea un evento público e o rexistra na base de datos. O creador será o usuario do calendario
     * @param nome o nome do evento
     * @param data a data do evento
     * @param hora a hora do evento
     * @throws EventoXaExisteException se o evento xa existe
     * @throws CredenciaisIncorrectasException se algún dato non é correcto
     */
    public static void crearEventoPublico(String nome, LocalDate data, LocalTime hora ) throws EventoXaExisteException, CredenciaisIncorrectasException {

        if(Datos.existeEventoEnDia(nome, data, usuario) ) {

            throw new EventoXaExisteException("Para nom: " + nome + " - data: " + data + " hora: " + hora);

        }

        if(nome.isEmpty() ) {

            throw new CredenciaisIncorrectasException(CredenciaisIncorrectasException.Tipo.NOME_NON_VALIDO, "O nome non pode ser unha cadea vacía");

        }

        Datos.crearEventoPublicoPrivado(nome, usuario, data, hora);

    }

    /**
     * Crea un evento grupal e o rexistra na base de datos. O creador será o usuario do calendario.
     * @param nome o nome do evento
     * @param data a data do evento
     * @param hora a hora do evento
     * @param users un arraylist con id de usuarios que participan no evento
     * @throws EventoXaExisteException se o evento xa existe
     * @throws CredenciaisIncorrectasException se algún dato non é correcto
     */
    public static void crearEventoGrupal(String nome, LocalDate data, LocalTime hora, ArrayList<Integer> users ) throws EventoXaExisteException, CredenciaisIncorrectasException{

        if(Datos.existeEventoEnDia(nome, data, usuario) ) {

            throw new EventoXaExisteException("Para nom: " + nome + " - data: " + data + " hora: " + hora);

        }

        if(nome.isEmpty() ) {

            throw new CredenciaisIncorrectasException(CredenciaisIncorrectasException.Tipo.NOME_NON_VALIDO, "O nome non pode ser unha cadea vacía");

        }

        Datos.crearEventoGrupal(nome, usuario, data, hora, users);
    }

    /**
     * Borra o evento
     * @param ev o evento a borrar
     */
    public static void borrarEvento(Evento ev) {

        if(ev.getCreador() == usuario.getId() ) {

            mostrarErro(getTraduccion("E15", "Non eres o creador do evento"));
        
        } else {

            Datos.borrarEvento(ev);

        }

    }

    /**
     * Método para pedir unha data ao usuario mediante a interface de selección de data
     * @param owner o frame que quedará bloqueado esperando pola data
     */
    public static void pedirData(JFrame owner ) {

        interfaceSeleccionData.mostrarUI(owner);

    }

    /**
     * Método para pedir unha datos dun evento privado ao usuario mediante a interface para pedir eventos privados
     * @param owner o frame que quedará bloqueado esperando pola data
     * @param data a data do evento
     */
    public static void pedirDatosEventoPrivado(JFrame owner, LocalDate data) {

        interfaceCreacionEventoPrivado.setData(data);
        interfaceCreacionEventoPrivado.mostrarUI(owner);

    }

    /**
     * Método para pedir unha datos dun evento público ao usuario mediante a interface para pedir eventos público
     * @param owner o frame que quedará bloqueado esperando pola data
     * @param data a data do evento
     */
    public static void pedirDatosEventoPublico(JFrame owner, LocalDate data) {

        interfaceCreacionEventoPublico.setData(data);
        interfaceCreacionEventoPublico.mostrarUI(owner);

    }

    /**
     * Método para pedir unha datos dun evento grupal ao usuario mediante a interface para pedir eventos grupais
     * @param owner o frame que quedará bloqueado esperando pola data
     * @param data a data do evento
     */
    public static void pedirDatosEventoGrupal(JFrame owner, LocalDate data) {

        interfaCreacionEventoGrupalUI.setData(data);
        interfaCreacionEventoGrupalUI.mostrarUI(owner);

    }

    /**
     * Método para mostrar o calendario
     */
    public static void mostrarCalendario() {

        interfaceCalendario.mostrarUI();

    }
    
}