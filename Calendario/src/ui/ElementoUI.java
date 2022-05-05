package ui;

public abstract class ElementoUI {
    
    protected static ModoColorUI modoColor;

    /**
     * @return the modoColor
     */
    public static ModoColorUI getModoColor() {
        return modoColor;
    }

    /**
     * @param modoColor the modoColor to set
     */
    public static void setModoColor(ModoColorUI modoColor) {
        ElementoUI.modoColor = modoColor;
    }

}
