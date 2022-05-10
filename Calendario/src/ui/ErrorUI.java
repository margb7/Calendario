package ui;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;

/**
 * Clase para mostrar nunha ventá os diferentes erros do programa
 */
public class ErrorUI extends ElementoUI {
    
    private JDialog dialog;
    private JLabel label;

    /**
     * Constructor para os obxectos da interface de erros
     */
    public ErrorUI() {

        iniciarComponentes();

    }

    @Override
    public void iniciarComponentes() {

        label = new JLabel("", JLabel.CENTER);
        label.setIcon(UIManager.getIcon("OptionPane.errorIcon"));

    }

    /**
     * Devolve o <code>JDialog</code> da interface de erro.
     * @return
     */
    public JDialog getDialog() {
        return dialog;
    }

    /**
     * Getter para obter o compoñente ca mensaxe de erro do diálogo.
     * @return o <code>JLabel</code> que representa a mensaxe de erro do diálogo.
     */
    public JLabel getLabel() {
        return label;
    }

    /**
     * Setter para o <code>JDialog</code> da interface de erro.
     * @param dialog o novo dialogo
     */
    public void setDialog(JDialog dialog) {
        this.dialog = dialog;
        this.dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.dialog.add(label);
    }

    /**
     * Mostra a interface de erro.
     * @param frame a xanela que lanza o erro para que a interface de erro se coloque centrada
     * no medio desa xanela.
     */
    public void mostrarUI(JFrame frame ) {

        int x,y;

        repintarComponentes();  // Antes de mostrar xa se actualizan as cores da interfaz

        dialog.setVisible(true);
        dialog.setResizable(false);
        dialog.setSize(300, 100);

        x = frame.getX() + (frame.getWidth() / 2) - (dialog.getWidth() / 2);
        y = frame.getY() + (frame.getHeight() / 2) - (dialog.getHeight() / 2);

        dialog.setLocation(x, y);

    }

    /**
     * Mostra a interface de erro sen bloquear ningunha outra xanela.
     */
    @Override
    public void mostrarUI() {

        repintarComponentes();  // Antes de mostrar xa se actualizan as cores da interfaz

        dialog.setVisible(true);
        dialog.setResizable(false);
        dialog.setSize(300, 100);

        dialog.setLocationRelativeTo(null);

    }

    /**
     * Repinta as cores da interface gráfica.
     */
    @Override
    protected void repintarComponentes() {
        
        label.setForeground(modoColor.getTexto());
        dialog.getContentPane().setBackground(modoColor.getFondo());
        label.setForeground(modoColor.getTexto());
        
    }

}