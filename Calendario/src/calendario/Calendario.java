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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

    private static LocalDate dataCalendario;
    private static LocalDate primerDiaMes;
    private static Usuario usuario;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Datos.cargarIdiomas();
        // Tema de color para todos os elementos
        ElementoUI.setModoColor(ModoColorUI.MODO_CLARO);
        
        // Para activar antialiasing nas fontes cando non están activadas por defecto
        System.setProperty("awt.useSystemAAFontSettings","on");

        // Mostrar a interfaz
        initLogin();
        LoginUI.mostrarUI();
        
    }

    /**
     * Inicializa os atributos e os eventos da ventá do calendario antes de mostralo.
     */
    private static void initCalendario() {

        CalendarioUI.init();

        JButton[] celdasDias = CalendarioUI.getCeldasDias();

        dataCalendario = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);
        primerDiaMes = dataCalendario;

        // Botón (">") na dereita para avanzar o mes
        CalendarioUI.getAvanzarMes().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                dataCalendario = dataCalendario.withDayOfMonth(1);

                actualizarCalendario();

            }

        });


        // Botón ("<") na esquerda para retroceder o mes
        CalendarioUI.getRetrocederMes().addActionListener(new ActionListener() {

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

                    CalendarioUI.getTextoDia().setText(textoDia);
                    
                    // Vaciar a lista para que non conteña eventos que non corresponden 
                    CalendarioUI.getListaEventos().setListData(new Evento[0]);

                    if(listaEventos.length != 0 ) {

                        CalendarioUI.getListaEventos().setListData(listaEventos);

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

        actualizarCalendario();

    }

    /**
     * Actualiza os contidos do calendario para reflexar correctamente o mes. Precísase o seu
     * uso cada vez que se fagan cambios no mes do calendario. 
     */
    public static void actualizarCalendario() {

        JButton[] celdasDias = CalendarioUI.getCeldasDias();
        String stringMes = Mes.values()[dataCalendario.getMonthValue() - 1] + ", " + dataCalendario.getYear();
        
        CalendarioUI.getTextoMes().setText(stringMes);
        CalendarioUI.getTextoMes().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                SeleccionDataUI.initSeleccionData(CalendarioUI.getFrame(), primerDiaMes.getYear());
                SeleccionDataUI.getOk().addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        
                        dataCalendario = LocalDate.of(SeleccionDataUI.getValorAnos(), SeleccionDataUI.getValorMes(), 1);
                        
                        SeleccionDataUI.getSeleccionData().dispose();

                        actualizarCalendario();
                        
                    }
                    
                });

                SeleccionDataUI.getSeleccionData().setVisible(true);

            }

            

        });

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

        CalendarioUI.actualizarCalendario();

    }

    public static void initLogin() {

        initLoginCard();
        initRexistroCard();

    }

    /**
     * Prepara a interfaz do login antes de mostrala.
     */
    private static void initLoginCard() {

        LoginUI.init();

        // Evento para cambiar ao modo rexistro dende o modo inicio de sesión
        LoginUI.getLogInButton().addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                
                CardLayout cl = (CardLayout) LoginUI.getCards().getLayout();

                cl.next(LoginUI.getCards());

            }

        });

        // Evento ao clicar no botón para iniciar sesión
        LoginUI.getSubmitLogIn().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                Usuario user;                
                
                try {

                    String passwd;

                    user = Datos.getUsuarioPorNome(LoginUI.getUsernameLogIn().getText());

                    passwd = new String(LoginUI.getPasswordLogin().getPassword());

                    if(user.getContrasinal().equals(passwd) ) {     // Inicio de sesión correcto

                        LoginUI.getFrame().setVisible(false);
                        usuario = user;

                        // Mostrar calendario
                        initCalendario();
                        CalendarioUI.mostrarUI();

                    } else {        // Usuario existe -> pero non é a contrasinal correcta

                        mostrarErro(LoginUI.getFrame(), Datos.getTraduccion("E01", "Credenciais incorrectas"));
                        LoginUI.getPasswordLogin().setText("");
                        LoginUI.getUsernameLogIn().setText("");

                    }

                } catch(UsuarioNonAtopadoException ex ) {        // Non existe o usuario 

                    mostrarErro(LoginUI.getFrame(), Datos.getTraduccion("E02", "O usuario non está rexistrado"));
                    LoginUI.getPasswordLogin().setText("");
                    LoginUI.getUsernameLogIn().setText("");

                }

            }
            
        });

        LoginUI.getCambioModoCorLogIn().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                LoginUI.setModoColor(LoginUI.getModoColor() == ModoColorUI.MODO_CLARO ? ModoColorUI.MODO_OSCURO : ModoColorUI.MODO_CLARO);

                LoginUI.repintarComponentes();
                
            }
            
        });

        LoginUI.getCambioModoCorSignUp().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                LoginUI.setModoColor(LoginUI.getModoColor() == ModoColorUI.MODO_CLARO ? ModoColorUI.MODO_OSCURO : ModoColorUI.MODO_CLARO);
                
                LoginUI.repintarComponentes();
                
            }
            
        });

    }

    private static void initRexistroCard() {

        // Evento para cambiar ao modo inicio de sesión dende o modo de rexistro
        LoginUI.getSignUpButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                CardLayout cl = (CardLayout) LoginUI.getCards().getLayout();

                cl.next(LoginUI.getCards());

            }

        });

        // Evento ao clicar no botón para rexistrarse
        LoginUI.getSubmitSignUp().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if(!Datos.usuarioEstaRexistrado(LoginUI.getUsernameSignUp().getText()) ) {  // Usuario non rexistrado

                    String passwd = new String(LoginUI.getPasswordSignUp().getPassword());
                    String confirm = new String(LoginUI.getConfirmPassword().getPassword());

                    if(passwd.equals(confirm) ) {       // Rexistro correcto

                        if(Funciones.nomeUsuarioValido(LoginUI.getUsernameSignUp().getText()) ) {

                            if(Funciones.contrasinalValida(passwd) ) {

                                try {
    
                                    LoginUI.getFrame().setVisible(false);
                                    usuario = Datos.rexistrarUsuario(LoginUI.getUsernameSignUp().getText(), passwd);
    
                                    // Mostrar calendario
                                    initCalendario();
                                    CalendarioUI.mostrarUI();
        
                                } catch(UsuarioXaRexistradoException ex ) {
        
                                    // Erro inesperado coa base de datos
        
                                } 
        
                                LoginUI.getFrame().setVisible(false);
                                CalendarioUI.mostrarUI();
    
                            } else {
    
                                mostrarErro(LoginUI.getFrame(), Datos.getTraduccion("E03", "A contrasinal non é válida"));  // TODO : explicar que requerimentos fan falta
                                LoginUI.getConfirmPassword().setText("");
                                LoginUI.getPasswordSignUp().setText("");
                                
                            }

                        } else {

                            mostrarErro(LoginUI.getFrame(), Datos.getTraduccion("E04", "Nome de usuario non válido"));
                            LoginUI.getConfirmPassword().setText("");
                            LoginUI.getPasswordSignUp().setText("");
                            LoginUI.getUsernameSignUp().setText("");

                        }

                    } else {    // Rexistro incorrecto (a contrasinal e a confirmación non concordan)

                        mostrarErro(LoginUI.getFrame(), Datos.getTraduccion("E05", "As contrasinais non coinciden"));
                        LoginUI.getConfirmPassword().setText("");
                        LoginUI.getPasswordSignUp().setText("");

                    }

                } else { // Usuario previamente rexisrado

                    mostrarErro(LoginUI.getFrame(), Datos.getTraduccion("E06", "Usuario xa rexistrado"));
                    LoginUI.getConfirmPassword().setText("");
                    LoginUI.getPasswordSignUp().setText("");
                    LoginUI.getUsernameSignUp().setText("");

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

        ErrorUI.setDialog(new JDialog(owner, Datos.getTraduccion("E07", "Erro")));
        ErrorUI.getDialog().setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        ErrorUI.getLabel().setText(str);
        ErrorUI.getDialog().add(ErrorUI.getLabel());

        ErrorUI.getDialog().addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosed(WindowEvent e) {
                
                owner.setEnabled(true);
                
            }

            @Override
            public void windowOpened(WindowEvent e) {
                
                owner.setEnabled(false);

            }

        });

        ErrorUI.mostrarUI(owner);

    }
    
}