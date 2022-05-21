package ui;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Toolkit;

import calendario.Calendario;

/**
 * Clase para mostrar nunha ventá os diferentes erros do programa
 */
public class ErrorUI extends ElementoUI {
    
    private JDialog dialog;
    private JLabel label;      

    public ErrorUI() {

        dialog = new JDialog();
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        label = new JLabel("", JLabel.CENTER);
        label.setIcon(UIManager.getIcon("OptionPane.errorIcon"));
        dialog.add(label);

    }

    public JDialog getDialog() {
        return dialog;
    }

    /**
     * @return a <code>JLabel</code> que representa a mensaxe de erro
     */
    public JLabel getLabel() {
        return label;
    }

    @Override
    public void mostrarUI() {

        dialog.addWindowListener(new WindowAdapter() {

            @Override
            public void windowOpened(WindowEvent e) {
                
                dialog.setAlwaysOnTop(true);
                
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                
                dialog.dispose();

            }
            
        });

        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                
                Toolkit tool = Toolkit.getDefaultToolkit();

                repintarComponentes();
                
                dialog.setTitle(Calendario.getTraduccion("E07", "Erro"));
                dialog.setVisible(true);

                dialog.setSize(300, 100);

                dialog.setLocationRelativeTo(null);
                dialog.setLocation((tool.getScreenSize().width - dialog.getWidth()) / 2, (tool.getScreenSize().height - dialog.getHeight() ) / 2 );
            
            }
            
        });
        
    }

    /**
     * Mostra a interface de erro
     * @param frame a ventá que lanza o erro para que a interface de erro se coloque centrada
     * no medio desa ventá.
     */
    public void mostrarUI(JFrame frame ) {

        // Evento para crear un diálgo de forma que ata que non se peche non 
        // se poda interactuar co owner
        dialog.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosed(WindowEvent e) {
                
                frame.setFocusable(true);
                frame.setFocusableWindowState(true);
                frame.setEnabled(true);
                
            }

            @Override
            public void windowOpened(WindowEvent e) {
                
                dialog.setAlwaysOnTop(true);

                frame.setFocusable(false);
                frame.setFocusableWindowState(false);
                frame.setEnabled(false);
                
                frame.addWindowListener(new WindowAdapter() {

                    @Override
                    public void windowGainedFocus(WindowEvent e) {
                        
                        frame.setEnabled(false);

                    } 

                });

            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                
                dialog.toFront();

            }
            
        });

        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                
                int x,y;

                repintarComponentes();
        
                dialog.setTitle(Calendario.getTraduccion("E07", "Erro"));
                dialog.setVisible(true);
                
                dialog.setSize(300, 100);

                x = frame.getX() + (frame.getWidth() / 2) - (dialog.getWidth() / 2);
                y = frame.getY() + (frame.getHeight() / 2) - (dialog.getHeight() / 2);

                dialog.setLocation(x, y);
                
            }
            
        });

    }

    @Override
    void repintarComponentes() {

        dialog.getContentPane().setBackground(modoColor.getFondo());
        label.setForeground(modoColor.getTexto());

    }

}