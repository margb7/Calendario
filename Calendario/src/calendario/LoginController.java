package calendario;

import ui.ElementoUI;
import ui.LoginUI;
import ui.ModoColorUI;
import utilidades.Funciones;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import excepcions.UsuarioNonAtopadoException;
import excepcions.UsuarioXaRexistradoException;
import model.Datos;
import model.Usuario;

import java.awt.CardLayout;

public class LoginController implements Controller<LoginUI> {

    @Override
    public LoginUI instanciarElemento() {
        
        LoginUI out = new LoginUI();

        crearListeners(out);

        return out;
    }

    @Override
    public void crearTraduccions(LoginUI instancia) {
        
        instancia.getFrame().setTitle(Datos.getTraduccion("L01", "Inicio Sesión"));
        instancia.getLogInCard().setName(Datos.getTraduccion("L02", "Log in"));
        instancia.getSignUpCard().setName( Datos.getTraduccion("L03", "Sign up"));

        instancia.getUnameLogInLabel().setText(Datos.getTraduccion("L04", "Usuario"));
        instancia.getUnameSignUpLabel().setText(Datos.getTraduccion("L05", "Contrasinal"));

        instancia.getSubmitLogIn().setText(Datos.getTraduccion("L06", "Log in"));
        instancia.getSignUpButton().setText(Datos.getTraduccion("L07", "Crea unha nova conta"));

        instancia.getCambioModoCorLogIn().setText(ElementoUI.getModoColor() == ModoColorUI.MODO_CLARO ? Datos.getTraduccion("L08", "Modo escuro") : Datos.getTraduccion("L09", "Modo claro"));
        
        
    
    }

    @Override
    public void crearListeners(LoginUI instancia) {

        // Para cambiar de color (nos JButton do login)
        ActionListener cambioColor = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                ElementoUI.cambiarModo();

                JButton source = (JButton) e.getSource();

                source.setText(ElementoUI.getModoColor() == ModoColorUI.MODO_CLARO ? Datos.getTraduccion("L08", "Modo escuro") : Datos.getTraduccion("L09", "Modo claro"));

                instancia.repintarComponentes();
                
            }

        };

        instancia.getCambioModoCorLogIn().addActionListener(cambioColor);
        instancia.getCambioModoCorSignUp().addActionListener(cambioColor);

        
        // Evento para cambiar ao modo rexistro dende o modo inicio de sesión
        ActionListener cambioCard = new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                
                CardLayout cl = (CardLayout) instancia.getCards().getLayout();

                cl.next(instancia.getCards());

            }

        };
        instancia.getLogInButton().addActionListener(cambioCard);
        instancia.getSignUpButton().addActionListener(cambioCard);

        // Evento ao clicar no botón para iniciar sesión ou rexistrarse
        instancia.getSubmitLogIn().addActionListener(eventoLogin(instancia));
        instancia.getSubmitSignUp().addActionListener(eventoRexistro(instancia));

    }
    
    private static ActionListener eventoRexistro(LoginUI instancia) {

        ActionListener out = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if(!Datos.usuarioEstaRexistrado(instancia.getUsernameSignUp().getText()) ) {  // Usuario non rexistrado

                    String passwd = new String(instancia.getPasswordSignUp().getPassword());
                    String confirm = new String(instancia.getConfirmPassword().getPassword());

                    if(passwd.equals(confirm) ) {       // Rexistro correcto

                        if(Funciones.nomeUsuarioValido(instancia.getUsernameSignUp().getText()) ) {

                            if(Funciones.contrasinalValida(passwd) ) {

                                try {
    
                                    
                                    Calendario.setUsuario(Datos.rexistrarUsuario(instancia.getUsernameSignUp().getText(), passwd));
                                    instancia.getFrame().setVisible(false);

                                    // Mostrar calendario
                                    Calendario.initCalendario();
                                    Calendario.getInterfaceCalendario().mostrarUI();
        
                                } catch(UsuarioXaRexistradoException ex ) {
        
                                    // Erro inesperado coa base de datos
        
                                } 

                            } else {
    
                                Calendario.mostrarErro(instancia.getFrame(), Datos.getTraduccion("E03", "A contrasinal non é válida"));  // TODO : explicar que requerimentos fan falta
                                instancia.getConfirmPassword().setText("");
                                instancia.getPasswordSignUp().setText("");
                                
                            }

                        } else {

                            Calendario.mostrarErro(instancia.getFrame(), Datos.getTraduccion("E04", "Nome de usuario non válido"));
                            instancia.getConfirmPassword().setText("");
                            instancia.getPasswordSignUp().setText("");
                            instancia.getUsernameSignUp().setText("");

                        }

                    } else {    // Rexistro incorrecto (a contrasinal e a confirmación non concordan)

                        Calendario.mostrarErro(instancia.getFrame(), Datos.getTraduccion("E05", "As contrasinais non coinciden"));
                        instancia.getConfirmPassword().setText("");
                        instancia.getPasswordSignUp().setText("");

                    }

                } else { // Usuario previamente rexisrado

                    Calendario.mostrarErro(instancia.getFrame(), Datos.getTraduccion("E06", "Usuario xa rexistrado"));
                    instancia.getConfirmPassword().setText("");
                    instancia.getPasswordSignUp().setText("");
                    instancia.getUsernameSignUp().setText("");

                }

            }

        };

        return out;
    }

    private static ActionListener eventoLogin(LoginUI instancia) {

        ActionListener out = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                Usuario user;                
                
                try {

                    String passwd;

                    user = Datos.getUsuarioPorNome(instancia.getUsernameLogIn().getText());

                    passwd = new String(instancia.getPasswordLogin().getPassword());

                    if(user.getContrasinal().equals(passwd) ) {     // Inicio de sesión correcto

                        
                        Calendario.setUsuario(user);
                        instancia.getFrame().setVisible(false);
                        // Mostrar calendario
                        Calendario.initCalendario();
                        Calendario.getInterfaceCalendario().mostrarUI();

                    } else {        // Usuario existe -> pero non é a contrasinal correcta

                        Calendario.mostrarErro(instancia.getFrame(), Datos.getTraduccion("E01", "Credenciais incorrectas"));
                        instancia.getPasswordLogin().setText("");
                        instancia.getUsernameLogIn().setText("");

                    }

                } catch(UsuarioNonAtopadoException ex ) {        // Non existe o usuario 

                    Calendario.mostrarErro(instancia.getFrame(), Datos.getTraduccion("E02", "O usuario non está rexistrado"));
                    instancia.getPasswordLogin().setText("");
                    instancia.getUsernameLogIn().setText("");

                }

            }
            
        };

        return out;
    }

}
