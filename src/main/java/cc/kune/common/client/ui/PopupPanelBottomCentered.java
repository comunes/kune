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

    public PopupPanelBottomCentered(boolean autohide, boolean modal) {
        super(autohide, modal);
        init();
    }

    public PopupPanelBottomCentered(boolean autohide) {
        super(autohide);
        init();
    }

    private void init() {
        setStyleName("k-popup-bottom-centered");
        addStyleName("k-opacity90");
        addStyleName("k-box-10shadow");
        addStyleName("k-top-10corners");
        Window.addResizeHandler(new ResizeHandler() {
            @Override
            public void onResize(ResizeEvent event) {
                setCenterPosition();
            }
        });
    }

    public void setCenterPosition() {
        Widget widget = getWidget();
        int x = (Window.getClientWidth() - (widget != null ? getWidget().getOffsetWidth() : 0)) / 2;
        int y = Window.getClientHeight() - (widget != null ? getWidget().getOffsetHeight() : 0);
        setPopupPosition(x, y);
    }

    @Override
    public void show() {
        super.show();
        setCenterPosition();
    }
}
