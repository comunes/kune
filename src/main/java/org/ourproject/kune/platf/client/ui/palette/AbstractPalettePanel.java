package org.ourproject.kune.platf.client.ui.palette;

import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public abstract class AbstractPalettePanel {
    private PopupPanel popupPalette;
    Widget widget;

    public void hide() {
        if (popupPalette != null) {
            popupPalette.hide();
        }
    }

    public void show(final int left, final int top) {
        if (widget == null) {
            createPalette();
        }
        popupPalette = new PopupPanel(true, true);
        popupPalette.addStyleName("kune-WebSafePalette-popup");
        popupPalette.setVisible(false);
        popupPalette.show();
        popupPalette.setPopupPosition(left, top);
        popupPalette.setWidget(widget);
        popupPalette.setVisible(true);
    }

    protected void createPalette() {
    }

}
