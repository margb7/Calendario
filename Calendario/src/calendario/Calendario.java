package calendario;

import utilidades.Dia;
import utilidades.Mes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.awt.CardLayout;
import java.awt.event.KeyListener;

import javax.naming.NameNotFoundException;
import javax.swing.JButton;

import model.Datos;
import model.Usuario;
import ui.CalendarioUI;
import ui.ErrorUI;
import ui.LoginUI;

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
        
        // Iniciar componentes        
        initLogin();
        initCalendario();

        // 

        // Mostrar a interfaz de usuario
        LoginUI.mostrarUI();
        
    }

    /**
     * Inicializa os atributos e os eventos da ventá do calendario antes de mostralo.
     */
    private static void initCalendario() {

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

                        CalendarioUI.getListaEventos().setListData(Datos.getEventosDia(dataDia));
    
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

                        ErrorUI.mostrarErro(LoginUI.getFrame(), "Credenciais incorrectas");

                    }

                } catch(NameNotFoundException ex ) {        // Non existe o usuario 

                    ErrorUI.mostrarErro(LoginUI.getFrame(), "O usuario non está rexistrado");

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

                        try {

                            LoginUI.getFrame().setVisible(false);
                            usuario = Datos.rexistrarUsuario(LoginUI.getUsernameSignUp().getText(), passwd);
                            CalendarioUI.mostrarUI();

                        } catch(UnsupportedOperationException ex ) {

                            // Erro inesperado coa base de datos

                        } 

                        LoginUI.getFrame().setVisible(false);
                        CalendarioUI.mostrarUI();

                    } else {    // Rexistro incorrecto (a contrasinal e a confirmación non concordan)

                        ErrorUI.mostrarErro(LoginUI.getFrame(), "As contrasinais non coinciden");

                    }

                } else { // Usuario previamente rexisrado

                    ErrorUI.mostrarErro(LoginUI.getFrame(), "Usuario xa rexistrado");

                }

            }

        });

    }
    
}