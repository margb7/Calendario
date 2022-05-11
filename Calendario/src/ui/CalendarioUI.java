package ui;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import model.Evento;

import java.awt.Toolkit;

import utilidades.Dia;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

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
    private JMenuItem itemPublico;
    private JMenuItem itemPrivado;
    private JMenuItem itemGrupal;
    private JButton cambioModoCor;

    /**
     * Constructor privado para evitar instancias
     */
    public CalendarioUI() {

        init();

    }

    /**
     * @return the avanzarMes
     */
    public JButton getAvanzarMes() {
        return avanzarMes;
    }

    /**
     * @return the retrocederMes
     */
    public JButton getRetrocederMes() {
        return retrocederMes;
    }

    public JList<Evento> getListaEventos() {
        return listaEventos;
    }

    /**
     * @return the celdasDias
     */
    public JButton[] getCeldasDias() {
        return celdasDias;
    }

    /**
     * @return the panelCalendario
     */
    public JPanel getPanelCalendario() {
        return panelCalendario;
    }
    
    /**
     * @return the textoMes
     */
    public JButton getTextoMes() {
        return textoMes;
    }

    public JLabel getTextoDia() {
        return textoDia;
    }

    public JFrame getFrame() {
        return frame;
    }

    public JMenuItem getItemGrupal() {
        return itemGrupal;
    }

    public JMenuItem getItemPrivado() {
        return itemPrivado;
    }

    public JMenuItem getItemPublico() {
        return itemPublico;
    }

    public JButton getCambioModoCor() {
        return cambioModoCor;
    }
    
    public void mostrarUI() {
        
        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                Toolkit tool = Toolkit.getDefaultToolkit();

                actualizarCalendario();
                frame.setVisible(true);
                frame.setLocation((tool.getScreenSize().width - frame.getWidth()) / 2, (tool.getScreenSize().height - frame.getHeight() ) / 2 );
                
            }

        });

    }

    public void init() {

        frame = new JFrame();//Ventá da aplicación

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setMinimumSize(new Dimension(600, 400));

        frame.setLayout(new GridBagLayout());//layout manager para organizar elementos en cuadrícula
        gbc = new GridBagConstraints();//parámetros para o layout manager
 
        gbc.weightx = 0.5;
        gbc.weighty = 1.0;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;

        panelLateral = new JPanel();
        panelLateral.setOpaque(true);
        panelLateral.setBackground(modoColor.getFondo());
        panelLateral.setLayout(new BoxLayout(panelLateral, BoxLayout.PAGE_AXIS));
        
        cambioModoCor = new JButton();
        cambioModoCor.setAlignmentX(Component.CENTER_ALIGNMENT);
        cambioModoCor.setFocusPainted(false);
        cambioModoCor.setBackground(modoColor.getFondo());
        cambioModoCor.setForeground(modoColor.getTexto());
        panelLateral.add(cambioModoCor);

        textoDia = new JLabel("", JLabel.CENTER);
        textoDia.setForeground(modoColor.getTextoResalte());
        textoDia.setBackground(modoColor.getFondo());
        textoDia.setAlignmentX(Component.CENTER_ALIGNMENT);//Parece un pouco a machada isto
        panelLateral.add(textoDia);

        listaEventos = new JList<>();
        listaEventos.setBackground(modoColor.getFondo());
        listaEventos.setForeground(modoColor.getTexto());

        panelLateral.add(listaEventos);
        
        frame.add(panelLateral, gbc);

        gbc.weightx = 0.75;
        gbc.gridx = 1;
        panelContido = new JPanel(new BorderLayout());
        panelContido.setOpaque(true);
        panelContido.setBackground(new Color(0, 255, 0));
        frame.add(panelContido, gbc);
        
        avanzarMes = new JButton();
        avanzarMes.setText(">");
        avanzarMes.setForeground(modoColor.getAcento());
        avanzarMes.setBackground(modoColor.getSeparador());
        panelContido.add(avanzarMes, BorderLayout.EAST);

        retrocederMes = new JButton();
        retrocederMes.setText("<");
        retrocederMes.setForeground(modoColor.getAcento());
        retrocederMes.setBackground(modoColor.getSeparador());
        panelContido.add(retrocederMes, BorderLayout.WEST);

        panelCalendario = new JPanel(new GridBagLayout());
        panelCalendario.setOpaque(true);
        panelCalendario.setBackground(modoColor.getFondo());

        panelCalendario.setComponentPopupMenu(new JPopupMenu());

        itemPublico = new JMenuItem();
        panelCalendario.getComponentPopupMenu().add(itemPublico);

        itemGrupal = new JMenuItem();
        panelCalendario.getComponentPopupMenu().add(itemGrupal);

        itemPrivado = new JMenuItem();

        panelCalendario.getComponentPopupMenu().add(itemPrivado);
        panelContido.add(panelCalendario, BorderLayout.CENTER);

        gbc.weightx = 1.0;
        gbc.weighty = 0.75;
        gbc.gridwidth = 7;
        gbc.gridx = 0;
        gbc.gridy = 0;
        
        textoMes = new JButton("default");//nome de mes
        textoMes.setForeground(modoColor.getTexto());
        textoMes.setBackground(modoColor.getFondo());
        textoMes.setFocusPainted(false);
        textoMes.setBorderPainted(false);
        panelCalendario.add(textoMes, gbc);

        gbc.gridwidth = 1;
        gbc.weighty = 0.50;
        gbc.gridy = 1;

        labelDias = new JLabel[7];

        for (byte i = 0; i < 7; i++) {//engade cada etiqueta na posición que lle corresponde
            gbc.gridx = i;

            JLabel lab = new JLabel(Dia.values()[i].getNomeCurto(),javax.swing.SwingConstants.CENTER);

            lab.setForeground(modoColor.getTextoResalte());
            lab.setBackground(modoColor.getFondo());

            labelDias[i] = lab;

            panelCalendario.add(lab, gbc);

        }

        celdasDias = new JButton[42];//celdas que conteñen os días

        gbc.weighty = 1.0;

        byte contador = 0;

        for(int i = 0; i < 6; i++ ) {

            for(int j = 0; j < 7; j++ ) {

                gbc.gridx = j;
                gbc.gridy = i + 2;

                celdasDias[contador] = new JButton();

                celdasDias[contador].setBackground(modoColor.getFondo());
                celdasDias[contador].setBorderPainted(false);
                celdasDias[contador].setFocusPainted(false);
                celdasDias[contador].setInheritsPopupMenu(true);


                panelCalendario.add(celdasDias[contador], gbc);
                contador++;

            }

        }

    }

    public void actualizarCalendario() {

        boolean primerDia = false;

        for(int i = 0; i < 42; i++ ) {

            if(!primerDia ) {

                if(celdasDias[i].getText().equals("1") ) {

                    primerDia = true;
                    celdasDias[i].setForeground(modoColor.getTexto());

                } else {

                    celdasDias[i].setForeground(modoColor.getTextoMenosImportante());

                }

            } else {

                if(celdasDias[i].getText().equals("1") ) {

                    primerDia = false;
                    celdasDias[i].setForeground(modoColor.getTextoMenosImportante());

                } else {

                    celdasDias[i].setForeground(modoColor.getTexto());

                }

            }

            celdasDias[i].setBackground(modoColor.getFondo());

        }

    }


    public void repintarComponentes() {
        
        
        panelLateral.setBackground(modoColor.getFondo());
        panelLateral.setForeground(modoColor.getTexto());
        panelContido.setBackground(modoColor.getFondo());
        cambioModoCor.setBackground(modoColor.getFondo());
        cambioModoCor.setForeground(modoColor.getTexto());

        panelCalendario.setBackground(modoColor.getFondo());
        panelCalendario.setForeground(modoColor.getTexto());

        textoMes.setForeground(modoColor.getTextoResalte());
        textoMes.setBackground(modoColor.getFondo());

        for (byte i = 0; i < 7; i++) {//engade cada etiqueta na posición que lle corresponde
            gbc.gridx = i;

            JLabel lab = labelDias[i];

            lab.setForeground(modoColor.getTextoResalte());
            lab.setBackground(modoColor.getFondo());

        }

        actualizarCalendario();
        /*
        JPanel panelContido;
        JPanel panelCalendario;
        JButton textoMes;
        JLabel textoDia;
        JButton[] celdasDias;
        JButton avanzarMes;
        JButton retrocederMes;
        JMenuItem itemPublico;
        JMenuItem itemPrivado;
        JMenuItem itemGrupal;
        */

    }
}