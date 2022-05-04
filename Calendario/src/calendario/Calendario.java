package calendario;

import utilidades.Dia;
import utilidades.Mes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.awt.CardLayout;

import javax.naming.NameNotFoundException;
import javax.swing.JButton;

import model.Datos;
import model.Usuario;
import ui.CalendarioUI;
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
        initRexistro();
        initCalendario();

        // Mostrar a interfaz de usuario
        LoginUI.mostrarUI();
        
    }

    /**
     * Inicializa os atributos e os eventos da ventá do calendario antes de mostralo.
     */
    private static void initCalendario() {

        dataCalendario = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);

        // Crear instancias iniciais da interfaz
        CalendarioUI.init();    

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

    /**
     * Prepara a interfaz do login antes de mostrala.
     */
    private static void initLogin() {

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

                    System.out.println(passwd);

                    if(user.getContrasinal().equals(passwd) ) {     // Inicio de sesión correcto

                        LoginUI.getFrame().setVisible(false);
                        usuario = user;
                        CalendarioUI.mostrarUI();

                    } else {        // Usuario existe -> pero non é a contrasinal correcta



                    }

                } catch(NameNotFoundException ex ) {        // Non existe o usuario -> TODO: mensaxe para suxerir rexistro

                    

                }

            }
            
        });

    }

    private static void initRexistro() {

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



                    }

                } else { // Usuario previamente rexisrado



                }

            }

        });

    }
    
}