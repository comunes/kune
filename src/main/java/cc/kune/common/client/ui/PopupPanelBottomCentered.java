package cc.kune.common.client.ui;

import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class PopupPanelBottomCentered extends PopupPanel {

    public PopupPanelBottomCentered() {
        super();
        init();
    }

    public PopupPanelBottomCentered(final boolean autohide) {
        super(autohide);
        init();
    }

    public PopupPanelBottomCentered(final boolean autohide, final boolean modal) {
        super(autohide, modal);
        init();
    }

    private void init() {
        setStyleName("k-popup-bottom-centered");
        addStyleName("k-opacity90");
        addStyleName("k-box-10shadow");
        addStyleName("k-top-10corners");
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
                final Widget widget = getWidget();
                final int x = (Window.getClientWidth() - (widget != null ? getWidget().getOffsetWidth() : 0)) / 2;
                final int y = Window.getClientHeight() - (widget != null ? getWidget().getOffsetHeight() : 0);
                PopupPanelBottomCentered.this.setPopupPosition(x, y);
            }
        });

    }

    public void showCentered() {
        setCenterPositionImpl();
    }
}
