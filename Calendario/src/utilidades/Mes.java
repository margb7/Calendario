package utilidades;

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
                nome = Datos.getTraduccion("M01", "Xaneiro");
            break;
            case 2:
                nome = Datos.getTraduccion("M02", "Febreiro");
            break;
            case 3:
                nome = Datos.getTraduccion("M03", "Marzo");
            break;
            case 4:
                nome = Datos.getTraduccion("M04", "Abril");
            break;
            case 5:
                nome = Datos.getTraduccion("M05", "Maio");
            break;
            case 6:
                nome = Datos.getTraduccion("M06", "Xuño");
            break;
            case 7:
                nome = Datos.getTraduccion("M07", "Xullo");
            break;
            case 8:
                nome = Datos.getTraduccion("M08", "Agosto");
            break;
            case 9:
                nome = Datos.getTraduccion("M09", "Setembro");
            break;
            case 10:
                nome = Datos.getTraduccion("M10", "Outubro");
            break;
            case 11:
                nome = Datos.getTraduccion("M11", "Novembro");
            break;
            default:
                nome = Datos.getTraduccion("M12", "Decembro");
            break;

        }

        return nome;
    }

    @Override
    public String toString() {
        return getNome();
    }

}
