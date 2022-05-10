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

    private static JList<Evento> listaEventos;
    private static JFrame frame;
    private static GridBagConstraints gbc;
    private static JPanel panelLateral;
    private static JPanel panelContido;
    private static JPanel panelCalendario;
    private static JButton textoMes;
    private static JLabel textoDia;
    private static JButton[] celdasDias;
    private static JButton avanzarMes;
    private static JButton retrocederMes;
    private static JMenuItem itemPublico;
    private static JMenuItem itemPrivado;
    private static JMenuItem itemGrupal;
    private static JButton cambioModoCor;

    /**
     * Constructor privado para evitar instancias
     */
    private CalendarioUI() {}

    /**
     * @return the avanzarMes
     */
    public static JButton getAvanzarMes() {
        return avanzarMes;
    }

    /**
     * @return the retrocederMes
     */
    public static JButton getRetrocederMes() {
        return retrocederMes;
    }

    public static JList<Evento> getListaEventos() {
        return listaEventos;
    }

    /**
     * @return the celdasDias
     */
    public static JButton[] getCeldasDias() {
        return celdasDias;
    }

    /**
     * @return the textoMes
     */
    public static JButton getTextoMes() {
        return textoMes;
    }

    public static JLabel getTextoDia() {
        return textoDia;
    }

    public static JFrame getFrame() {
        return frame;
    }

    public static JMenuItem getItemGrupal() {
        return itemGrupal;
    }

    public static JMenuItem getItemPrivado() {
        return itemPrivado;
    }

    public static JMenuItem getItemPublico() {
        return itemPublico;
    }

    public static JButton getCambioModoCor() {
        return cambioModoCor;
    }
    
    public static void mostrarUI() {
        
        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                Toolkit tool = Toolkit.getDefaultToolkit();

                actualizarCalendario();
                frame.setVisible(true);
                frame.setLocation((tool.getScreenSize().width - frame.getWidth()) / 2, (tool.getScreenSize().height - frame.getHeight() ) / 2 );
            }

        });

    }

    public static void init() {

        frame = new JFrame("Calendario");//Ventá da aplicación

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
        
        cambioModoCor = new JButton("Cambiar tema de cor");
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
        panelCalendario.setComponentPopupMenu(new JPopupMenu("Crear evento"));
        itemPublico = new JMenuItem("Público");
        panelCalendario.getComponentPopupMenu().add(itemPublico);
        itemGrupal = new JMenuItem("Grupal");
        panelCalendario.getComponentPopupMenu().add(itemGrupal);
        itemPrivado = new JMenuItem("Privado");
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

        for (byte i = 0; i < 7; i++) {//engade cada etiqueta na posición que lle corresponde
            gbc.gridx = i;

            JLabel lab = new JLabel(Dia.values()[i].getNomeCurto(),javax.swing.SwingConstants.CENTER);

            lab.setForeground(modoColor.getTextoResalte());

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

    public static void actualizarCalendario() {

        boolean primerDia = false;

        for(int i = 0; i < 42; i++ ) {

            if(!primerDia ) {

                if(celdasDias[i].getText().equals("1") ) {

                    primerDia = true;
                    celdasDias[i].setForeground(modoColor.getTextoResalte());

                } else {

                    celdasDias[i].setForeground(modoColor.getTextoMenosImportante());

                }

            } else {

                if(celdasDias[i].getText().equals("1") ) {

                    primerDia = false;
                    celdasDias[i].setForeground(modoColor.getTextoMenosImportante());

                } else {

                    celdasDias[i].setForeground(modoColor.getTextoResalte());

                }

            }

        }

    }


    public static void repintarComponentes() {
        
        
        panelLateral.setBackground(modoColor.getFondo());
        panelLateral.setForeground(modoColor.getTexto());
        panelContido.setBackground(modoColor.getFondo());
        cambioModoCor.setBackground(modoColor.getFondo());
        cambioModoCor.setForeground(modoColor.getTexto());
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