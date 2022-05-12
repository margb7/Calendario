package ui;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.Datos;

public class LoginUI extends ElementoUI {

    private JFrame frame;
    private JPanel cards;
    private JPanel logInCard;
    private JPanel signUpCard;
    private JButton logInButton;
    private JButton signUpButton;
    private JButton submitLogIn;
    private JButton submitSignUp;
    private JTextField usernameLogIn;
    private JLabel unameLogInLabel;
    private JPasswordField passwordLogin;
    private JLabel pswdLoginLabel;
    private JTextField usernameSignUp;
    private JLabel unameSignUpLabel;
    private JPasswordField passwordSignUp;
    private JLabel pswdSignUpLabel;
    private JPasswordField confirmPassword;
    private JLabel confirmPswdLabel;
    private JButton cambioModoCorLogIn;
    private JButton cambioModoCorSignUp;

    public LoginUI() {

        init();

    }

    public JFrame getFrame() {
        return frame;
    }

    public JPanel getCards() {
        return cards;
    }

    /**
     * @return the logInCard
     */
    public JPanel getLogInCard() {
        return logInCard;
    }

    /**
     * @return the signUpCard
     */
    public JPanel getSignUpCard() {
        return signUpCard;
    }

    public JButton getLogInButton() {
        return logInButton;
    }

    public JButton getSignUpButton() {
        return signUpButton;
    }

    public JTextField getUsernameSignUp() {
        return usernameSignUp;
    }

    public JTextField getUsernameLogIn() {
        return usernameLogIn;
    }

    public JPasswordField getConfirmPassword() {
        return confirmPassword;
    }

    public JPasswordField getPasswordLogin() {
        return passwordLogin;
    }

    public JPasswordField getPasswordSignUp() {
        return passwordSignUp;
    }

    public JButton getSubmitLogIn() {
        return submitLogIn;
    }

    public JButton getSubmitSignUp() {
        return submitSignUp;
    }

    public JButton getCambioModoCorLogIn() {
        return cambioModoCorLogIn;
    }

    public JButton getCambioModoCorSignUp() {
        return cambioModoCorSignUp;
    }

    public void init() {

        frame = new JFrame();

        frame.setSize(600, 400);
        frame.setMinimumSize(new Dimension(400, 300));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        cards = new JPanel(new CardLayout());
        frame.setContentPane(cards);

        initLogIn();
        initSignUp();

        frame.add(logInCard);
        frame.add(signUpCard);

        actualizarTraduccions();
        repintarComponentes();

    }

    private void initLogIn() {

        logInCard = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        usernameLogIn = new JTextField(15);

        unameLogInLabel = new JLabel();
        unameLogInLabel.setLabelFor(usernameLogIn);

        passwordLogin = new JPasswordField(15);

        pswdLoginLabel = new JLabel();
        pswdLoginLabel.setLabelFor(passwordLogin);

        submitLogIn = new JButton();

        signUpButton = new JButton();

        cambioModoCorLogIn = new JButton();
        
        gbc.insets = new Insets(10, 0, 0, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        logInCard.add(unameLogInLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        logInCard.add(usernameLogIn, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        logInCard.add(pswdLoginLabel, gbc);

        gbc.insets = new Insets(10, 0, 5, 5);
        gbc.gridx = 1;
        gbc.gridy = 1;
        logInCard.add(passwordLogin, gbc);

        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 2;
        logInCard.add(submitLogIn, gbc);

        gbc.gridy = 3;
        logInCard.add(signUpButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        logInCard.add(cambioModoCorLogIn, gbc);

    }

    private void initSignUp() {

        signUpCard = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        usernameSignUp = new JTextField(15);

        unameSignUpLabel = new JLabel();
        unameSignUpLabel.setLabelFor(usernameSignUp);

        passwordSignUp = new JPasswordField(15);

        pswdSignUpLabel = new JLabel();
        pswdSignUpLabel.setLabelFor(passwordSignUp);

        confirmPassword = new JPasswordField(15);
        confirmPswdLabel = new JLabel();

        submitSignUp = new JButton();

        logInButton = new JButton();

        cambioModoCorSignUp = new JButton();
        
        gbc.insets = new Insets(10, 0, 0, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        signUpCard.add(unameSignUpLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        signUpCard.add(usernameSignUp, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        signUpCard.add(pswdSignUpLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        signUpCard.add(passwordSignUp, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        signUpCard.add(confirmPswdLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        signUpCard.add(confirmPassword, gbc);
        
        gbc.insets = new Insets(10, 0, 5, 5);
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 3;
        signUpCard.add(submitSignUp, gbc);

        gbc.gridy = 4;
        signUpCard.add(logInButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        signUpCard.add(cambioModoCorSignUp, gbc);

    }

    @Override
    public void actualizarTraduccions() {
        
        frame.setTitle(Datos.getTraduccion("L01", "Inicio Sesión"));

        // Para login
        logInCard.setName(Datos.getTraduccion("L02", "Log in"));
        unameLogInLabel.setText(Datos.getTraduccion("L04", "Usuario"));
        pswdLoginLabel.setText(Datos.getTraduccion("L05", "Contrasinal"));
        submitLogIn.setText(Datos.getTraduccion("L06", "Log in"));
        signUpButton.setText(Datos.getTraduccion("L07", "Crea unha nova conta"));
        cambioModoCorLogIn.setText(ElementoUI.getModoColor() == ModoColorUI.MODO_CLARO ? Datos.getTraduccion("L08", "Modo escuro") : Datos.getTraduccion("L09", "Modo claro"));

        // Para rexistro
        signUpCard.setName(Datos.getTraduccion("L03", "Sign up"));
        unameSignUpLabel.setText(Datos.getTraduccion("L04", "Usuario"));
        pswdSignUpLabel.setText(Datos.getTraduccion("L05", "Contrasinal"));
        confirmPswdLabel.setText(Datos.getTraduccion("L10", "Confirma o contrasinal"));
        submitSignUp.setText(Datos.getTraduccion("L03", "Sign up"));
        logInButton.setText(Datos.getTraduccion("L11", "Xa estás rexistrado?"));
        cambioModoCorSignUp.setText(ElementoUI.getModoColor() == ModoColorUI.MODO_CLARO ? Datos.getTraduccion("L08", "Modo escuro") : Datos.getTraduccion("L09", "Modo claro"));

    }

    public void mostrarUI() {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                
                frame.setVisible(true);
                
            }
            
        });
        

    }

    public void repintarComponentes() {

        //frame;
        //cards;
        logInCard.setBackground(modoColor.getFondo());
        signUpCard.setBackground(modoColor.getFondo());
        logInButton.setBackground(modoColor.getFondo());
        logInButton.setForeground(modoColor.getTexto());
        signUpButton.setBackground(modoColor.getFondo());
        signUpButton.setForeground(modoColor.getTexto());
        submitLogIn.setBackground(modoColor.getFondo());
        submitLogIn.setForeground(modoColor.getTexto());
        submitSignUp.setBackground(modoColor.getFondo());
        submitSignUp.setForeground(modoColor.getTexto());
        //usernameLogIn;
        unameLogInLabel.setForeground(modoColor.getTexto());
        //passwordLogin;
        pswdLoginLabel.setForeground(modoColor.getTexto());
        //usernameSignUp;
        unameSignUpLabel.setForeground(modoColor.getTexto());
        //passwordSignUp;
        pswdSignUpLabel.setForeground(modoColor.getTexto());
        //confirmPassword;
        confirmPswdLabel.setForeground(modoColor.getTexto());
        cambioModoCorLogIn.setBackground(modoColor.getFondo());
        cambioModoCorLogIn.setForeground(modoColor.getTexto());
        cambioModoCorSignUp.setBackground(modoColor.getFondo());
        cambioModoCorSignUp.setForeground(modoColor.getTexto());

    }

}