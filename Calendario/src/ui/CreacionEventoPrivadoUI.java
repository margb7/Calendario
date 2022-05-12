package ui;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

public class CreacionEventoPrivadoUI extends ElementoUI {

    private JDialog dialogoCreacion;
    private JLabel nombreEventoLabel;
    private JLabel horaEventoLabel;
    private JTextField nombreEventoTexto;
    private JSpinner horaEventoValor;
    private JButton crearEventoButton;
    private JButton cancelarButton;

    public CreacionEventoPrivadoUI() {

        initCreacionEvento();

    }

    public void initCreacionEvento() {

        JDialog dialogoCreacion = new JDialog();

        nombreEventoLabel = new JLabel("Nombre:");
        nombreEventoLabel.setLabelFor(nombreEventoTexto);

        horaEventoLabel = new JLabel("Hora:");

        nombreEventoTexto = new JTextField(15);

        horaEventoValor = new JSpinner(new SpinnerDateModel());
        horaEventoValor.setEditor(new JSpinner.DateEditor(horaEventoValor, "HH:mm"));

        dialogoCreacion.add(nombreEventoLabel);
        dialogoCreacion.add(nombreEventoTexto);
        dialogoCreacion.add(horaEventoLabel);
        dialogoCreacion.add(horaEventoValor);

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

        dialogoCreacion.setVisible(true);
        dialogoCreacion.getContentPane().setBackground(modoColor.getFondo());
        dialogoCreacion.setSize(300, 100);

        x = frame.getX() + (frame.getWidth() / 2) - (dialogoCreacion.getWidth() / 2);
        y = frame.getY() + (frame.getHeight() / 2) - (dialogoCreacion.getHeight() / 2);

        dialogoCreacion.setLocation(x, y);

    }
    

}
