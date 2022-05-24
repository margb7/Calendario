package ui;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

public abstract class VistaEvento extends ElementoUI {

    protected JDialog dialogo;
    protected JSplitPane contido;
    protected JPanel info;
    protected JScrollPane detalles;
    protected JLabel hora;
    protected JLabel creador;
    protected JLabel tipo;
    protected JLabel detallesTexto;

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

        info = new JPanel();

        info.setLayout(new BoxLayout(info, BoxLayout.PAGE_AXIS));

        hora = new JLabel();
        hora.setAlignmentX(Component.CENTER_ALIGNMENT);

        creador = new JLabel();
        creador.setAlignmentX(Component.CENTER_ALIGNMENT);

        tipo = new JLabel();
        tipo.setAlignmentX(Component.CENTER_ALIGNMENT);

        info.add(hora);
        info.add(creador);
        info.add(tipo);

        detallesTexto = new JLabel();

        detalles = new JScrollPane(detallesTexto);

        contido = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, info, detalles);
        
        dialogo.setContentPane(contido);

    }

    @Override
    void repintarComponentes() {

        info.setBackground(modoColor.getFondo());
        hora.setForeground(modoColor.getTexto());
        creador.setForeground(modoColor.getTexto());
        tipo.setForeground(modoColor.getTexto());
        detalles.setBackground(modoColor.getFondo());
        detallesTexto.setForeground(modoColor.getTexto());
        
    }
    
}
