package calendario;

import utilidades.Dia;
import utilidades.Funciones;
import utilidades.Mes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.time.LocalDate;
import java.util.HashMap;
import java.awt.CardLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SpinnerNumberModel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Toolkit;
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
 *  TODO: funciona como controlador
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

        Datos.init();
        idiomasDisponibles = Datos.cargarIdiomas();

        setIdomaSeleccionado("Castellano");

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

        initSeleccionData();

        Toolkit tool = Toolkit.getDefaultToolkit();
            
        // TODO: Temporal -> solo para dar un aviso 
        if(Datos.getConexionBase() == null ) {

            interfaceErro.getDialog().setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            interfaceErro.getLabel().setText("Non se puido conectar coa base da datos");
            interfaceErro.getDialog().add(interfaceErro.getLabel());
            interfaceErro.mostrarUI();

            interfaceErro.getDialog().setLocation((tool.getScreenSize().width - interfaceLogin.getFrame().getWidth()) / 2, (tool.getScreenSize().height - interfaceLogin.getFrame().getHeight() ) / 2 );

        } else {

            
            initLogin();
            
            // Mostrar o login
            interfaceLogin.mostrarUI();
                        
            interfaceLogin.getFrame().setLocation((tool.getScreenSize().width - interfaceLogin.getFrame().getWidth()) / 2, (tool.getScreenSize().height - interfaceLogin.getFrame().getHeight() ) / 2 );
        

        }

        
    }

    public static HashMap<String, HashMap<String, String>> getIdiomasDisponibles() {
        return idiomasDisponibles;
    }

    /**
     * @param idomaSeleccionado the idomaSeleccionado to set
     */
    public static void setIdomaSeleccionado(String idioma) {
        
        if(idiomasDisponibles.containsKey(idioma) ) {

            Calendario.idiomaSeleccionado = idiomasDisponibles.get(idioma);

        }

    }

    private static void initSeleccionData() {

        String[] lista = Mes.getListaMeses();

        interfaceSeleccionData.getAnos().setModel(new SpinnerNumberModel(dataCalendario.getYear(), 1980, dataCalendario.getYear() + 10, 1));

        for(int i = 0; i < lista.length; i++ ){

            interfaceSeleccionData.getMeses().addItem(lista[i]);

        }

        interfaceSeleccionData.getMeses().setSelectedIndex(dataCalendario.getMonthValue() - 1);

        interfaceSeleccionData.getOk().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                dataCalendario = LocalDate.of((int)interfaceSeleccionData.getAnos().getValue(), (int) interfaceSeleccionData.getMeses().getSelectedIndex() + 1, 1);
                
                interfaceSeleccionData.getSeleccionData().dispose();

                actualizarCalendario();
                
            }
                    
        });

    }

    /**
     * Inicializa os atributos e os eventos da ventá do calendario antes de mostralo.
     */
    private static void initCalendario() {

        JButton[] celdasDias = interfaceCalendario.getCeldasDias();

        // Botón (">") na dereita para avanzar o mes
        interfaceCalendario.getAvanzarMes().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                dataCalendario = dataCalendario.withDayOfMonth(1);

                actualizarCalendario();

            }

        });


        // Botón ("<") na esquerda para retroceder o mes
        interfaceCalendario.getRetrocederMes().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                dataCalendario = dataCalendario.withDayOfMonth(1).minusMonths(2);

                actualizarCalendario();
                
            }

        });

        for(int i = 0; i < celdasDias.length; i++ ) {

            celdasDias[i].addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent e) {

                    // Engadir para cada dia do mes a capacidade de mostrar os eventos no panel lateral
                    JButton boton = (JButton) e.getSource();

                    // Uso do nome do botón para obter o día que representa
                    LocalDate dataDia = LocalDate.parse(boton.getName());
                    Evento[] listaEventos = Datos.getEventosDia(dataDia, usuario);
                    String textoDia = Dia.values()[dataDia.getDayOfWeek().ordinal()].getNome() + " " + dataDia.getDayOfMonth() + " de " + Mes.values()[dataDia.getMonthValue() - 1].getNome();

                    interfaceCalendario.getTextoDia().setText(textoDia);
                    
                    // Vaciar a lista para que non conteña eventos que non corresponden 
                    interfaceCalendario.getListaEventos().setListData(new Evento[0]);

                    if(listaEventos.length != 0 ) {

                        interfaceCalendario.getListaEventos().setListData(listaEventos);

                    }

                    // Se o mes do día seleccionado non coincide co mes do calendario -> pásase a mostrar ese mes
                    if(dataDia.getMonthValue() != primerDiaMes.getMonthValue() ) {

                        dataCalendario = LocalDate.of(dataDia.getYear(), dataDia.getMonthValue(), 1);
                        actualizarCalendario();

                    }

                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    
                    JButton src = (JButton) e.getSource();
                    
                    src.setBorderPainted(true);
                    
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    
                    JButton src = (JButton) e.getSource();
                    
                    src.setBorderPainted(false);

                }
                
            });

        }

        interfaceCalendario.getTextoMes().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                pedirData(interfaceCalendario.getFrame());

            }

            

        });

        //TODO implementar as accións dos ítems do menú contextual
        interfaceCalendario.getItemGrupal().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                System.out.println(e.getActionCommand());
                
            }

        });

        interfaceCalendario.getItemPrivado().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                System.out.println(e.getActionCommand());
                
            }

        });

        interfaceCalendario.getItemPublico().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                System.out.println(e.getActionCommand());
                
            }

        });
                                                                            
        interfaceCalendario.getCambioModoCor().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                ElementoUI.setModoColor(ElementoUI.getModoColor() == ModoColorUI.MODO_CLARO ? ModoColorUI.MODO_OSCURO : ModoColorUI.MODO_CLARO);
                
                interfaceCalendario.repintarComponentes();
                
            }
            
        });

        actualizarCalendario();

    }

    /**
     * Actualiza os contidos do calendario para reflexar correctamente o mes. Precísase o seu
     * uso cada vez que se fagan cambios no mes do calendario. 
     */
    public static void actualizarCalendario() {

        JButton[] celdasDias = interfaceCalendario.getCeldasDias();
        String stringMes = Mes.values()[dataCalendario.getMonthValue() - 1] + ", " + dataCalendario.getYear();
        
        interfaceCalendario.getTextoMes().setText(stringMes);
        

        primerDiaMes = dataCalendario;

        // data -> comeza no primer día do mes
        int offset = dataCalendario.getDayOfWeek().ordinal();
        dataCalendario = dataCalendario.minusDays(offset);

        for(int i = 0; i < celdasDias.length; i++ ) {

            JButton celda = celdasDias[i];

            celda.setText(Integer.toString(dataCalendario.getDayOfMonth()) );
            celda.setName(dataCalendario.toString());   // Para identificar cada botón co seu día

            dataCalendario = dataCalendario.plusDays(1);

        }

        interfaceCalendario.actualizarCalendario();

    }

    public static void initLogin() {

        initLoginCard();
        initRexistroCard();

    }

    /**
     * Prepara a interfaz do login antes de mostrala.
     */
    private static void initLoginCard() {

        // Evento para cambiar ao modo rexistro dende o modo inicio de sesión
        interfaceLogin.getLogInButton().addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                
                CardLayout cl = (CardLayout) interfaceLogin.getCards().getLayout();

                cl.next(interfaceLogin.getCards());

            }

        });

        // Evento ao clicar no botón para iniciar sesión
        interfaceLogin.getSubmitLogIn().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                Usuario user;                
                
                try {

                    String passwd;

                    user = Datos.getUsuarioPorNome(interfaceLogin.getUsernameLogIn().getText());

                    passwd = new String(interfaceLogin.getPasswordLogin().getPassword());

                    if(user.getContrasinal().equals(passwd) ) {     // Inicio de sesión correcto

                        interfaceLogin.getFrame().setVisible(false);
                        usuario = user;

                        // Mostrar calendario
                        initCalendario();
                        interfaceCalendario.mostrarUI();

                        // Para que o tamaño do calendario e a posición se corresponda coa xanela que
                        // se acaba de pechar
                        interfaceCalendario.getFrame().setSize(interfaceLogin.getFrame().getSize());
                        interfaceCalendario.getFrame().setLocation(interfaceLogin.getFrame().getLocation());


                    } else {        // Usuario existe -> pero non é a contrasinal correcta

                        mostrarErro(interfaceLogin.getFrame(), getTraduccion("E01", "Credenciais incorrectas"));
                        interfaceLogin.getPasswordLogin().setText("");
                        interfaceLogin.getUsernameLogIn().setText("");

                    }

                } catch(UsuarioNonAtopadoException ex ) {        // Non existe o usuario 

                    mostrarErro(interfaceLogin.getFrame(), getTraduccion("E02", "O usuario non está rexistrado"));
                    interfaceLogin.getPasswordLogin().setText("");
                    interfaceLogin.getUsernameLogIn().setText("");

                }

            }
            
        });

        interfaceLogin.getCambioModoCorLogIn().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                ElementoUI.setModoColor(ElementoUI.getModoColor() == ModoColorUI.MODO_CLARO ? ModoColorUI.MODO_OSCURO : ModoColorUI.MODO_CLARO);

                interfaceLogin.getCambioModoCorLogIn().setText(ElementoUI.getModoColor() == ModoColorUI.MODO_CLARO ? getTraduccion("L08", "Modo escuro") : getTraduccion("L09", "Modo claro"));
                interfaceLogin.repintarComponentes();
                
            }
            
        });

        interfaceLogin.getCambioModoCorSignUp().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                LoginUI.setModoColor(LoginUI.getModoColor() == ModoColorUI.MODO_CLARO ? ModoColorUI.MODO_OSCURO : ModoColorUI.MODO_CLARO);
                
                interfaceLogin.getCambioModoCorLogIn().setText(ElementoUI.getModoColor() == ModoColorUI.MODO_CLARO ? getTraduccion("L08", "Modo escuro") : getTraduccion("L09", "Modo claro"));
                interfaceLogin.repintarComponentes();
                
            }
            
        });

    }

    private static void initRexistroCard() {

        // Evento para cambiar ao modo inicio de sesión dende o modo de rexistro
        interfaceLogin.getSignUpButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                CardLayout cl = (CardLayout) interfaceLogin.getCards().getLayout();

                cl.next(interfaceLogin.getCards());

            }

        });

        // Evento ao clicar no botón para rexistrarse
        interfaceLogin.getSubmitSignUp().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if(!Datos.usuarioEstaRexistrado(interfaceLogin.getUsernameSignUp().getText()) ) {  // Usuario non rexistrado

                    String passwd = new String(interfaceLogin.getPasswordSignUp().getPassword());
                    String confirm = new String(interfaceLogin.getConfirmPassword().getPassword());

                    if(passwd.equals(confirm) ) {       // Rexistro correcto

                        if(Funciones.nomeUsuarioValido(interfaceLogin.getUsernameSignUp().getText()) ) {

                            if(Funciones.contrasinalValida(passwd) ) {

                                try {
    
                                    interfaceLogin.getFrame().setVisible(false);
                                    usuario = Datos.rexistrarUsuario(interfaceLogin.getUsernameSignUp().getText(), passwd);
    
                                    // Mostrar calendario
                                    initCalendario();
                                    interfaceCalendario.mostrarUI();
        
                                    // Para que o tamaño do calendario e a posición se corresponda coa xanela que
                                    // se acaba de pechar
                                    interfaceCalendario.getFrame().setSize(interfaceLogin.getFrame().getSize());
                                    interfaceCalendario.getFrame().setLocation(interfaceLogin.getFrame().getLocation());

                                } catch(UsuarioXaRexistradoException ex ) {
        
                                    // Erro inesperado coa base de datos
        
                                }                                 
    
                            } else {
    
                                mostrarErro(interfaceLogin.getFrame(), getTraduccion("E03", "A contrasinal non é válida"));  // TODO : explicar que requerimentos fan falta
                                interfaceLogin.getConfirmPassword().setText("");
                                interfaceLogin.getPasswordSignUp().setText("");
                                
                            }

                        } else {

                            mostrarErro(interfaceLogin.getFrame(), getTraduccion("E04", "Nome de usuario non válido"));
                            interfaceLogin.getConfirmPassword().setText("");
                            interfaceLogin.getPasswordSignUp().setText("");
                            interfaceLogin.getUsernameSignUp().setText("");

                        }

                    } else {    // Rexistro incorrecto (a contrasinal e a confirmación non concordan)

                        mostrarErro(interfaceLogin.getFrame(), getTraduccion("E05", "As contrasinais non coinciden"));
                        interfaceLogin.getConfirmPassword().setText("");
                        interfaceLogin.getPasswordSignUp().setText("");

                    }

                } else { // Usuario previamente rexisrado

                    mostrarErro(interfaceLogin.getFrame(), getTraduccion("E06", "Usuario xa rexistrado"));
                    interfaceLogin.getConfirmPassword().setText("");
                    interfaceLogin.getPasswordSignUp().setText("");
                    interfaceLogin.getUsernameSignUp().setText("");

                }

            }

        });

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
        interfaceErro.getDialog().add(interfaceErro.getLabel());

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
    
}