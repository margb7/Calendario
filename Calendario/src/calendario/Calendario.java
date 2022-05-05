package calendario;

import utilidades.Dia;
import utilidades.Funciones;
import utilidades.Mes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.time.LocalDate;
import java.awt.CardLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;

import excepcions.UsuarioNonAtopadoException;
import excepcions.UsuarioXaRexistradoException;

import model.Datos;
import model.Evento;
import model.Usuario;

import ui.CalendarioUI;
import ui.ErrorUI;
import ui.LoginUI;
import ui.ModoColorUI;

/**
 *  TODO: funciona como controlador
 */
public class Calendario {

    private static LocalDate dataCalendario;
    private static LocalDate primerDiaMes;
    private static Usuario usuario;
    private static ModoColorUI temaColorGlobal;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        temaColorGlobal = ModoColorUI.MODO_CLARO;
        
        System.setProperty("awt.useSystemAAFontSettings","on"); // Para activar antialiasing nas fontes cando non están activadas por defecto

        // Iniciar componentes        
        initLogin();
        initCalendario();

        // Mostrar a interfaz de usuario
        LoginUI.mostrarUI();
        
    }

    /**
     * Inicializa os atributos e os eventos da ventá do calendario antes de mostralo.
     */
    private static void initCalendario() {

        CalendarioUI.setModoColor(temaColorGlobal);
        CalendarioUI.init();

        dataCalendario = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);

        // Botón (">") na dereita para avanzar o mes
        CalendarioUI.getAvanzarMes().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                dataCalendario = dataCalendario.withDayOfMonth(1);
                primerDiaMes = dataCalendario;

                actualizarCalendario();

            }

        });


        // Botón ("<") na esquerda para retroceder o mes
        CalendarioUI.getRetrocederMes().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                dataCalendario = dataCalendario.withDayOfMonth(1).minusMonths(2);
                primerDiaMes = dataCalendario;

                actualizarCalendario();
                
            }

        });

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

        // data -> comeza no primer día do mes
        int offset = dataCalendario.getDayOfWeek().ordinal();
        
        dataCalendario = dataCalendario.minusDays(offset);

        byte contador = 0;

        for(int i = 0; i < 6; i++ ) {

            for(int j = 0; j < 7; j++ ) {

                JButton celda = celdasDias[contador];

                celda.setText(Integer.toString(dataCalendario.getDayOfMonth() ) );
                celda.setName(dataCalendario.toString());   // Para identificar cada botón co seu día



                // Engadir para cada dia do mes a capacidade de mostrar os eventos no panel lateral
                celda.addActionListener(new ActionListener() {
                
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // TODO : añadir una pestaña con la fecha del día seleccionado y los eventos abajo

                        //String dataString = Dia.values()[dc.getData().getDayOfWeek().ordinal()] + 
                        //    " " + dc.getData().getDayOfMonth() + " de " + Mes.values()[dc.getData().getMonthValue() - 1];
                        JButton boton = (JButton) e.getSource();

                        // Uso do nome do botón para obter o día que representa
                        LocalDate dataDia = LocalDate.parse(boton.getName());

                        Evento[] listaEventos = Datos.getEventosDia(dataDia, usuario);

                        if(listaEventos.length != 0 ) {

                            CalendarioUI.getListaEventos().setListData(listaEventos);

                        } 
    
                    }
        
                });

                contador++;

                dataCalendario = dataCalendario.plusDays(1);

            }

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

        LoginUI.setModoColor(temaColorGlobal);
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
                        CalendarioUI.mostrarUI();

                    } else {        // Usuario existe -> pero non é a contrasinal correcta

                        mostrarErro(LoginUI.getFrame(), "Credenciais incorrectas");
                        LoginUI.getPasswordLogin().setText("");
                        LoginUI.getUsernameLogIn().setText("");

                    }

                } catch(UsuarioNonAtopadoException ex ) {        // Non existe o usuario 

                    mostrarErro(LoginUI.getFrame(), "O usuario non está rexistrado");
                    LoginUI.getPasswordLogin().setText("");
                    LoginUI.getUsernameLogIn().setText("");

                }

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

                        if(Funciones.contrasinalValida(passwd) ) {

                            try {

                                LoginUI.getFrame().setVisible(false);
                                usuario = Datos.rexistrarUsuario(LoginUI.getUsernameSignUp().getText(), passwd);
                                CalendarioUI.mostrarUI();
    
                            } catch(UsuarioXaRexistradoException ex ) {
    
                                // Erro inesperado coa base de datos
    
                            } 
    
                            LoginUI.getFrame().setVisible(false);
                            CalendarioUI.mostrarUI();

                        } else {

                            mostrarErro(LoginUI.getFrame(), "A contrasinal non é válida");  // TODO : explicar que requerimentos fan falta
                            LoginUI.getConfirmPassword().setText("");
                            LoginUI.getPasswordSignUp().setText("");
                            
                        }

                    } else {    // Rexistro incorrecto (a contrasinal e a confirmación non concordan)

                        mostrarErro(LoginUI.getFrame(), "As contrasinais non coinciden");
                        LoginUI.getConfirmPassword().setText("");
                        LoginUI.getPasswordSignUp().setText("");

                    }

                } else { // Usuario previamente rexisrado

                    mostrarErro(LoginUI.getFrame(), "Usuario xa rexistrado");
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

        ErrorUI.setDialog(new JDialog(owner, "Error"));
        ErrorUI.getDialog().setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        ErrorUI.getLabel().setText(str);
        ErrorUI.getDialog().add(ErrorUI.getLabel());



        // TODO : tiene que haber una mejor forma de hacer esto 
        ErrorUI.getDialog().addWindowListener(new WindowListener() {

            @Override
            public void windowClosed(WindowEvent e) {
                
                owner.setEnabled(true);
                
            }

            @Override
            public void windowOpened(WindowEvent e) {
                
                owner.setEnabled(false);

            }

            @Override
            public void windowClosing(WindowEvent e) {

            }


            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            
                
            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

        });

        ErrorUI.mostrarUI(owner);

    }
    
}