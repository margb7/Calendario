package ui;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public abstract class VistaEvento extends ElementoUI {

    protected JDialog dialogo;
    protected JPanel info;
    protected JScrollPane detalles;
    protected JLabel hora;
    protected JLabel creador;
    protected JLabel tipo;
    protected JLabel detallesTexto;
    protected JPanel contido;

    protected VistaEvento() {

        init();

    }

    protected JPanel getInfo() {
        return info;
    }

    public JDialog getDialogo() {
        return dialogo;
    }

    private void init() {//TODO traducións

        dialogo = new JDialog();

        contido = new JPanel(new BorderLayout());

        info = new JPanel();

        info.setLayout(new BoxLayout(info, BoxLayout.PAGE_AXIS));

        hora = new JLabel();
        hora.setAlignmentX(Component.CENTER_ALIGNMENT);
        hora.setAlignmentY(Component.CENTER_ALIGNMENT);

        creador = new JLabel();
        creador.setAlignmentX(Component.CENTER_ALIGNMENT);
        creador.setAlignmentY(Component.CENTER_ALIGNMENT);

        tipo = new JLabel();
        tipo.setAlignmentX(Component.CENTER_ALIGNMENT);
        tipo.setAlignmentY(Component.CENTER_ALIGNMENT);

        info.add(hora);
        info.add(creador);
        info.add(tipo);

        contido.add(info, BorderLayout.CENTER);

        detallesTexto = new JLabel();

        detalles = new JScrollPane(detallesTexto);
        
        dialogo.setContentPane(contido);

    }
    
}
