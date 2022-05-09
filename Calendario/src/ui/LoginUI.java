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

    private static JFrame frame;
    private static JPanel cards;
    private static JPanel logInCard;
    private static JPanel signUpCard;
    private static JButton logInButton;
    private static JButton signUpButton;
    private static JButton submitLogIn;
    private static JButton submitSignUp;
    private static JTextField usernameLogIn;
    private static JLabel unameLogInLabel;
    private static JPasswordField passwordLogin;
    private static JLabel pswdLoginLabel;
    private static JTextField usernameSignUp;
    private static JLabel unameSignUpLabel;
    private static JPasswordField passwordSignUp;
    private static JLabel pswdSignUpLabel;
    private static JPasswordField confirmPassword;
    private static JLabel confirmPswdLabel;
    private static JButton cambioModoCorLogIn;
    private static JButton cambioModoCorSignUp;

    private LoginUI() {}

    public static JFrame getFrame() {
        return frame;
    }

    public static JPanel getCards() {
        return cards;
    }

    public static JButton getLogInButton() {
        return logInButton;
    }

    public static JButton getSignUpButton() {
        return signUpButton;
    }

    public static JTextField getUsernameSignUp() {
        return usernameSignUp;
    }

    public static JTextField getUsernameLogIn() {
        return usernameLogIn;
    }

    public static JPasswordField getConfirmPassword() {
        return confirmPassword;
    }

    public static JPasswordField getPasswordLogin() {
        return passwordLogin;
    }

    public static JPasswordField getPasswordSignUp() {
        return passwordSignUp;
    }

    public static JButton getSubmitLogIn() {
        return submitLogIn;
    }

    public static JButton getSubmitSignUp() {
        return submitSignUp;
    }

    public static JButton getCambioModoCorLogIn() {
        return cambioModoCorLogIn;
    }

    public static JButton getCambioModoCorSignUp() {
        return cambioModoCorSignUp;
    }

    public static void init() {

        frame = new JFrame(Datos.getTraduccion("L01", "Inicio Sesión"));

        frame.setSize(600, 400);
        frame.setMinimumSize(new Dimension(400, 300));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        cards = new JPanel(new CardLayout());
        frame.setContentPane(cards);

        initLogIn();
        initSignUp();

        frame.add(logInCard, Datos.getTraduccion("L02", "Log in"));
        frame.add(signUpCard, Datos.getTraduccion("L03", "Sign up"));

        logInCard.setBackground(modoColor.getFondo());
        signUpCard.setBackground(modoColor.getFondo());

    }

    private static void initLogIn() {

        logInCard = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        usernameLogIn = new JTextField(15);

        unameLogInLabel = new JLabel(Datos.getTraduccion("L04", "Usuario"));
        unameLogInLabel.setLabelFor(usernameLogIn);
        unameLogInLabel.setForeground(modoColor.getTexto());

        passwordLogin = new JPasswordField(15);

        pswdLoginLabel = new JLabel(Datos.getTraduccion("L05", "Contrasinal"));
        pswdLoginLabel.setLabelFor(passwordLogin);
        pswdLoginLabel.setForeground(modoColor.getTexto());

        submitLogIn = new JButton(Datos.getTraduccion("L06", "Log in"));

        signUpButton = new JButton(Datos.getTraduccion("L07", "Crea unha nova conta"));

        cambioModoCorLogIn = new JButton(getModoColor() == ModoColorUI.MODO_CLARO ? Datos.getTraduccion("L08", "Modo escuro") : Datos.getTraduccion("L09", "Modo claro"));
        
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

    private static void initSignUp() {

        signUpCard = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        usernameSignUp = new JTextField(15);

        unameSignUpLabel = new JLabel(Datos.getTraduccion("L04", "Usuario"));
        unameSignUpLabel.setLabelFor(usernameSignUp);
        unameSignUpLabel.setForeground(modoColor.getTexto());

        passwordSignUp = new JPasswordField(15);

        pswdSignUpLabel = new JLabel(Datos.getTraduccion("L05", "Contrasinal"));
        pswdSignUpLabel.setLabelFor(passwordSignUp);
        passwordSignUp.setForeground(modoColor.getTexto());

        confirmPassword = new JPasswordField(15);
        confirmPswdLabel = new JLabel(Datos.getTraduccion("L10", "Confirma o contrasinal"));

        submitSignUp = new JButton(Datos.getTraduccion("L03", "Sign up"));

        logInButton = new JButton(Datos.getTraduccion("L11", "Xa estás rexistrado?"));

        cambioModoCorSignUp = new JButton(getModoColor() == ModoColorUI.MODO_CLARO ? Datos.getTraduccion("L08", "Modo escuro") : Datos.getTraduccion("L09", "Modo claro"));
        
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

    public static void mostrarUI() {

        frame.setVisible(true);

    }

    public static void repintarComponentes() {

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