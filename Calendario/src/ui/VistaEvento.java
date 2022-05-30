package ui;

import java.awt.BorderLayout;
import java.awt.Component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import calendario.Calendario;
import excepcions.UsuarioNonAtopadoException;
import model.Datos;
import model.Evento;
import model.EventoPrivado;
import model.EventoPublico;

public class VistaEvento extends ElementoUI {

    private JDialog dialogo;
    private JPanel info;
    private JScrollPane detalles;
    private JLabel hora;
    private JLabel creador;
    private JLabel tipo;
    private JLabel detallesTexto;
    private JPanel contido;
    private JPanel panelBoton;
    private JButton borrarEventoButton;
    private Evento evento;

    public VistaEvento() {

        init();
        iniciarListeners();

    }

    protected JPanel getInfo() {
        return info;
    }

    public JDialog getDialogo() {
        return dialogo;
    }

    private void init() {

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

        panelBoton = new JPanel(new BorderLayout());

        borrarEventoButton = new JButton();
        borrarEventoButton.setFocusPainted(false);

        panelBoton.add(borrarEventoButton, BorderLayout.SOUTH);
        
        contido.add(panelBoton, BorderLayout.SOUTH);

    }

    @Override
    void repintarComponentes() {

        info.setBackground(modoColor.getFondo());
        hora.setForeground(modoColor.getTexto());
        creador.setForeground(modoColor.getTexto());
        tipo.setForeground(modoColor.getTexto());
        detalles.setBackground(modoColor.getFondo());
        detallesTexto.setForeground(modoColor.getTexto());
        borrarEventoButton.setForeground(modoColor.getTexto());
        borrarEventoButton.setBackground(modoColor.getFondo());
    
    }

    @Override
    void mostrarUI() {
        
    }

    public void mostrarUI(JFrame frame, Evento ev ) {

        dialogo.setTitle(ev.getNome());

        this.evento = ev;

        borrarEventoButton.setText(Calendario.getTraduccion("V0", "Borrar evento"));

        hora.setText(Calendario.getTraduccion("V02", "Hora: ") + ev.getTempo().getHour() + ":" + ev.getTempo().getMinute());

        try {

            creador.setText(Calendario.getTraduccion("V01", "Creador: ") + Datos.getUsuarioPorId(ev.getCreador()).getNome());

        } catch (UsuarioNonAtopadoException e1) {}

        String tipoString;

        if(ev instanceof EventoPrivado ) {

            tipoString = Calendario.getTraduccion("C05", "Privado");

        } else if (ev instanceof EventoPublico ) {

            tipoString = Calendario.getTraduccion("C03", "Público");

        } else {

            tipoString = Calendario.getTraduccion("C04", "Grupal");

        }
        
        tipo.setText(Calendario.getTraduccion("V03", "Tipo: ") + tipoString );

        repintarComponentes();
        actualizarTraduccions();

        int x,y;

        getDialogo().addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosed(WindowEvent e) {

                frame.setEnabled(true);
                
            }

            @Override
            public void windowOpened(WindowEvent e) {

                frame.setEnabled(false);

            }

        });

        getDialogo().setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        getDialogo().setVisible(true);
        getDialogo().setSize(300, 200);

        x = frame.getX() + (frame.getWidth() / 2) - (getDialogo().getWidth() / 2);
        y = frame.getY() + (frame.getHeight() / 2) - (getDialogo().getHeight() / 2);

        getDialogo().setLocation(x, y);

    }

    private void iniciarListeners() {

        borrarEventoButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                Calendario.borrarEvento(evento);
                Calendario.getInterfaceCalendario().actualizarListaEventos(evento.getData());

                dialogo.setVisible(false);
                dialogo.dispose();

            }

        });

    }
    
}
