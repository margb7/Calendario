package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;

import javax.swing.JButton;

public class VistaEventoPrivado extends VistaEvento {

    private JButton borrarEventoButton;

    public VistaEventoPrivado(String nome, String texto, String tipo, String usuario, String hora) {

        super(nome, texto, tipo, usuario, hora);
        //TODO

    }

    @Override
    void repintarComponentes() {
        // TODO Auto-generated method stub
        
    }

    @Override
    void mostrarUI() {
        // TODO Auto-generated method stub
        
    }

    private void init() {

        borrarEventoButton = new JButton("Borrar evento");//TODO tradución

        getInfo().add(borrarEventoButton, BorderLayout.SOUTH);

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
