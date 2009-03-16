package org.ourproject.kune.platf.client.ui.palette;

import com.calclab.suco.client.events.Listener;

public abstract class AbstractPalettePresenter {

    protected Listener<String> onColorSelected;
    private AbstractPaletteView view;

    public void hide() {
        this.view.hide();
    }

    public void init(final AbstractPaletteView view) {
        this.view = view;
    }

    public void show(final int left, final int top, final Listener<String> onColorSelected) {
        view.show(left, top);
        this.onColorSelected = onColorSelected;
    }

    protected void onColorSelected(final String color) {
        onColorSelected.onEvent(color);
        view.hide();
    }

}
