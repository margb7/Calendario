package ui;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.MenuElement;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import calendario.Calendario;
import model.Datos;
import model.Evento;
import model.Usuario;
import utilidades.Dia;
import utilidades.Mes;

import java.nio.file.FileSystems;
import java.time.LocalDate;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

/**
 * Interface para mostrar o calendario
 */
public class CalendarioUI extends ElementoUI {

    private JList<Evento> listaEventos;
    private JFrame frame;
    private GridBagConstraints gbc;
    private JPanel panelLateral;
    private JPanel panelContido;
    private JPanel panelCalendario;
    private JButton textoMes;
    private JLabel[] labelDias;
    private JLabel textoDia;
    private JButton[] celdasDias;
    private JButton avanzarMes;
    private JButton retrocederMes;
    private JButton cambioModoCor;

    /**
     * Constructor para esta interface
     */
    public CalendarioUI() {

        init();
        iniciarListeners();
        actualizarTraduccions();
        repintarComponentes();

    }
    
    /**
     * Mostra a interface
     */
    public void mostrarUI() {
    
        // Ten como finalidade actualizar o color no caso de que se actualizase o tema de cor 
        // despois de instanciar a clase
        actualizarTraduccions();
        repintarComponentes();

        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                Toolkit tool = Toolkit.getDefaultToolkit();

                actualizarCalendario();
                frame.setVisible(true);
                frame.setLocation((tool.getScreenSize().width - frame.getWidth()) / 2, (tool.getScreenSize().height - frame.getHeight() ) / 2 );
                
            }

        });

    }

    /**
     * Inicializa os elementos da interface
     */
    public void init() {

        frame = new JFrame(); //Vent?? da aplicaci??n

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1050, 700);
        frame.setMinimumSize(new Dimension(1050, 700));
        frame.setIconImage(new ImageIcon(FileSystems.getDefault().getPath("Imaxes", "icono.png").toString()).getImage());
        frame.setLayout(new GridBagLayout());//layout manager para organizar elementos en cuadr??cula
        
        gbc = new GridBagConstraints();//par??metros para o layout manager
 
        gbc.weightx = 0.5;
        gbc.weighty = 1.0;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;

        panelLateral = new JPanel();
        panelLateral.setOpaque(true);
        panelLateral.setLayout(new BoxLayout(panelLateral, BoxLayout.PAGE_AXIS));
        
        cambioModoCor = new JButton();
        cambioModoCor.setBorder(new BordeRedondo(RADIO_BORDE));
        cambioModoCor.setAlignmentX(Component.CENTER_ALIGNMENT);
        cambioModoCor.setFocusPainted(false);
        panelLateral.add(cambioModoCor);

        textoDia = new JLabel("", JLabel.CENTER);
        textoDia.setFont(FONTE_RESALTE);
        textoDia.setAlignmentX(Component.CENTER_ALIGNMENT);//Parece un pouco a machada isto
        panelLateral.add(textoDia);

        listaEventos = new JList<>();

        panelLateral.add(listaEventos);
        
        frame.add(panelLateral, gbc);

        gbc.weightx = 0.75;
        gbc.gridx = 1;
        panelContido = new JPanel(new BorderLayout());
        panelContido.setOpaque(true);
        frame.add(panelContido, gbc);
        
        avanzarMes = new JButton();
        avanzarMes.setBorder(new BordeRedondo(RADIO_BORDE));
        avanzarMes.setText(">");
        avanzarMes.setFocusPainted(false);
        panelContido.add(avanzarMes, BorderLayout.EAST);

        retrocederMes = new JButton();
        retrocederMes.setBorder(new BordeRedondo(RADIO_BORDE));
        retrocederMes.setText("<");
        retrocederMes.setFocusPainted(false);
        panelContido.add(retrocederMes, BorderLayout.WEST);

        panelCalendario = new JPanel(new GridBagLayout());
        panelCalendario.setOpaque(true);

        panelContido.add(panelCalendario, BorderLayout.CENTER);

        gbc.weightx = 1.0;
        gbc.weighty = 0.75;
        gbc.gridwidth = 7;
        gbc.gridx = 0;
        gbc.gridy = 0;
        
        textoMes = new JButton();//nome de mes
        textoMes.setFont(FONTE_RESALTE);
        textoMes.setFocusPainted(false);
        textoMes.setBorderPainted(false);
        panelCalendario.add(textoMes, gbc);

        gbc.gridwidth = 1;
        gbc.weighty = 0.50;
        gbc.gridy = 1;

        labelDias = new JLabel[7];

        for (byte i = 0; i < 7; i++) {//engade cada etiqueta na posici??n que lle corresponde
            gbc.gridx = i;

            JLabel lab = new JLabel(Dia.values()[i].getNomeCurto(),javax.swing.SwingConstants.CENTER);

            labelDias[i] = lab;

            panelCalendario.add(lab, gbc);

        }

        celdasDias = new JButton[42];//celdas que conte??en os d??as

        gbc.weighty = 1.0;

        byte contador = 0;

        for(int i = 0; i < 6; i++ ) {

            for(int j = 0; j < 7; j++ ) {

                gbc.gridx = j;
                gbc.gridy = i + 2;

                celdasDias[contador] = new JButton();

                celdasDias[contador].setBorderPainted(false);
                celdasDias[contador].setFocusPainted(false);
                celdasDias[contador].setInheritsPopupMenu(true);


                panelCalendario.add(celdasDias[contador], gbc);
                contador++;

            }

        }

        

    }

    /**
     * Inicia os listeners de eventos para diversos elementos da interface
     */
    private void iniciarListeners() {

        listaEventos.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                
                if(listaEventos.getSelectedValue() != null ) {

                    Calendario.mostrarEvento(frame, listaEventos.getSelectedValue());

                }
                
            }
         
            
        });

        // Bot??n (">") na dereita para avanzar o mes
        avanzarMes.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                Calendario.avanzarMes();
                actualizarCalendario();

            }

        });


        // Bot??n ("<") na esquerda para retroceder o mes
        retrocederMes.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                Calendario.retrocederMes();
                actualizarCalendario();
            
            }

        });

        for(int i = 0; i < celdasDias.length; i++ ) {

            JButton boton = celdasDias[i];

            // Se a bbdd est?? desconectada -> non se da a opci??n de crear eventos
            if(Calendario.isConectado()) {

                JMenuItem itemPublico;
                JMenuItem itemPrivado;
                JMenuItem itemGrupal;
                
                boton.setComponentPopupMenu(new JPopupMenu());
                
                itemPublico = new JMenuItem();
                itemPublico.setName("publico");
                boton.getComponentPopupMenu().add(itemPublico);

                itemGrupal = new JMenuItem();
                itemGrupal.setName("grupal");
                boton.getComponentPopupMenu().add(itemGrupal);

                itemPrivado = new JMenuItem();
                itemPrivado.setName("privado");
                boton.getComponentPopupMenu().add(itemPrivado);

                itemGrupal.addActionListener(new ActionListener() {
                    
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        
                        Calendario.pedirDatosEventoGrupal(frame, LocalDate.parse(boton.getName()));
                        
                    }

                });

                // Evento do men?? para crear un evento privado
                itemPrivado.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        
                        Calendario.pedirDatosEventoPrivado(frame, LocalDate.parse(boton.getName()));
                        
                    }

                });

                // Evento do men?? para crear un evento p??blico
                itemPublico.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        
                        Calendario.pedirDatosEventoPublico(frame, LocalDate.parse(boton.getName()));
                        
                    }

                });

            }

            boton.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent e) {

                    // Engadir para cada dia do mes a capacidade de mostrar os eventos no panel lateral
                    JButton boton = (JButton) e.getSource();

                    // Uso do nome do bot??n para obter o d??a que representa
                    LocalDate dataDia = LocalDate.parse(boton.getName());
                    String texto = Dia.values()[dataDia.getDayOfWeek().ordinal()].getNome() + " " + dataDia.getDayOfMonth() + " " +
                                Calendario.getTraduccion("C06", "de") + " " + Mes.values()[dataDia.getMonthValue() - 1].getNome();

                    textoDia.setText(texto);
                    
                    // Vaciar a lista para que non conte??a eventos que non corresponden 
                    listaEventos.setListData(new Evento[0]);

                    actualizarListaEventos(dataDia);

                    // Se o mes do d??a seleccionado non coincide co mes do calendario -> p??sase a mostrar ese mes
                    if(dataDia.getMonthValue() != Calendario.getPrimerDiaMes().getMonthValue() ) {

                        Calendario.setDataCalendario(LocalDate.of(dataDia.getYear(), dataDia.getMonthValue(), 1));

                    }

                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    
                    JButton src = (JButton) e.getSource();
                    
                    src.setBorderPainted(true);
                    
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    
                    JButton src = (JButton) e.getSource();
                    
                    src.setBorderPainted(false);

                }
                
            });

        }

        // Evento para pedir unha data ao usuario que pasar?? a ser a mostrada no calendario
        textoMes.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                Calendario.pedirData(frame);

            }

        });
        
        // Evento para repintar compo??entes
        cambioModoCor.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                ElementoUI.setModoColor(modoColor == ModoColorUI.MODO_CLARO ? ModoColorUI.MODO_OSCURO : ModoColorUI.MODO_CLARO);
                
                repintarComponentes();
                
            }
            
        });

    }

    public void actualizarListaEventos(LocalDate data ) {

        Evento[] eventos = Calendario.obterEventos(data);

        if(eventos.length != 0 ) {

            listaEventos.setListData(eventos);

        }

    }

    /**
     * Actualiza os contidos do calendario para reflexar correctamente o mes. Prec??sase o seu
     * uso cada vez que se fagan cambios no mes do calendario. 
     */
    public void actualizarCalendario() {

        String stringMes = Mes.values()[Calendario.getDataCalendario().getMonthValue() - 1] + ", " + Calendario.getDataCalendario().getYear();
        LocalDate data;
        boolean primerDia = false;

        textoMes.setText(stringMes);
        
        Calendario.setPrimerDiaMes(Calendario.getDataCalendario());

        // data -> comeza no primer d??a do mes
        int offset = Calendario.getDataCalendario().getDayOfWeek().ordinal();
        data = Calendario.getDataCalendario().minusDays(offset);

        Usuario user = Calendario.getUsuario();//Para usar no coloreado dos d??as

        for(int i = 0; i < celdasDias.length; i++ ) {

            JButton celda = celdasDias[i];

            celda.setText(Integer.toString(data.getDayOfMonth()) );
            celda.setName(data.toString());   // Para identificar cada bot??n co seu d??a

            if(!primerDia ) {

                if(celdasDias[i].getText().equals("1") ) {

                    primerDia = true;

                    

                    if (user != null && Datos.getEventosDia(data, Calendario.getUsuario()).length > 0) {

                        celdasDias[i].setForeground(modoColor.getTextoResalte()); // Cor para d??a con evento

                    } else {

                        celdasDias[i].setForeground(modoColor.getTexto()); // Cor para d??a normal

                    }

                } else {

                    celdasDias[i].setForeground(modoColor.getTextoMenosImportante());

                }

            } else {

                if(celdasDias[i].getText().equals("1") ) {

                    primerDia = false;
                    celdasDias[i].setForeground(modoColor.getTextoMenosImportante());

                } else {

                    if (user != null && Datos.getEventosDia(data, Calendario.getUsuario()).length > 0) {

                        celdasDias[i].setForeground(modoColor.getTextoResalte()); // Cor para d??a con evento

                    } else {

                        celdasDias[i].setForeground(modoColor.getTexto()); // Cor para d??a normal

                    }

                }

            }

            celdasDias[i].setBackground(modoColor.getFondo());

            data = data.plusDays(1);

        }

    }

    @Override
    public void actualizarTraduccions() {

        frame.setTitle(Calendario.getTraduccion("C01", "Calendario"));
        cambioModoCor.setText(Calendario.getTraduccion("C02", "Cambiar tema de cor"));

        // Se a bbdd est?? desconectada -> non se da a opci??n de crear eventos
        if(Calendario.isConectado() ) {

            for(JButton boton : celdasDias ) {

                MenuElement[] items = boton.getComponentPopupMenu().getSubElements();
    
                JMenuItem publico = (JMenuItem) items[0];
                JMenuItem grupal = (JMenuItem) items[1];
                JMenuItem privado = (JMenuItem) items[2];
                
                publico.setText(Calendario.getTraduccion("C03", "P??blico"));
                grupal.setText(Calendario.getTraduccion("C04", "Grupal"));
                privado.setText(Calendario.getTraduccion("C05", "Privado"));
    
            }

        }
    
    }

    @Override
    public void repintarComponentes() {

        panelLateral.setBackground(modoColor.getFondo());

        cambioModoCor.setBackground(modoColor.getSeparador());
        cambioModoCor.setForeground(modoColor.getTexto());

        panelContido.setBackground(new Color(0, 255, 0));
        
        panelLateral.setBackground(modoColor.getFondo());
        panelLateral.setForeground(modoColor.getTexto());
        panelContido.setBackground(modoColor.getFondo());

        // Para bot??ns de cambio de mes
        avanzarMes.setForeground(modoColor.getAcento());
        avanzarMes.setBackground(modoColor.getSeparador());
        avanzarMes.setBorderPainted(false);

        retrocederMes.setForeground(modoColor.getAcento());
        retrocederMes.setBackground(modoColor.getSeparador());
        retrocederMes.setBorderPainted(false);


        panelCalendario.setBackground(modoColor.getFondo());
        panelCalendario.setForeground(modoColor.getTexto());
        panelCalendario.setBorder(new LineBorder(modoColor.getBordes(), 2));

        textoMes.setForeground(modoColor.getTextoResalte());
        textoMes.setBackground(modoColor.getFondo());

        textoDia.setForeground(modoColor.getTextoResalte());
        textoDia.setBackground(modoColor.getFondo());

        listaEventos.setBackground(modoColor.getFondo());
        listaEventos.setForeground(modoColor.getTexto());

        for (byte i = 0; i < 7; i++) {

            JLabel lab = labelDias[i];

            lab.setForeground(modoColor.getTextoResalte());
            lab.setBackground(modoColor.getFondo());

        }

        actualizarCalendario();
        
    }

}