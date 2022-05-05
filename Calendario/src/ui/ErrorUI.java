package ui;


import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;

public class ErrorUI {
    
    private static JDialog dialog;
    private static JLabel label;

    private ErrorUI() {}

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
     * @param dialog the dialog to set
     */
    public static void setDialog(JDialog dialog) {
        ErrorUI.dialog = dialog;
    }

    public static void mostrarUI(JFrame frame ) {

        int x,y;

        dialog.setVisible(true);
        dialog.setSize(300, 100);

        x = frame.getX() + (frame.getWidth() / 2) - (dialog.getWidth() / 2);
        y = frame.getY() + (frame.getHeight() / 2) - (dialog.getHeight() / 2);

        dialog.setLocation(x, y);

    }

}