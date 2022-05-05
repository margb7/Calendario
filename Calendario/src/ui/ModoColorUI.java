package ui;

import java.awt.Color;

public enum ModoColorUI {

    MODO_OSCURO(new Color(20, 54, 66), Color.WHITE, Color.PINK, Color.WHITE, Color.GRAY, new Color(155,200,155), new Color(155, 200, 155)),
    MODO_CLARO(Color.WHITE, Color.DARK_GRAY, Color.RED, Color.BLACK, Color.GRAY, new Color(155,200,155), new Color(155, 200, 155));

    private Color fondo;
    private Color texto;
    private Color acento;
    private Color textoResalte;
    private Color textoMenosImportante;
    private Color bordes;
    private Color separador;

    ModoColorUI(Color fondo, Color texto, Color acento, Color textoResalte, Color textoMenosImportante, Color bordes, Color separador ) {
        this.fondo = fondo;
        this.texto = texto;
        this.acento = acento;
        this.textoResalte = textoResalte;
        this.textoMenosImportante = textoMenosImportante;
        this.bordes = bordes;
        this.separador = separador;
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

    public Color getBordes() {
        return bordes;
    }

    public Color getSeparador() {
        return separador;
    }

}
