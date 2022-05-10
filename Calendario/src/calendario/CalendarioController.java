package calendario;

import ui.CalendarioUI;
import ui.ElementoUI;
import ui.LoginUI;
import ui.ModoColorUI;
import utilidades.Funciones;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JButton;

import excepcions.UsuarioNonAtopadoException;
import excepcions.UsuarioXaRexistradoException;
import model.Datos;
import model.Usuario;

public class CalendarioController implements Controller<CalendarioUI> {
    
    @Override
    public CalendarioUI instanciarElemento() {
        
        CalendarioUI out = new CalendarioUI();

        crearListeners(out);

        return out;
    }

    @Override
    public void crearListeners(CalendarioUI instancia) {
        

        JButton[] celdasDias = instancia.getCeldasDias();

        Calendario.setDataCalendario(LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);
        primerDiaMes = dataCalendario;

        // Botón (">") na dereita para avanzar o mes
        CalendarioUI.getAvanzarMes().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                dataCalendario = dataCalendario.withDayOfMonth(1);

                actualizarCalendario();

            }

        });


        // Botón ("<") na esquerda para retroceder o mes
        CalendarioUI.getRetrocederMes().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                dataCalendario = dataCalendario.withDayOfMonth(1).minusMonths(2);

                actualizarCalendario();
                
            }

        });

        for(int i = 0; i < celdasDias.length; i++ ) {

            celdasDias[i].addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent e) {

                    // Engadir para cada dia do mes a capacidade de mostrar os eventos no panel lateral
                    JButton boton = (JButton) e.getSource();

                    // Uso do nome do botón para obter o día que representa
                    LocalDate dataDia = LocalDate.parse(boton.getName());
                    Evento[] listaEventos = Datos.getEventosDia(dataDia, usuario);
                    String textoDia = Dia.values()[dataDia.getDayOfWeek().ordinal()].getNome() + " " + dataDia.getDayOfMonth() + " de " + Mes.values()[dataDia.getMonthValue() - 1].getNome();

                    CalendarioUI.getTextoDia().setText(textoDia);
                    
                    // Vaciar a lista para que non conteña eventos que non corresponden 
                    CalendarioUI.getListaEventos().setListData(new Evento[0]);

                    if(listaEventos.length != 0 ) {

                        CalendarioUI.getListaEventos().setListData(listaEventos);

                    }

                    // Se o mes do día seleccionado non coincide co mes do calendario -> pásase a mostrar ese mes
                    if(dataDia.getMonthValue() != primerDiaMes.getMonthValue() ) {

                        dataCalendario = LocalDate.of(dataDia.getYear(), dataDia.getMonthValue(), 1);
                        actualizarCalendario();

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

    }

}
