package utilidades;

import calendario.Calendario;

/**
 * Clase para xestionar os nomes dos días usados no calendario
 */
public enum Dia {
    
    LUNS(1),
    MARTES(2),
    MERCORES(3),
    XOVES(4),
    VENRES(5),
    SABADO(6),
    DOMINGO(7);

    private int num;

    /**
     * Constructor da enumeración de días. Encárgase de mapear cada día da semana co seu nome
     * independentemente do idioma.
     * @param num o número do día que representa.
     */
    Dia(int num) {

        this.num = num;

    }

    /**
     * Devolve unha lista con todos os valores dos nomes de días da semana.
     * @return un array de <code>String</code> cos valores.
     */
    public static String[] getListaDias() {

        String[] out = new String[values().length];

        for(int i = 0; i < values().length; i++ ) {

            out[i] = values()[i].getNome();

        }

        return out;
    }

    /**
     * Devolve o nome do día da semana no idioma configurado.
     * @return o día da semana que representa
     */
    public String getNome() {

        String nome;
        
        switch(num) {

            case 1:
                nome = Calendario.getTraduccion("D01", "Luns");
            break;
            case 2:
                nome = Calendario.getTraduccion("D03", "Martes");
            break;
            case 3:
                nome = Calendario.getTraduccion("D05", "Mércores");
            break;
            case 4:
                nome = Calendario.getTraduccion("D07", "Xoves");
            break;
            case 5:
                nome = Calendario.getTraduccion("D09", "Venres");
            break;
            case 6:
                nome = Calendario.getTraduccion("D11", "Sábado");
            break;
            default:
                nome = Calendario.getTraduccion("D13", "Domingo");
            break;

        }

        return nome;
    }

    /**
     * Devolve o nome curto do día (por exemplo "Lu." para o Luns).
     * @return o nome curto do día.
     */
    public String getNomeCurto() {

        String nomeCurto;

        switch(num) {

            case 1:
                nomeCurto = Calendario.getTraduccion("D02", "Lu.");
            break;
            case 2:
                nomeCurto = Calendario.getTraduccion("D04", "Ma.");
            break;
            case 3:
                nomeCurto = Calendario.getTraduccion("D06", "Mé.");
            break;
            case 4:
                nomeCurto = Calendario.getTraduccion("D08", "Xo.");
            break;
            case 5:
                nomeCurto = Calendario.getTraduccion("D10", "Ve.");
            break;
            case 6:
                nomeCurto = Calendario.getTraduccion("D12", "Sá.");
            break;
            default:
                nomeCurto = Calendario.getTraduccion("D14", "Do.");
            break;

        }

        return nomeCurto;
    }

    /**
     * Devolve o nome do día que representa.
     */
    @Override
    public String toString() {
        return getNome();
    }

}
