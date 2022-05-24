package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingConstants;

import excepcions.UsuarioNonAtopadoException;
import model.Datos;
import model.EventoPrivado;

public class VistaEventoPrivado extends VistaEvento {

    private JButton borrarEventoButton;

    public VistaEventoPrivado() {

        init();

    }

    @Override
    void repintarComponentes() {

        super.repintarComponentes();
        borrarEventoButton.setForeground(modoColor.getTexto());
        borrarEventoButton.setBackground(modoColor.getFondo());
        
    }

    @Override
    void mostrarUI() {
        
        
        
    }

    public void mostrarUI(JFrame frame, EventoPrivado ev) {
        
        hora.setText("Hora: " + ev.getTempo().getHour() + ":" + ev.getTempo().getMinute());

        try {

            creador.setText("Creador: " + Datos.getUsuarioPorId(ev.getCreador()).getNome());

        } catch (UsuarioNonAtopadoException e1) {}

        tipo.setText("Tipo: privado");

        //detallesTexto.setText();TODO hai descricion?

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

    private void init() {

        borrarEventoButton = new JButton("Borrar evento");//TODO tradución
        borrarEventoButton.setFocusPainted(false);

        getInfo().add(borrarEventoButton);

                

    }

    private void iniciarListeners() {

        borrarEventoButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                //TODO borrar evento
                
            }

        });

    }
    
}
