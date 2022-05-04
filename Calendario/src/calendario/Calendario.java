package calendario;

import utilidades.Dia;
import utilidades.Mes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JButton;

import model.Datos;
import ui.CalendarioUI;
import ui.LoginUI;

/**
 *  TODO: funciona como controlador
 */
public class Calendario {

    private static Boolean modoRegistro;
    private static LocalDate dataCalendario;
    private static LocalDate primerDiaMes;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // Iniciar componentes
        modoRegistro = false;
        
        initLogin();
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

        LoginUI.getBotonAceptar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                LoginUI.getFrame().setVisible(false);
                LoginUI.getFrame().dispose();
                CalendarioUI.mostrarUI();                

            }


        });

        LoginUI.getBotonRegistro().addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                
                if(modoRegistro) {

                    modoRegistro = false;
                    vistaLogin();

                } else {

                    modoRegistro = true;
                    vistaRegistro();

                }

            }

        });

    }

    /**
     * Cambia a interfaz do login para que permita iniciar sesión
     */
    private static void vistaLogin() {

        LoginUI.getFrame().setTitle("Login");
        LoginUI.getBotonRegistro().setText("Registro");

    }

    /**
     * Cambia a interfaz do login para que permita rexistrarse
     */
    private static void vistaRegistro() {

        LoginUI.getFrame().setTitle("Registro");
        LoginUI.getBotonRegistro().setText("Login");

    }
    
}
