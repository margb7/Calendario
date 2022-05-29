package ui;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.border.Border;

/**
 * Clase para xuntar o comportamento dos elementos da interface gráfica
 */
public abstract class ElementoUI {
    
    protected static ModoColorUI modoColor;

    /**
     * Establece o modo de color da aplicación
     * @param modoColor o novo modo de color a aplicar 
     */
    public static final void setModoColor(ModoColorUI modoColor) {
        ElementoUI.modoColor = modoColor;
    }

    /**
     * Repinta os elementos da interface para que conteñan as cores do tema de cor establecido
     */
    abstract void repintarComponentes();

    /**
     * Mostra a interface deste elemento
     */
    abstract void mostrarUI();

    /**
     * Actualiza as traduccions ao idioma establecido
     */
    public void actualizarTraduccions() {};

}


class BordeRedondo implements Border {

    private int r;

    BordeRedondo(int r) {
        this.r = r;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        
        g.drawRoundRect(x, y, width - 1, height - 1, r, r);
        
    }

    @Override
    public Insets getBorderInsets(Component c) {
        
        return new Insets(this.r + 5, this.r + 10, this.r + 5, this.r + 10);

    }

    @Override
    public boolean isBorderOpaque() {
        
        return true;

    }
    
}