package ui;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import calendario.Calendario;
import utilidades.Mes;

import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.awt.Color;
import java.awt.GridBagConstraints;

import java.time.LocalDate;

public class SeleccionDataUI extends ElementoUI {
    
    private JDialog seleccionData;
    private JSpinner anos;
    private JComboBox<String> meses;
    private JLabel de;
    private JButton ok;

    public SeleccionDataUI() {

        initSeleccionData();
        iniciarListeners();

    }

    public JDialog getSeleccionData() {
        return seleccionData;
    }

    public JButton getOk() {
        return ok;
    }

    /**
     * @return the de
     */
    public JLabel getDe() {
        return de;
    }

    /**
     * @return the anos
     */
    public JSpinner getAnos() {
        return anos;
    }

    /**
     * @return the meses
     */
    public JComboBox<String> getMeses() {
        return meses;
    }

    public void initSeleccionData() {

        seleccionData = new JDialog();
        seleccionData.setLayout(new GridBagLayout());
        seleccionData.setUndecorated(true);
        seleccionData.getRootPane().setBorder(new javax.swing.border.LineBorder(Color.GRAY, 1));
        seleccionData.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        seleccionData.setSize(300, 100);
        seleccionData.setLocation(seleccionData.getParent().getX(), seleccionData.getParent().getY());

        meses = new JComboBox<>();
        anos = new JSpinner();
        de = new JLabel();
        ok = new JButton();

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

    private void iniciarListeners() {

        String[] lista = Mes.getListaMeses();

        anos.setModel(new SpinnerNumberModel( Calendario.getDataCalendario().getYear(), 1980, Calendario.getDataCalendario().getYear() + 10, 1));

        for(int i = 0; i < lista.length; i++ ){

            meses.addItem(lista[i]);

        }

        meses.setSelectedIndex(Calendario.getDataCalendario().getMonthValue() - 1);

        ok.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                Calendario.setDataCalendario(LocalDate.of((int)anos.getValue(), (int) meses.getSelectedIndex() + 1, 1));
                seleccionData.dispose();
                
            }
                    
        });

    }

    @Override
    public void actualizarTraduccions() {

        seleccionData.setTitle(Calendario.getTraduccion("S01", "Selecciona unha data"));
        de.setText(Calendario.getTraduccion("S02", "de"));
        ok.setText(Calendario.getTraduccion("S03", "Ok"));
    
    }
    
    @Override
    public void mostrarUI() {

        actualizarTraduccions();
        repintarComponentes();
        
        seleccionData.setVisible(true);
        seleccionData.setAlwaysOnTop(true);
        seleccionData.setSize(300, 100);

        seleccionData.setLocationRelativeTo(null);

    }

    public void mostrarUI(JFrame frame ) {

        seleccionData.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosed(WindowEvent e) {
                
                frame.setEnabled(true);
                
            }

            @Override
            public void windowOpened(WindowEvent e) {
                
                frame.setEnabled(false);

            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                
                seleccionData.dispose();

            }

        });

        int x,y;

        actualizarTraduccions();
        repintarComponentes();

        seleccionData.setVisible(true);
        seleccionData.setAlwaysOnTop(true);
        seleccionData.setSize(300, 100);

        x = frame.getX() + (frame.getWidth() / 2) - (seleccionData.getWidth() / 2);
        y = frame.getY() + (frame.getHeight() / 2) - (seleccionData.getHeight() / 2);

        seleccionData.setLocation(x, y);

    }

    @Override
    void repintarComponentes() {
        
        seleccionData.getContentPane().setBackground(modoColor.getFondo());

    }

}
