package ui;

import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginUI {

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
    public static ModoColorUI modoColor;

    private LoginUI() {}

    static {
        modoColor = ModoColorUI.MODO_CLARO;
    }

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

    /**
     * @return the modoColor
     */
    public static ModoColorUI getModoColor() {
        return modoColor;
    }

    /**
     * @param modoColor the modoColor to set
     */
    public static void setModoColor(ModoColorUI modoColor) {
        LoginUI.modoColor = modoColor;
    }

    public static void init() {
        frame = new JFrame("Inicio Sesión");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cards = new JPanel(new CardLayout());
        frame.setContentPane(cards);

        initLogIn();
        initSignUp();

        frame.add(logInCard, "Log in");
        frame.add(signUpCard, "Sign up");

        logInCard.setBackground(modoColor.getFondo());
        signUpCard.setBackground(modoColor.getFondo());

    }

    private static void initLogIn() {

        logInCard = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        usernameLogIn = new JTextField(20);

        unameLogInLabel = new JLabel("Usuario");
        unameLogInLabel.setLabelFor(usernameLogIn);
        unameLogInLabel.setForeground(modoColor.getTexto());

        passwordLogin = new JPasswordField(20);

        pswdLoginLabel = new JLabel("Contrasinal");
        pswdLoginLabel.setLabelFor(passwordLogin);
        pswdLoginLabel.setForeground(modoColor.getTexto());

        submitLogIn = new JButton("Log in");

        signUpButton = new JButton("Crea unha nova conta");
        
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        logInCard.add(unameLogInLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        logInCard.add(usernameLogIn, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        logInCard.add(pswdLoginLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        logInCard.add(passwordLogin, gbc);

        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 2;
        logInCard.add(submitLogIn, gbc);

        gbc.gridy = 3;
        logInCard.add(signUpButton, gbc);

    }

    private static void initSignUp() {

        signUpCard = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        usernameSignUp = new JTextField(20);

        unameSignUpLabel = new JLabel("Usuario");
        unameSignUpLabel.setLabelFor(usernameSignUp);
        unameSignUpLabel.setForeground(modoColor.getTexto());

        passwordSignUp = new JPasswordField(20);

        pswdSignUpLabel = new JLabel("Contrasinal");
        pswdSignUpLabel.setLabelFor(passwordSignUp);
        passwordSignUp.setForeground(modoColor.getTexto());

        confirmPassword = new JPasswordField(20);
        confirmPswdLabel = new JLabel("Confirma o contrasinal");

        submitSignUp = new JButton("Sign up");

        logInButton = new JButton("Xa estás rexistrado?");
        
        gbc.insets = new Insets(10, 10, 10, 10);
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
        
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 3;
        signUpCard.add(submitSignUp, gbc);

        gbc.gridy = 4;
        signUpCard.add(logInButton, gbc);

    }

    public static void mostrarUI() {

        frame.setVisible(true);

    }


}