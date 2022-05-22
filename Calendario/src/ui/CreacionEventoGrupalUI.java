package ui;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.JSpinner.DateEditor;
import javax.swing.plaf.InsetsUIResource;
import javax.swing.text.DateFormatter;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.WindowAdapter;
import java.time.LocalDate;
import java.awt.event.WindowEvent;
import java.awt.Color;
import java.awt.Dimension;

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

        listaLabel = new JLabel("default");

        String[] defList = {"default", "default", "default"}; //TODO borrar cando se finalice a implementacion
        participantesLista = new JList<>(defList);

        panelLista = new JScrollPane();
        panelLista.setViewportView(participantesLista);
        participantesLista.setLayoutOrientation(JList.VERTICAL);
        panelLista.setColumnHeaderView(listaLabel);

        crearEventoButton = new JButton("default");

        cancelarButton = new JButton("default");

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
        dialogoCreacion.add(participanteTexto, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.NONE;
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
        
    }

    @Override
    void repintarComponentes() {
        // TODO Auto-generated method stub
        
    }

    @Override
    void mostrarUI() {
        // TODO Auto-generated method stub
        
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
    
}
