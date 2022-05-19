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
import java.awt.Color;

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

    public CreacionEventoPrivadoUI() {

        initCreacionEvento();

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

    public void initCreacionEvento() {

        dialogoCreacion = new JDialog();
        dialogoCreacion.setLayout(new GridBagLayout());

        gbc = new GridBagConstraints();

        nombreEventoLabel = new JLabel("Nombre:");
        nombreEventoLabel.setLabelFor(nombreEventoTexto);

        horaEventoLabel = new JLabel("Hora:");

        nombreEventoTexto = new JTextField(15);

        mod = new SpinnerDateModel();

        horaEventoValor = new JSpinner(mod);

        DateEditor editor = new JSpinner.DateEditor(horaEventoValor, "HH:mm");
        DateFormatter formatter = (DateFormatter)editor.getTextField().getFormatter();
        formatter.setAllowsInvalid(false);
        formatter.setOverwriteMode(false);
        
        horaEventoValor.setEditor(editor);

        crearEventoButton = new JButton("Crear");

        cancelarButton = new JButton("Cancelar");

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

    @Override
    public void mostrarUI() {

        dialogoCreacion.setVisible(true);
        dialogoCreacion.getContentPane().setBackground(modoColor.getFondo());
        dialogoCreacion.setSize(300, 100);

        dialogoCreacion.setLocationRelativeTo(null);
        
    }
    
    public void mostrarUI(JFrame frame ) {

        int x,y;

        dialogoCreacion.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialogoCreacion.getContentPane().setBackground(modoColor.getFondo());
        dialogoCreacion.setUndecorated(true);
        dialogoCreacion.getRootPane().setBorder(new javax.swing.border.LineBorder(Color.GRAY, 1));
        dialogoCreacion.setVisible(true);
        dialogoCreacion.setSize(300, 200);

        x = frame.getX() + (frame.getWidth() / 2) - (dialogoCreacion.getWidth() / 2);
        y = frame.getY() + (frame.getHeight() / 2) - (dialogoCreacion.getHeight() / 2);

        dialogoCreacion.setLocation(x, y);
        

    }
    

}