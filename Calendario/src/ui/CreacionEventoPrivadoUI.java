package ui;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.JSpinner.DateEditor;
import javax.swing.plaf.InsetsUIResource;
import javax.swing.text.DateFormatter;

import calendario.Calendario;
import excepcions.CredenciaisIncorrectasException;
import excepcions.EventoXaExisteException;
import utilidades.Funciones;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.awt.event.WindowAdapter;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

/**
 * Interface para crear un evento privado
 */
public class CreacionEventoPrivadoUI extends ElementoUI {

    private JDialog dialogoCreacion;
    private JLabel nombreEventoLabel;
    private JLabel horaEventoLabel;
    private JTextField nombreEventoTexto;
    private JSpinner horaEventoValor;
    private JButton crearEventoButton;
    private JButton cancelarButton;
    private GridBagConstraints gbc;
    private SpinnerDateModel mod;
    private LocalDate data;

    /**
     * Constructor para a interface de creación de eventos privados
     */
    public CreacionEventoPrivadoUI() {

        init();
        iniciarListeners();

    }

    /**
     * Setter para declarar o día no cal acontecerá o evento creado
     * @param data a data na cal ocorre o evento
     */
    public void setData(LocalDate data) {
        this.data = data;
    }

    /**
     * Inicializa os compoñentes desta interface
     */
    public void init() {

        dialogoCreacion = new JDialog();
        dialogoCreacion.setLayout(new GridBagLayout());

        gbc = new GridBagConstraints();

        nombreEventoLabel = new JLabel();
        nombreEventoLabel.setLabelFor(nombreEventoTexto);

        horaEventoLabel = new JLabel();

        nombreEventoTexto = new JTextField(15);

        mod = new SpinnerDateModel();

        horaEventoValor = new JSpinner(mod);

        DateEditor editor = new JSpinner.DateEditor(horaEventoValor, "HH:mm");
        DateFormatter formatter = (DateFormatter)editor.getTextField().getFormatter();
        formatter.setAllowsInvalid(false);
        formatter.setOverwriteMode(false);
        
        horaEventoValor.setEditor(editor);

        crearEventoButton = new JButton();

        cancelarButton = new JButton();

        gbc.gridx = 0;
        gbc.gridy = 0;
        dialogoCreacion.add(nombreEventoLabel, gbc);

        gbc.gridx = 1;
        dialogoCreacion.add(nombreEventoTexto, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        dialogoCreacion.add(horaEventoLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        dialogoCreacion.add(horaEventoValor, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new InsetsUIResource(20, 0, 0, 0);
        gbc.anchor = GridBagConstraints.LINE_START;
        dialogoCreacion.add(crearEventoButton, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        dialogoCreacion.add(cancelarButton, gbc);

    }

    /**
     * Inicia os listeners de eventos desta interface
     */
    private void iniciarListeners() {

        cancelarButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                dialogoCreacion.dispose();
                
            }
            
        });

        crearEventoButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                try {
                    
                    Calendar cal = Calendar.getInstance();
                    String nome = Funciones.purificarString(nombreEventoTexto.getText());
                    LocalTime hora;

                    cal.setTime((Date)(horaEventoValor.getValue()));

                    hora = LocalTime.of(cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE)); 

                    Calendario.crearEventoPrivado(nome, data, hora);
                    dialogoCreacion.dispose();

                } catch (EventoXaExisteException exc ) {
                    
                    Calendario.mostrarErro(Calendario.getTraduccion("E10", "O evento xa existe"));

                } catch(CredenciaisIncorrectasException credExc ) {

                    Calendario.mostrarErro(Calendario.getTraduccion("E13", "O nome non pode estar vacío"));

                }

            }

        });

    }

    @Override
    public void mostrarUI() {

        repintarComponentes();
        actualizarTraduccions();

        dialogoCreacion.setVisible(true);
        dialogoCreacion.setSize(300, 100);

        dialogoCreacion.setLocationRelativeTo(null);
        
    }
    
    /**
     * Mostra a interface gráfica deste elemento
     * @param frame o frame que chama a esta interface e que quedará bloqueado
     */
    public void mostrarUI(JFrame frame ) {
        
        repintarComponentes();
        actualizarTraduccions();

        int x,y;

        dialogoCreacion.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosed(WindowEvent e) {

                frame.setEnabled(true);
                
            }

            @Override
            public void windowOpened(WindowEvent e) {

                frame.setEnabled(false);

            }

        });

        dialogoCreacion.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        dialogoCreacion.setUndecorated(true);
        dialogoCreacion.getRootPane().setBorder(new javax.swing.border.LineBorder(Color.GRAY, 1));
        dialogoCreacion.setVisible(true);
        dialogoCreacion.setSize(300, 200);

        x = frame.getX() + (frame.getWidth() / 2) - (dialogoCreacion.getWidth() / 2);
        y = frame.getY() + (frame.getHeight() / 2) - (dialogoCreacion.getHeight() / 2);

        dialogoCreacion.setLocation(x, y);
        
    }

    @Override
    void repintarComponentes() {
        
        dialogoCreacion.getContentPane().setBackground(modoColor.getFondo());
        nombreEventoLabel.setForeground(modoColor.getTexto());
        nombreEventoLabel.setBackground(modoColor.getFondo());
        horaEventoLabel.setForeground(modoColor.getTexto());
        horaEventoLabel.setBackground(modoColor.getFondo());
        nombreEventoTexto.setForeground(modoColor.getTexto());
        nombreEventoTexto.setBackground(modoColor.getFondo());
        nombreEventoTexto.setCaretColor(modoColor.getTexto());
        horaEventoValor.getComponent(0).setBackground(modoColor.getFondo());
        horaEventoValor.getComponent(0).setForeground(modoColor.getTexto());
        horaEventoValor.getComponent(1).setBackground(modoColor.getFondo());
        horaEventoValor.getComponent(1).setForeground(modoColor.getTexto());
        horaEventoValor.getEditor().getComponent(0).setBackground(modoColor.getFondo());
        horaEventoValor.getEditor().getComponent(0).setForeground(modoColor.getTexto());
        horaEventoValor.getComponent(2).setForeground(modoColor.getTexto());
        crearEventoButton.setForeground(modoColor.getTexto());
        crearEventoButton.setBackground(modoColor.getFondo());
        cancelarButton.setForeground(modoColor.getTexto());
        cancelarButton.setBackground(modoColor.getFondo());
        
    }
    
    @Override
    public void actualizarTraduccions() {
        
        nombreEventoLabel.setText(Calendario.getTraduccion("P01", "Nome:"));
        horaEventoLabel.setText(Calendario.getTraduccion("P02", "Hora:"));
        crearEventoButton.setText(Calendario.getTraduccion("P03", "Crear"));
        cancelarButton.setText(Calendario.getTraduccion("P04", "Cancelar"));
        dialogoCreacion.setTitle(Calendario.getTraduccion("P05", "Crear evento privado"));

    }

}