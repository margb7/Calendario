package ui;

import java.awt.BorderLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

public abstract class VistaEvento extends ElementoUI {

    private JDialog dialogo;
    private JSplitPane contido;
    private JPanel info;
    private JScrollPane detalles;
    private JLabel hora;
    private JLabel creador;
    private JLabel tipo;
    private JLabel detallesTexto;

    protected VistaEvento(String nome, String texto, String tipo, String usuario, String hora) {

        init(texto, nome, tipo, usuario, hora);

    }

    private void init(String texto, String nome, String tipoEv, String usuario, String horaEv) {//TODO traducións

        dialogo = new JDialog();
        dialogo.setTitle(nome);

        info = new JPanel(new BorderLayout());

        hora = new JLabel("Hora: " + horaEv);

        creador = new JLabel("Creador:" + usuario); 

        tipo = new JLabel("Tipo: " + tipoEv);

        info.add(hora, BorderLayout.NORTH);
        info.add(creador, BorderLayout.NORTH);
        info.add(tipo, BorderLayout.NORTH);

        detallesTexto = new JLabel(texto);

        detalles = new JScrollPane(detallesTexto);

        contido = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, info, detalles);
        
        dialogo.setContentPane(contido);

    }
    
}
