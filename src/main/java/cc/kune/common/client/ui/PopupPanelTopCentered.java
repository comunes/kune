package cc.kune.common.client.ui;

import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.PopupPanel;

public class PopupPanelTopCentered extends PopupPanel {
    public PopupPanelTopCentered() {
        super();
        init();
    }

    public PopupPanelTopCentered(final boolean autohide) {
        super(autohide);
        init();
    }

    public PopupPanelTopCentered(final boolean autohide, final boolean modal) {
        super(autohide, modal);
        init();
    }

    private void init() {
        setStyleName("k-popup-top-centered");
        addStyleName("k-opacity90");
        addStyleName("k-box-10shadow");
        addStyleName("k-bottom-10corners");
        Window.addResizeHandler(new ResizeHandler() {
            @Override
            public void onResize(final ResizeEvent event) {
                setCenterPositionImpl();
            }
        });
    }

    public void setCenterPosition() {
        setCenterPositionImpl();
    }

    private void setCenterPositionImpl() {
        setPopupPositionAndShow(new PopupPanel.PositionCallback() {
            @Override
            public void setPosition(final int offsetWidth, final int offsetHeight) {
                final int x = (Window.getClientWidth() - (getWidget() != null ? getWidget().getOffsetWidth() : 0)) / 2;
                final int y = 0;
                PopupPanelTopCentered.this.setPopupPosition(x, y);
            }
        });
    }

    public void showCentered() {
        setCenterPositionImpl();
    }
}
