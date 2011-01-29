package cc.kune.common.client.ui;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.PopupPanel;

public class PopupTopPanel extends AbstractAtBorderPopupPanel {
    public PopupTopPanel() {
        this(false, false);
    }

    public PopupTopPanel(final boolean autohide) {
        this(autohide, false);
    }

    public PopupTopPanel(final boolean autohide, final boolean modal) {
        super(autohide, modal);
        defaultStyleImpl();
    }

    @Override
    public void defaultStyle() {
        defaultStyleImpl();
    }

    private void defaultStyleImpl() {
        setStyleName("k-popup-top-centered");
        super.defaultStyle();
        addStyleName("k-bottom-10corners");
    }

    @Override
    protected void setCenterPositionImpl() {
        setPopupPositionAndShow(new PopupPanel.PositionCallback() {
            @Override
            public void setPosition(final int offsetWidth, final int offsetHeight) {
                final int x = (Window.getClientWidth() - (getWidget() != null ? getWidget().getOffsetWidth() : 0)) / 2;
                final int y = 0;
                PopupTopPanel.this.setPopupPosition(x, y);
            }
        });
    }

}
