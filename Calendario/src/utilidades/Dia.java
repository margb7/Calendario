package utilidades;

import model.Datos;

public enum Dia {
    
    LUNS(1),
    MARTES(2),
    MERCORES(3),
    XOVES(4),
    VENRES(5),
    SABADO(6),
    DOMINGO(7);

    private String nome;
    private String nomeCurto;

    Dia(int num) {

        switch(num) {

            case 1:
                nome = Datos.getTraduccion("D01", "Luns");
                nomeCurto = Datos.getTraduccion("D02", "Lu.");
            break;
            case 2:
                nome = Datos.getTraduccion("D03", "Martes");
                nomeCurto = Datos.getTraduccion("D04", "Ma.");
            break;
            case 3:
                nome = Datos.getTraduccion("D05", "Mércores");
                nomeCurto = Datos.getTraduccion("D06", "Mé.");
            break;
            case 4:
                nome = Datos.getTraduccion("D07", "Xoves");
                nomeCurto = Datos.getTraduccion("D08", "Xo.");
            break;
            case 5:
                nome = Datos.getTraduccion("D09", "Venres");
                nomeCurto = Datos.getTraduccion("D10", "Ve.");
            break;
            case 6:
                nome = Datos.getTraduccion("D11", "Sábado");
                nomeCurto = Datos.getTraduccion("D12", "Sá.");
            break;
            default:
                nome = Datos.getTraduccion("D13", "Domingo");
                nomeCurto = Datos.getTraduccion("D14", "Do.");
            break;

        }

    }

    public static String[] getListaDias() {

        String[] out = new String[values().length];

        for(int i = 0; i < values().length; i++ ) {

            out[i] = values()[i].getNome();

        }

        return out;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @return the nomeCorto
     */
    public String getNomeCurto() {
        return nomeCurto;
    }

    @Override
    public String toString() {
        return nome;
    }

}
