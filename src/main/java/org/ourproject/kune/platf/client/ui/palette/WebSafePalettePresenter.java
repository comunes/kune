package org.ourproject.kune.platf.client.ui.palette;

import java.util.ArrayList;
import java.util.Iterator;

public class WebSafePalettePresenter {

    private WebSafePaletteView view;

    private final ArrayList colorSelectListners;

    public WebSafePalettePresenter() {
        colorSelectListners = new ArrayList();
    }

    public void init(final WebSafePaletteView view) {
        this.view = view;
    }

    public void addColorSelectListener(final ColorSelectListener listener) {
        colorSelectListners.add(listener);
    }

    protected void fireColorSelectListeners(final String color) {
        for (Iterator it = colorSelectListners.iterator(); it.hasNext();) {
            ((ColorSelectListener) it.next()).onColorSelected(color);
        }
    }
    public void onColorSelected(final int row, final int col) {
        String color = getColor(row, col);
        view.hide();
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

}
