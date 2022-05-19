package calendario;

import utilidades.Dia;
import utilidades.Funciones;
import utilidades.Mes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.time.LocalDate;
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
import ui.CreacionEventoPrivadoUI;
import ui.ElementoUI;
import ui.ErrorUI;
import ui.LoginUI;
import ui.ModoColorUI;
import ui.SeleccionDataUI;

/**
 *  TODO: funciona como controlador
 */
public class Calendario {

    private static LocalDate dataCalendario;
    private static LocalDate primerDiaMes;
    private static Usuario usuario;

    private static CalendarioUI interfaceCalendario;
    private static LoginUI interfaceLogin;
    private static SeleccionDataUI interfaceSeleccionData;
    private static ErrorUI interfaceErro;
    private static CreacionEventoPrivadoUI interfaceCreacionEventoPrivado;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Datos.cargarIdiomas();

        Datos.setIdomaSeleccionado("Castellano");

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

        initSeleccionData();
        initLogin();
        
        // Mostrar o login
        interfaceLogin.mostrarUI();
        
        Toolkit tool = Toolkit.getDefaultToolkit();
        
        interfaceLogin.getFrame().setLocation((tool.getScreenSize().width - interfaceLogin.getFrame().getWidth()) / 2, (tool.getScreenSize().height - interfaceLogin.getFrame().getHeight() ) / 2 );
        
    }

    private static void initSeleccionData() {

        String[] lista = Mes.getListaMeses();

        interfaceSeleccionData.getSeleccionData().setTitle(Datos.getTraduccion("S01", "Selecciona unha data"));
        interfaceSeleccionData.getDe().setText(Datos.getTraduccion("S02", "de"));
        interfaceSeleccionData.getOk().setText(Datos.getTraduccion("S03", "Ok"));

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

        // NO afecta -> interfaceCalendario.getPanelCalendario().getComponentPopupMenu().setName("Crear");
        interfaceCalendario.getFrame().setTitle(Datos.getTraduccion("C01", "Calendario"));
        interfaceCalendario.getItemPublico().setText(Datos.getTraduccion("C03", "Público"));
        interfaceCalendario.getItemPrivado().setText(Datos.getTraduccion("C05", "Privado"));
        interfaceCalendario.getItemGrupal().setText(Datos.getTraduccion("C04", "Grupal"));

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
                
                pedirDatosEventoPrivado(interfaceCalendario.getFrame());
                
            }

        });

        interfaceCalendario.getItemPublico().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                System.out.println(e.getActionCommand());
                
            }

        });
                                                                            
        interfaceCalendario.getCambioModoCor().setText(Datos.getTraduccion("C02", "Cambiar tema de cor"));

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

        interfaceLogin.getFrame().setTitle(Datos.getTraduccion("L01", "Inicio Sesión"));

        initLoginCard();
        initRexistroCard();

    }

    /**
     * Prepara a interfaz do login antes de mostrala.
     */
    private static void initLoginCard() {

        interfaceLogin.getLogInCard().setName(Datos.getTraduccion("L02", "Log in"));
        interfaceLogin.getUnameLogInLabel().setText(Datos.getTraduccion("L04", "Usuario"));
        interfaceLogin.getPswdLoginLabel().setText(Datos.getTraduccion("L05", "Contrasinal"));
        interfaceLogin.getSubmitLogIn().setText(Datos.getTraduccion("L06", "Log in"));
        interfaceLogin.getSignUpButton().setText(Datos.getTraduccion("L07", "Crea unha nova conta"));
        interfaceLogin.getCambioModoCorLogIn().setText(ElementoUI.getModoColor() == ModoColorUI.MODO_CLARO ? Datos.getTraduccion("L08", "Modo escuro") : Datos.getTraduccion("L09", "Modo claro"));

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

                        mostrarErro(interfaceLogin.getFrame(), Datos.getTraduccion("E01", "Credenciais incorrectas"));
                        interfaceLogin.getPasswordLogin().setText("");
                        interfaceLogin.getUsernameLogIn().setText("");

                    }

                } catch(UsuarioNonAtopadoException ex ) {        // Non existe o usuario 

                    mostrarErro(interfaceLogin.getFrame(), Datos.getTraduccion("E02", "O usuario non está rexistrado"));
                    interfaceLogin.getPasswordLogin().setText("");
                    interfaceLogin.getUsernameLogIn().setText("");

                }

            }
            
        });

        interfaceLogin.getCambioModoCorLogIn().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                ElementoUI.setModoColor(ElementoUI.getModoColor() == ModoColorUI.MODO_CLARO ? ModoColorUI.MODO_OSCURO : ModoColorUI.MODO_CLARO);

                interfaceLogin.repintarComponentes();
                
            }
            
        });

        interfaceLogin.getCambioModoCorSignUp().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                LoginUI.setModoColor(LoginUI.getModoColor() == ModoColorUI.MODO_CLARO ? ModoColorUI.MODO_OSCURO : ModoColorUI.MODO_CLARO);
                
                //LoginUI.repintarComponentes();
                
            }
            
        });

    }

    private static void initRexistroCard() {

        interfaceLogin.getSignUpCard().setName(Datos.getTraduccion("L03", "Sign up"));
        interfaceLogin.getUnameSignUpLabel().setText(Datos.getTraduccion("L04", "Usuario"));
        interfaceLogin.getPswdSignUpLabel().setText(Datos.getTraduccion("L05", "Contrasinal"));
        interfaceLogin.getConfirmPswdLabel().setText(Datos.getTraduccion("L10", "Confirma o contrasinal"));
        interfaceLogin.getSubmitSignUp().setText(Datos.getTraduccion("L03", "Sign up"));
        interfaceLogin.getLogInButton().setText(Datos.getTraduccion("L11", "Xa estás rexistrado?"));
        interfaceLogin.getCambioModoCorSignUp().setText(ElementoUI.getModoColor() == ModoColorUI.MODO_CLARO ? Datos.getTraduccion("L08", "Modo escuro") : Datos.getTraduccion("L09", "Modo claro"));


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
    
                                mostrarErro(interfaceLogin.getFrame(), Datos.getTraduccion("E03", "A contrasinal non é válida"));  // TODO : explicar que requerimentos fan falta
                                interfaceLogin.getConfirmPassword().setText("");
                                interfaceLogin.getPasswordSignUp().setText("");
                                
                            }

                        } else {

                            mostrarErro(interfaceLogin.getFrame(), Datos.getTraduccion("E04", "Nome de usuario non válido"));
                            interfaceLogin.getConfirmPassword().setText("");
                            interfaceLogin.getPasswordSignUp().setText("");
                            interfaceLogin.getUsernameSignUp().setText("");

                        }

                    } else {    // Rexistro incorrecto (a contrasinal e a confirmación non concordan)

                        mostrarErro(interfaceLogin.getFrame(), Datos.getTraduccion("E05", "As contrasinais non coinciden"));
                        interfaceLogin.getConfirmPassword().setText("");
                        interfaceLogin.getPasswordSignUp().setText("");

                    }

                } else { // Usuario previamente rexisrado

                    mostrarErro(interfaceLogin.getFrame(), Datos.getTraduccion("E06", "Usuario xa rexistrado"));
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

        interfaceErro.getDialog().setTitle(Datos.getTraduccion("E07", "Erro"));
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

    public static void pedirDatosEventoPrivado(JFrame owner) {

        interfaceCreacionEventoPrivado.getDialogoCreacion().addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosed(WindowEvent e) {

                owner.setEnabled(true);
                
            }

            @Override
            public void windowOpened(WindowEvent e) {

                owner.setEnabled(false);

            }

        });

        interfaceCreacionEventoPrivado.getCancelarButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                interfaceCreacionEventoPrivado.getDialogoCreacion().dispose();
                
            }
            
        });

        interfaceCreacionEventoPrivado.getCrearEventoButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                //TODO crear evento

                interfaceCreacionEventoPrivado.getDialogoCreacion().dispose();
                
            }

        });

        interfaceCreacionEventoPrivado.mostrarUI(owner);

    }
    
}