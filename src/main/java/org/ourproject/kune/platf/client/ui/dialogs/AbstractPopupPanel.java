package org.ourproject.kune.platf.client.ui.dialogs;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public abstract class AbstractPopupPanel {

    private PopupPanel popupPalette;
    protected Widget widget;

    private final boolean autoHide;
    private final boolean modal;

    public AbstractPopupPanel() {
        this(true, true);
    }

    public AbstractPopupPanel(final boolean autohide) {
        this(autohide, false);
    }

    public AbstractPopupPanel(final boolean autohide, final boolean modal) {
        this.autoHide = autohide;
        this.modal = modal;
    }

    public void addStyleName(final String style) {
        assert popupPalette != null;
        popupPalette.addStyleName(style);
    }

    public void hide() {
        if (popupPalette != null) {
            popupPalette.hide();
        }
    }

    public boolean isVisible() {
        if (popupPalette != null && popupPalette.isVisible()) {
            return true;
        }
        return false;
    }

    public void show(final int left, final int top) {
        popupPalette = new PopupPanel(autoHide, modal);
        popupPalette.addStyleName("k-def-popup");
        if (widget == null) {
            createWidget();
        }
        popupPalette.setWidget(widget);
        popupPalette.setPopupPositionAndShow(new PopupPanel.PositionCallback() {
            public void setPosition(final int offsetWidth, final int offsetHeight) {
                final int maxLeft = Window.getClientWidth() - offsetWidth;
                final int maxTop = Window.getClientHeight() - offsetHeight;
                popupPalette.setPopupPosition(left < maxLeft ? left : maxLeft, top < maxTop ? top : maxTop);
            }
        });
    }

    protected abstract void createWidget();

}
