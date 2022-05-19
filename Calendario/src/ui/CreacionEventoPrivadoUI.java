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
import model.Datos;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.awt.event.WindowAdapter;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

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

    public CreacionEventoPrivadoUI() {

        init();
        iniciarListeners();

    }

    /**
     * @return the data
     */
    public LocalDate getData() {
        return data;
    }

    public JDialog getDialogoCreacion() {
        return dialogoCreacion;
    }

    public JButton getCancelarButton() {
        return cancelarButton;
    }

    public JButton getCrearEventoButton() {
        return crearEventoButton;
    }

    /**
     * @param data
     */
    public void setData(LocalDate data) {
        this.data = data;
    }

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
                
                Datos.crearEventoGrupal(nombreEventoTexto.getText(), Calendario.getUsuario(), data, (LocalTime) horaEventoValor.getValue());
                dialogoCreacion.dispose();
                horaEventoValor.getValue();
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