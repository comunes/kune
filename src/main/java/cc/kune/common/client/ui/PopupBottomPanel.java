package cc.kune.common.client.ui;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class PopupBottomPanel extends AbstractAtBorderPopupPanel {
    public PopupBottomPanel() {
        this(false, false);
    }

    public PopupBottomPanel(final boolean autohide) {
        this(autohide, false);
    }

    public PopupBottomPanel(final boolean autohide, final boolean modal) {
        super(autohide, modal);
        defaultStyleImpl();
    }

    @Override
    public void defaultStyle() {
        defaultStyleImpl();
    }

    private void defaultStyleImpl() {
        setStyleName("k-popup-bottom-centered");
        super.defaultStyle();
        addStyleName("k-top-10corners");
    }

    @Override
    protected void setCenterPositionImpl() {
        setPopupPositionAndShow(new PopupPanel.PositionCallback() {
            @Override
            public void setPosition(final int offsetWidth, final int offsetHeight) {
                final Widget widget = getWidget();
                final int x = (Window.getClientWidth() - (widget != null ? getWidget().getOffsetWidth() : 0)) / 2;
                final int y = Window.getClientHeight() - (widget != null ? getWidget().getOffsetHeight() : 0);
                PopupBottomPanel.this.setPopupPosition(x, y);
            }
        });
    }

}
