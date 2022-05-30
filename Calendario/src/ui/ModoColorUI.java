package ui;

import java.awt.Color;

/**
 * Enumeración que contén os temas de cor da aplicación
 */
public enum ModoColorUI {

    MODO_OSCURO(new Color(45, 47, 76), new Color(239, 215, 157), new Color(239, 215, 157), new Color(240, 163, 157), new Color(153, 139, 150), new Color(233,224,207), new Color(34, 34, 58)),
    MODO_CLARO(new Color(233, 224, 207), new Color(77, 50, 71), new Color(77, 50, 71), new Color(211, 45, 47), new Color(239, 150, 70), new Color(211, 45, 47), new Color(249, 189, 85));

    private Color fondo;
    private Color texto;
    private Color acento;
    private Color textoResalte;
    private Color textoMenosImportante;
    private Color bordes;
    private Color separador;

    /**
     * Constructor da enumeración dos modos de color
     * @param fondo a cor de fondo
     * @param texto a cor do texto
     * @param acento a cor de acento
     * @param textoResalte a cor de texto resaltado
     * @param textoMenosImportante a cor de texto menos importante
     * @param bordes a cor dos bordes
     * @param separador a cor dos separadores
     */
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
     * Devolve a cor de acento
     * @return un <code>Color</code> co acento
     */
    public Color getAcento() {
        return acento;
    }

    /**
     * Devolve a cor de fondo
     * @return un <code>Color</code> coa cor de fondo
     */
    public Color getFondo() {
        return fondo;
    }
    
    /**
     * Devolve a cor de texto
     * @return un <code>Color</code> coa cor de texto
     */
    public Color getTexto() {
        return texto;
    }

    /**
     * Devolve a cor de texto menos importante
     * @return un <code>Color</code> coa cor dun texto menos importante
     */
    public Color getTextoMenosImportante() {
        return textoMenosImportante;
    }
    
    /**
     * Devolve a cor de texto resaltado
     * @return un <code>Color</code> coa cor de texto resaltado
     */
    public Color getTextoResalte() {
        return textoResalte;
    }

    /**
     * Devolve a cor dos bordes
     * @return un <code>Color</code> coa cor dos bordes
     */
    public Color getBordes() {
        return bordes;
    }

    /**
     * Devolve a cor de separadores
     * @return un <code>Color</code> coa cor de separadores
     */
    public Color getSeparador() {
        return separador;
    }

}
