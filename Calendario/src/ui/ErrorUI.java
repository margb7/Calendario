package ui;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;

/**
 * Clase para mostrar nunha ventá os diferentes erros do programa
 */
public class ErrorUI extends ElementoUI {
    
    private static JDialog dialog;
    private static JLabel label;

    private ErrorUI() {}

    /**
     * Constructor estático para a interface
     */
    static {
        
        label = new JLabel("", JLabel.CENTER);
        label.setIcon(UIManager.getIcon("OptionPane.errorIcon"));
        label.setForeground(modoColor.getTexto());

    }

    public static JDialog getDialog() {
        return dialog;
    }

    /**
     * @return the label
     */
    public static JLabel getLabel() {
        return label;
    }

    /**
     * Setter para o JDialog
     * @param dialog o novo dialogo
     */
    public static void setDialog(JDialog dialog) {
        ErrorUI.dialog = dialog;
    }

    /**
     * @param modoColor the modoColor to set
     */
    public static void setModoColor(ModoColorUI modoColor) {
        ErrorUI.modoColor = modoColor;
    }

    /**
     * Mostra a interface de erro
     * @param frame a ventá que lanza o erro para que a interface de erro se coloque centrada
     * no medio desa ventá.
     */
    public static void mostrarUI(JFrame frame ) {

        int x,y;

        dialog.setVisible(true);
        dialog.getContentPane().setBackground(modoColor.getFondo());
        label.setForeground(modoColor.getTexto());
        dialog.setSize(300, 100);

        x = frame.getX() + (frame.getWidth() / 2) - (dialog.getWidth() / 2);
        y = frame.getY() + (frame.getHeight() / 2) - (dialog.getHeight() / 2);

        dialog.setLocation(x, y);

    }

}