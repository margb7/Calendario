package ui;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import model.Datos;
import utilidades.Dia;
import utilidades.Mes;

import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.GridBagConstraints;

public class SeleccionDataUI extends ElementoUI {
    
    private JDialog seleccionData;
    private JSpinner anos;
    private SpinnerNumberModel model;
    private JComboBox<String> meses;
    private JLabel de;
    private JButton ok;

    private SeleccionDataUI() {}

    public JDialog getSeleccionData() {
        return seleccionData;
    }

    public JButton getOk() {
        return ok;
    }

    public void iniciarComponentes(JFrame owner, int anoActual) {

        String[] lista = Mes.getListaMeses();

        seleccionData = new JDialog(owner, Datos.getTraduccion("S01", "Selecciona unha data"), true);
        seleccionData.setLayout(new GridBagLayout());
        seleccionData.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        seleccionData.setSize(300, 100);

        meses = new JComboBox<>(lista);
        model = new SpinnerNumberModel(anoActual, 1980, anoActual + 10, 1);
        anos = new JSpinner(model);
        de = new JLabel("de");
        ok = new JButton("OK");

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 5);
        seleccionData.add(meses, gbc);        

        gbc.gridx = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        de.setLabelFor(anos);
        seleccionData.add(de, gbc);

        gbc.gridx = 2;
        seleccionData.add(anos, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(10, 0, 0, 0);
        seleccionData.add(ok, gbc);

    }

    public int getValorAnos() {
        return (int)anos.getValue();
    }

    public int getValorMes() {
        return meses.getSelectedIndex() + 1;
    }

    @Override
    public void mostrarUI() {
         
    }

    @Override
    public void iniciarComponentes() {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void repintarComponentes() {
        // TODO Auto-generated method stub
        
    }

}
