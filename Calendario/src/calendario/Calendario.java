package calendario;

import utilidades.Dia;
import utilidades.Funciones;
import utilidades.Mes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.CardLayout;

import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;


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

    // Elementos da interface
    private static ErrorUI interfaceErro;
    private static LoginUI interfaceLogin;
    private static CalendarioUI interfaceCalendario;
    private static SeleccionDataUI interfaceSeleccionarData;


    // Variables de estado do calendario
    private static LocalDate dataCalendario;
    private static LocalDate primerDiaMes;
    private static Usuario usuario;

    /**
     * @return the usuario
     */
    public static Usuario getUsuario() {
        return usuario;
    }

    /**
     * @return the interfaceCalendario
     */
    public static CalendarioUI getInterfaceCalendario() {
        return interfaceCalendario;
    }

    /**
     * @return the interfaceErro
     */
    public static ErrorUI getInterfaceErro() {
        return interfaceErro;
    }

    /**
     * @return the interfaceLogin
     */
    public static LoginUI getInterfaceLogin() {
        return interfaceLogin;
    }

    /**
     * @return the interfaceSeleccionarData
     */
    public static SeleccionDataUI getInterfaceSeleccionarData() {
        return interfaceSeleccionarData;
    }

    /**
     * @param usuario the usuario to set
     */
    public static void setUsuario(Usuario usuario) {
        Calendario.usuario = usuario;
    }

    /**
     * @param dataCalendario the dataCalendario to set
     */
    public static void setDataCalendario(LocalDate dataCalendario) {
        Calendario.dataCalendario = dataCalendario;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // Para activar antialiasing nas fontes cando non están activadas por defecto
        System.setProperty("awt.useSystemAAFontSettings","on");

        // Cargar idiomas
        Datos.cargarIdiomas();
        Datos.setIdomaSeleccionado("Castellano");

        // Tema de color para todos os elementos
        ElementoUI.setModoColor(ModoColorUI.MODO_CLARO);
        
        // Instanciar os elementos da interface e configurar 
        // idiomas de textos, actionlisteners, ...
        iniciarComponentes();

        // Mostrar a interfaz
        interfaceLogin.mostrarUI();
        
    }

    private static void iniciarComponentes() {

        interfaceLogin = new LoginController().instanciarElemento();
        interfaceCalendario = new CalendarioController().instanciarElemento();


        interfaceSeleccionarData = new SeleccionDataUI();
        initSeleccionData();

    }

    /**
     * Inicializa os atributos e os eventos da ventá do calendario antes de mostralo.
     */
    public static void initCalendario() {

        

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

                interfaceSeleccionarData.getSeleccionData().setVisible(true);

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

    /**
     * Lanza un diálogo mostrando información dun erro. Tamén bloquea a ventá que chama 
     * ao erro ata que se pecha o erro.
     * @param owner a ventá que lanza o erro e que quedará bloqueada.
     * @param str a mensaxe de erro.
     */
    public static void mostrarErro(JFrame owner, String str ) {

        interfaceErro.setDialog(new JDialog(owner, Datos.getTraduccion("E07", "Erro")));
        interfaceErro.getDialog().setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        interfaceErro.getLabel().setText(str);
        interfaceErro.getDialog().add(interfaceErro.getLabel());

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
    
}