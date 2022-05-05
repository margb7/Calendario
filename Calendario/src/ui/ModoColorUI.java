package ui;

import java.awt.Color;

public enum ModoColorUI {

    MODO_OSCURO(new Color(20, 54, 66), Color.WHITE, Color.PINK, Color.WHITE, Color.GRAY),
    MODO_CLARO(Color.WHITE, Color.DARK_GRAY, Color.RED, Color.BLACK, Color.GRAY);

    private Color fondo;
    private Color texto;
    private Color acento;
    private Color textoResalte;
    private Color textoMenosImportante;

    ModoColorUI(Color fondo, Color texto, Color acento, Color textoResalte, Color textoMenosImportante ) {
        this.fondo = fondo;
        this.texto = texto;
        this.acento = acento;
        this.textoResalte = textoResalte;
        this.textoMenosImportante = textoMenosImportante;
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

    /**
     * @return the textoMenosImportante
     */
    public Color getTextoMenosImportante() {
        return textoMenosImportante;
    }
    
    /**
     * @return the textoResalte
     */
    public Color getTextoResalte() {
        return textoResalte;
    }

}
