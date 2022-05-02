package proba;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.GridBagLayout;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.awt.GridBagConstraints;
import java.awt.BorderLayout;
import java.awt.Color;

class Proba {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                init();
            }
        });
    }

    public static void init() {
        JFrame frame = new JFrame("Calendario");//Ventá da aplicación
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1250, 650);

        frame.setLayout(new GridBagLayout());//layout manager para organizar elementos en cuadrícula
        GridBagConstraints gbc = new GridBagConstraints();//parámetros para o layout manager

        gbc.weightx = 0.5;
        gbc.weighty = 1.0;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        JPanel panelLateral = new JPanel();//falta engadir espazos para os textos de información de eventos etc.
        panelLateral.setOpaque(true);
        panelLateral.setBackground(new Color(255, 0, 0));//esta todo coloreado con corres horribles para distinguir os elementos da interface
        frame.add(panelLateral, gbc);

        gbc.weightx = 1.0;
        gbc.gridx = 1;
        JPanel panelContido = new JPanel(new BorderLayout());
        panelContido.setOpaque(true);
        panelContido.setBackground(new Color(0, 255, 0));
        frame.add(panelContido, gbc);

        JPanel panelCalendario = new JPanel(new GridBagLayout());
        panelCalendario.setOpaque(true);
        panelCalendario.setBackground(new Color(0, 0, 255));

        panelContido.add(panelCalendario, BorderLayout.CENTER);

        JLabel[] etiquetas = new JLabel[7];//etiquetas cos nomes dos días
        etiquetas[0] = new JLabel("lu.", javax.swing.SwingConstants.CENTER);
        etiquetas[1] = new JLabel("ma.", javax.swing.SwingConstants.CENTER);
        etiquetas[2] = new JLabel("mé.", javax.swing.SwingConstants.CENTER);
        etiquetas[3] = new JLabel("xo.", javax.swing.SwingConstants.CENTER);
        etiquetas[4] = new JLabel("ve.", javax.swing.SwingConstants.CENTER);
        etiquetas[5] = new JLabel("sá.", javax.swing.SwingConstants.CENTER);
        etiquetas[6] = new JLabel("do.", javax.swing.SwingConstants.CENTER);
        for (JLabel j : etiquetas) {
            j.setForeground(new Color(255, 255, 255));
        }

        gbc.weightx = 1.0;
        gbc.weighty = 0.75;
        gbc.gridwidth = 7;
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel mes = new JLabel("Abril, 2022", javax.swing.SwingConstants.CENTER);//nome de mes,,, aquí é un literal, pero debería ser variable
        mes.setOpaque(true);
        mes.setBackground(new Color(0, 255, 255));
        panelCalendario.add(mes, gbc);

        gbc.gridwidth = 1;
        gbc.weighty = 0.50;
        gbc.gridy = 1;
        for (byte i = 0; i < 7; i++) {//engade cada etiqueta na posición que lle corresponde
            gbc.gridx = i;
            panelCalendario.add(etiquetas[i], gbc);
        }

        LocalDate data = LocalDate.of(2022, 4, 1);
        while (data.getDayOfWeek() != DayOfWeek.MONDAY) {//atopa o luns máis cercano para comezar a encher a cuadrícula
            data = data.minusDays(1);
        }

        gbc.weighty = 1.0;
        JButton[] dias = new JButton[42];//celdas que conteñen os días
        byte contador = 0;
        Color gris = new Color(185, 185, 185);
        for (byte i = 0; i < 6; i++) {//engade e da valor ás celdas dos días
            for (byte j = 0; j < 7; j++) {
                gbc.gridx = j;
                gbc.gridy = i + 2;
                dias[contador] = new JButton(String.valueOf(data.getDayOfMonth()));
                if (data.getMonthValue() != 4) {//cor distinta para os días de outros meses,,, aquí é unha constante, pero debería ser variable
                    dias[contador].setForeground(gris);
                }
                panelCalendario.add(dias[contador], gbc);
                contador++;
                data = data.plusDays(1);
            }
        }

        frame.setVisible(true);
    }
}