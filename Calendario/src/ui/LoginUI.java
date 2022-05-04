package ui;

import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.Toolkit;

public class LoginUI {

    private static String titulo;
    private static JFrame frame;
    private static JButton botonAceptar;
    private static JButton botonRegistro;

    /**
     * Constructor privado para evitar instancias
     */
    private LoginUI() {}

    public static JButton getBotonAceptar() {
        return botonAceptar;
    }

    public static JButton getBotonRegistro() {
        return botonRegistro;
    }

    public static JFrame getFrame() {
        return frame;
    }
    
    /**
     * @return the titulo
     */
    public static String getTitulo() {
        return titulo;
    }

    /**
     * @param titulo the titulo to set
     */
    public static void setTitulo(String titulo) {
        LoginUI.titulo = titulo;
    }
    

    public static void init() {

        titulo = "Login";

        frame = new JFrame();//Ventá da aplicación

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        botonAceptar = new JButton("OK");
        botonRegistro = new JButton("Registro");

        frame.add(botonAceptar);
        //frame.add(botonRegistro);

    }

    public static void mostrarUI() {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Toolkit tool = Toolkit.getDefaultToolkit();

                frame.setSize(400, 400);
                frame.setVisible(true);
                frame.setTitle(titulo);
                frame.setLocation((tool.getScreenSize().width - frame.getWidth()) / 2, (tool.getScreenSize().height - frame.getHeight() ) / 2 );
            }
        });

    }

    
}
