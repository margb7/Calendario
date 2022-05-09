package ui;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import utilidades.Mes;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

public class SeleccionDataUI extends ElementoUI {
    
    private static JDialog seleccionData;
    private static JSpinner anos;
    private static SpinnerNumberModel model;
    private static JComboBox<String> meses;
    private static JLabel de;
    private static JButton ok;

    private SeleccionDataUI() {}

    public static JDialog getSeleccionData() {
        return seleccionData;
    }

    public static JButton getOk() {
        return ok;
    }

    public static void initSeleccionData(JFrame owner, int anoActual) {

        String[] lista = {"Xaneiro", "Febreiro", "Marzo", "Abril", "Maio", "Xuño", "Xullo", "Agosto", "Setembro", "Outubro", "Novembro", "Decembro"};

        seleccionData = new JDialog(owner, "title", true);
        seleccionData.setLayout(new GridBagLayout());
        seleccionData.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        meses = new JComboBox<>(lista);

        model = new SpinnerNumberModel(anoActual, 1980, anoActual + 10, 1);

        anos = new JSpinner(model);

        de = new JLabel("de");

        ok = new JButton("OK");

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        seleccionData.add(meses, gbc);        

        gbc.gridx = 1;
        de.setLabelFor(anos);
        seleccionData.add(de, gbc);
        seleccionData.add(anos, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        seleccionData.add(ok, gbc);

    }

    public static int getValorAnos() {
        return (int)anos.getValue();
    }

    public static int getValorMes() {
        return meses.getSelectedIndex() + 1;
    }

}
