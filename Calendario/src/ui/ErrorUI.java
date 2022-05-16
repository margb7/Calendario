package ui;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;

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
        dialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE); // TODO: no seguro 

        label = new JLabel("", JLabel.CENTER);
        label.setForeground(modoColor.getTexto());
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

        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                
                Toolkit tool = Toolkit.getDefaultToolkit();

                dialog.setTitle(Calendario.getTraduccion("E07", "Erro"));
                dialog.setVisible(true);
                dialog.getContentPane().setBackground(modoColor.getFondo());
                label.setForeground(modoColor.getTexto());
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

        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                
                int x,y;
        
                dialog.setTitle(Calendario.getTraduccion("E07", "Erro"));
                dialog.setVisible(true);
                dialog.getContentPane().setBackground(modoColor.getFondo());
                label.setForeground(modoColor.getTexto());
                dialog.setSize(300, 100);

                x = frame.getX() + (frame.getWidth() / 2) - (dialog.getWidth() / 2);
                y = frame.getY() + (frame.getHeight() / 2) - (dialog.getHeight() / 2);

                dialog.setLocation(x, y);
                
            }
            
        });

    }

}