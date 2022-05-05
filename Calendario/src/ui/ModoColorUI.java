package ui;

import java.awt.Color;

public enum ModoColorUI {

    MODO_OSCURO(new Color(20, 54, 66), Color.WHITE, Color.PINK),
    MODO_CLARO(Color.WHITE, Color.BLACK, Color.RED);

    private Color fondo;
    private Color texto;
    private Color acento;

    ModoColorUI(Color fondo, Color texto, Color acento ) {
        this.fondo = fondo;
        this.texto = texto;
        this.acento = acento;
    }

    /**
     * @return the acento
     */
    public Color getAcento() {
        return acento;
    }

    /**
     * @return the fondo
     */
    public Color getFondo() {
        return fondo;
    }
    
    /**
     * @return the texto
     */
    public Color getTexto() {
        return texto;
    }

}
