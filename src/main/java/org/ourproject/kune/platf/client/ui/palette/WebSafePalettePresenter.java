package org.ourproject.kune.platf.client.ui.palette;

import java.util.ArrayList;
import java.util.Iterator;

public class WebSafePalettePresenter {

    private final ArrayList colorSelectListeners;

    public WebSafePalettePresenter() {
        colorSelectListeners = new ArrayList();
    }

    public void addColorSelectListener(final ColorSelectListener listener) {
        colorSelectListeners.add(listener);
    }

    protected void fireColorSelectListeners(final String color) {
        for (Iterator it = colorSelectListeners.iterator(); it.hasNext();) {
            ((ColorSelectListener) it.next()).onColorSelected(color);
        }
    }
    public void onColorSelected(final int row, final int col) {
        String color = getColor(row, col);
        fireColorSelectListeners(color);
    }

    private String getColor(final int row, final int col) {
        String color = null;
        int pd = (row * WebSafePaletteView.COLS + col);
        int da = (pd) / 6;
        int ra = (pd) % 6;
        int aa = (da - ra / 6);
        int db = (aa) / 6;
        int rb = (aa) % 6;
        int rc = (db - rb / 6) % 6;
        color = "rgb(" + ra * 51 + ", " + rc * 51 + ", " + rb * 51 + ")";
        return color;
    }

    public void reset() {
        colorSelectListeners.clear();
    }

}
