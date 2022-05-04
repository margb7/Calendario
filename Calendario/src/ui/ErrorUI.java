package ui;


import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;

public class ErrorUI {
    
    private static JDialog dialog;
    private static JLabel label;

    private ErrorUI() {}

    public static void mostrarErro(JFrame frame, String str) {

        dialog = new JDialog(frame, "Erro");
        label = new JLabel(str);

        label.setIcon(UIManager.getIcon("OptionPane.errorIcon"));

        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.add(label);
        dialog.setVisible(true);
        dialog.setSize(300, 100);

        int x = frame.getX() + (frame.getWidth() / 2) - (dialog.getWidth() / 2);
        int y = frame.getY() + (frame.getHeight() / 2) - (dialog.getHeight() / 2);

        dialog.setLocation(x, y);
        
    }

    public static JDialog getDialog() {
        return dialog;
    }

}