package utilidades;

import calendario.Calendario;
import model.Datos;

public enum Mes {
    
    XANEIRO(1),
    FEBREIRO(2),
    MARZO(3),
    ABRIL(4),
    MAIO(5),
    XUNIO(6),
    XULIO(7),
    AGOSTO(8),
    SETEMBRO(9),
    OUTUBRO(10),
    NOVEMBRO(11),
    DECEMBRO(12);

    private int num;

    Mes(int num ) {

        this.num = num;

    }

    public static String[] getListaMeses() {

        String[] out = new String[values().length];

        for(int i = 0; i < values().length; i++ ) {

            out[i] = values()[i].getNome();

        }

        return out;

    }

    public String getNome() {

        String nome;

        switch(num) {

            case 1:
                nome = Calendario.getTraduccion("M01", "Xaneiro");
            break;
            case 2:
                nome = Calendario.getTraduccion("M02", "Febreiro");
            break;
            case 3:
                nome = Calendario.getTraduccion("M03", "Marzo");
            break;
            case 4:
                nome = Calendario.getTraduccion("M04", "Abril");
            break;
            case 5:
                nome = Calendario.getTraduccion("M05", "Maio");
            break;
            case 6:
                nome = Calendario.getTraduccion("M06", "Xuño");
            break;
            case 7:
                nome = Calendario.getTraduccion("M07", "Xullo");
            break;
            case 8:
                nome = Calendario.getTraduccion("M08", "Agosto");
            break;
            case 9:
                nome = Calendario.getTraduccion("M09", "Setembro");
            break;
            case 10:
                nome = Calendario.getTraduccion("M10", "Outubro");
            break;
            case 11:
                nome = Calendario.getTraduccion("M11", "Novembro");
            break;
            default:
                nome = Calendario.getTraduccion("M12", "Decembro");
            break;

        }

        return nome;
    }

    @Override
    public String toString() {
        return getNome();
    }

}
