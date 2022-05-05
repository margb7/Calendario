package ui;


import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;

/**
 * Clase para mostrar nunha ventá os diferentes erros do programa
 */
public class ErrorUI {
    
    private static JDialog dialog;
    private static JLabel label;

    private ErrorUI() {}

    /**
     * Constructor estático para a interface
     */
    static {

        label = new JLabel();
        label.setIcon(UIManager.getIcon("OptionPane.errorIcon"));

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
     * @param label the label to set
     */
    public static void setLabel(JLabel label) {
        ErrorUI.label = label;
    }

    /**
     * Setter para o JDialog
     * @param dialog o novo dialogo
     */
    public static void setDialog(JDialog dialog) {
        ErrorUI.dialog = dialog;
    }

    /**
     * Mostra a interface de erro
     * @param frame a ventá que lanza o erro para que a interface de erro se coloque centrada
     * no medio desa ventá.
     */
    public static void mostrarUI(JFrame frame ) {

        int x,y;

        dialog.setVisible(true);
        dialog.setSize(300, 100);

        x = frame.getX() + (frame.getWidth() / 2) - (dialog.getWidth() / 2);
        y = frame.getY() + (frame.getHeight() / 2) - (dialog.getHeight() / 2);

        dialog.setLocation(x, y);

    }

}