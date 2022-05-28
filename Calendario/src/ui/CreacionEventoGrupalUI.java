package ui;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.JSpinner.DateEditor;
import javax.swing.plaf.InsetsUIResource;
import javax.swing.text.DateFormatter;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;

import calendario.Calendario;
import excepcions.CredenciaisIncorrectasException;
import excepcions.EventoXaExisteException;
import excepcions.UsuarioNonAtopadoException;
import model.Datos;
import utilidades.Funciones;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import java.time.LocalDate;
import java.time.LocalTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CreacionEventoGrupalUI extends ElementoUI {

    private JDialog dialogoCreacion;
    private JLabel nombreEventoLabel;
    private JLabel horaEventoLabel;
    private JLabel participanteLabel;
    private JLabel listaLabel;
    private JTextField participanteTexto;
    private JTextField nombreEventoTexto;
    private JSpinner horaEventoValor;
    private JButton crearEventoButton;
    private JButton cancelarButton;
    private JButton engadirButton;
    private JList<String> participantesLista;
    private GridBagConstraints gbc;
    private JScrollPane panelLista;
    private SpinnerDateModel mod;
    private LocalDate data;
    private static DefaultListModel<String> model;

    public CreacionEventoGrupalUI() {

        init();
        iniciarListeners();

    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    private void init() {

        gbc = new GridBagConstraints();
        
        dialogoCreacion = new JDialog();
        dialogoCreacion.setLayout(new GridBagLayout());
        dialogoCreacion.setMinimumSize(new Dimension(150, 400));

        gbc = new GridBagConstraints();

        nombreEventoLabel = new JLabel("default");
        nombreEventoLabel.setLabelFor(nombreEventoTexto);

        horaEventoLabel = new JLabel("default");

        nombreEventoTexto = new JTextField(20);

        mod = new SpinnerDateModel();

        horaEventoValor = new JSpinner(mod);

        DateEditor editor = new JSpinner.DateEditor(horaEventoValor, "HH:mm");
        DateFormatter formatter = (DateFormatter)editor.getTextField().getFormatter();
        formatter.setAllowsInvalid(false);
        formatter.setOverwriteMode(false);
        
        horaEventoValor.setEditor(editor);

        participanteLabel = new JLabel("default");
        
        participanteTexto = new JTextField(15);

        engadirButton = new JButton("default");
        engadirButton.setFocusPainted(false);

        listaLabel = new JLabel("default");
        
        model = new DefaultListModel<>();
        participantesLista = new JList<>();

        participantesLista.setCellRenderer(new DefaultListCellRenderer() {

            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                    boolean cellHasFocus) {
                    
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                if(isSelected ) {

                    setBackground(modoColor.getAcento());

                } else {
                    
                    setBackground(modoColor.getFondo());

                }
                
                setForeground(modoColor.getTexto());

                return c;

            }

        });

        panelLista = new JScrollPane();
        panelLista.setViewportView(participantesLista);
        participantesLista.setLayoutOrientation(JList.VERTICAL);
        panelLista.setColumnHeaderView(listaLabel);

        crearEventoButton = new JButton("default");
        crearEventoButton.setFocusPainted(false);

        cancelarButton = new JButton("default");
        cancelarButton.setFocusPainted(false);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new InsetsUIResource(30, 0, 0, 0);
        dialogoCreacion.add(nombreEventoLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        dialogoCreacion.add(nombreEventoTexto, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new InsetsUIResource(10, 0, 0, 0);
        dialogoCreacion.add(horaEventoLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        dialogoCreacion.add(horaEventoValor, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        dialogoCreacion.add(participanteLabel, gbc);

        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipadx = 10;
        gbc.insets = new InsetsUIResource(10, 0, 0, 5);
        dialogoCreacion.add(participanteTexto, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new InsetsUIResource(10, 0, 0, 0);
        dialogoCreacion.add(engadirButton, gbc);

        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 0.15;
        gbc.insets = new InsetsUIResource(20, 0, 0, 0);
        dialogoCreacion.add(panelLista, gbc);
        
        gbc.gridy = 7;
        gbc.gridwidth = 1; 
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weighty = 0.1;
        gbc.insets = new InsetsUIResource(20, 0, 0, 20);
        dialogoCreacion.add(crearEventoButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.insets = new InsetsUIResource(20, 20, 0, 0);
        dialogoCreacion.add(cancelarButton, gbc);

    }

    private void iniciarListeners() {

        //TODO listener para eliminar elemento da lista con doble click (?????)
        
        cancelarButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                model.removeAllElements();  // Reset para que no seguinte evento non se "arrastren" usuarios do anterior evento 
                participantesLista.setListData(new String[0]);
                dialogoCreacion.dispose();
                
            }
            
        });

        crearEventoButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                try {

                    String nomeEvento = Funciones.purificarString(nombreEventoTexto.getText());

                    if(nomeEvento.isEmpty() ) {

                        Calendario.mostrarErro(Calendario.getTraduccion("E09", "Non é un nome válido"));

                    } else {

                        int tamanhoLista = participantesLista.getModel().getSize();

                        ArrayList<Integer> participantes = new ArrayList<>();
                        Calendar cal = Calendar.getInstance();
                        LocalTime hora;

                        for (int i = 0; i < tamanhoLista; i++) {

                            participantes.add(Datos.getUsuarioPorNome(participantesLista.getModel().getElementAt(i)).getId());

                        }

                        cal.setTime((Date)(horaEventoValor.getValue()));
                        hora = LocalTime.of(cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE));

                        Calendario.crearEventoGrupal(nomeEvento, data, hora, participantes);

                        model.removeAllElements();  // Reset para que no seguinte evento non se "arrastren" usuarios do anterior evento 

                        participantesLista.setListData(new String[0]);
                        dialogoCreacion.dispose();

                    }

                } catch (EventoXaExisteException exc ) {
                    
                    Calendario.mostrarErro(Calendario.getTraduccion("E10", "O evento xa existe"));

                } catch (UsuarioNonAtopadoException e1) {
                    
                    Calendario.mostrarErro(Calendario.getTraduccion("E11", "Usuario non atopado"));

                } catch (CredenciaisIncorrectasException e1) {
                    
                    Calendario.mostrarErro(Calendario.getTraduccion("E09", "Non é un nome válido"));
                    
                }

            }

        });

        engadirButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                String participante = Funciones.purificarString(participanteTexto.getText());
                participanteTexto.setText("");

                if (Datos.usuarioEstaRexistrado(participante)) {

                    if(!participante.equals(Calendario.getUsuario().getNome()) ) {

                        boolean xaIncluido = false;

                        for(int i = 0; i < participantesLista.getModel().getSize() && !xaIncluido; i++ ) {

                            if(participantesLista.getModel().getElementAt(i).equals(participante) ) {

                                xaIncluido = true;

                            }

                        }

                        if(xaIncluido ) {

                            Calendario.mostrarErro(Calendario.getTraduccion("E12", "Xa está incluído na lista de participantes")); //TODO :  traducción

                        } else {

                            model.addElement(participante);
                            participantesLista.setModel(model);

                        }

                    } else {

                        Calendario.mostrarErro(Calendario.getTraduccion("E", "O creador xa está incluído"));

                    }
                    
                } else {

                    Calendario.mostrarErro(Calendario.getTraduccion("E02", "O usuario non está rexistrado")); //TODO tradución

                }             
                
            }            

        });

    }

    @Override
    void repintarComponentes() {
        
        dialogoCreacion.getContentPane().setBackground(modoColor.getFondo());
        nombreEventoLabel.setForeground(modoColor.getTexto());
        horaEventoLabel.setForeground(modoColor.getTexto());
        participanteLabel.setForeground(modoColor.getTexto());
        participanteTexto.setForeground(modoColor.getTexto());
        participanteTexto.setBackground(modoColor.getFondo());
        participanteTexto.setCaretColor(modoColor.getTexto());
        nombreEventoTexto.setForeground(modoColor.getTexto());
        nombreEventoTexto.setBackground(modoColor.getFondo());
        nombreEventoTexto.setCaretColor(modoColor.getTexto());
        horaEventoValor.getComponent(0).setBackground(modoColor.getFondo());
        horaEventoValor.getComponent(0).setForeground(modoColor.getTexto());
        horaEventoValor.getComponent(1).setBackground(modoColor.getFondo());
        horaEventoValor.getComponent(1).setForeground(modoColor.getTexto());
        horaEventoValor.getEditor().getComponent(0).setBackground(modoColor.getFondo());
        horaEventoValor.getEditor().getComponent(0).setForeground(modoColor.getTexto());
        horaEventoValor.getComponent(2).setForeground(modoColor.getTexto());
        crearEventoButton.setForeground(modoColor.getTexto());
        crearEventoButton.setBackground(modoColor.getFondo());
        cancelarButton.setForeground(modoColor.getTexto());
        cancelarButton.setBackground(modoColor.getFondo());
        engadirButton.setForeground(modoColor.getTexto());
        engadirButton.setBackground(modoColor.getFondo());

        panelLista.getViewport().setBackground(modoColor.getFondo());
        
        panelLista.setForeground(modoColor.getTexto());
        panelLista.setBackground(modoColor.getFondo());

        participantesLista.setBackground(modoColor.getFondo());
    }

    @Override
    void mostrarUI() {
        
        
    }

    public void mostrarUI(JFrame frame) {

        repintarComponentes();
        actualizarTraduccions();

        int x,y;

        dialogoCreacion.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosed(WindowEvent e) {

                frame.setEnabled(true);
                
            }

            @Override
            public void windowOpened(WindowEvent e) {

                frame.setEnabled(false);

            }

        });

        dialogoCreacion.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        dialogoCreacion.setUndecorated(true);
        dialogoCreacion.getRootPane().setBorder(new javax.swing.border.LineBorder(Color.GRAY, 1));
        dialogoCreacion.setVisible(true);
        dialogoCreacion.setSize(300, 200);

        x = frame.getX() + (frame.getWidth() / 2) - (dialogoCreacion.getWidth() / 2);
        y = frame.getY() + (frame.getHeight() / 2) - (dialogoCreacion.getHeight() / 2);

        dialogoCreacion.setLocation(x, y);

    }

    @Override
    public void actualizarTraduccions() {
        
        nombreEventoLabel.setText(Calendario.getTraduccion("G01", "Nome:"));
        horaEventoLabel.setText(Calendario.getTraduccion("G02", "Hora:"));
        crearEventoButton.setText(Calendario.getTraduccion("G03", "Crear"));
        cancelarButton.setText(Calendario.getTraduccion("G04", "Cancelar"));
        participanteLabel.setText(Calendario.getTraduccion("G05", "Nome participante"));
        engadirButton.setText(Calendario.getTraduccion("G06", "Engadir"));
        listaLabel.setText(Calendario.getTraduccion("G07", "Participantes"));
        dialogoCreacion.setTitle(Calendario.getTraduccion("G08", "Crear evento grupal"));

    }
    
}
