package ui;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.border.Border;

public abstract class ElementoUI {
    
    protected static ModoColorUI modoColor;

    /**
     * @return the modoColor
     */
    public static final ModoColorUI getModoColor() {
        return modoColor;
    }

    /**
     * @param modoColor the modoColor to set
     */
    public static final void setModoColor(ModoColorUI modoColor) {
        ElementoUI.modoColor = modoColor;
    }

    protected void repintarComponentes() {}
    public void actualizarTraduccions() {};
    abstract void mostrarUI();

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