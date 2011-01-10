package cc.kune.common.client.ui;

import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class PopupPanelTopCentered extends PopupPanel {
    public PopupPanelTopCentered() {
        super();
        init();
    }

    public PopupPanelTopCentered(boolean autohide, boolean modal) {
        super(autohide, modal);
        init();
    }

    public PopupPanelTopCentered(boolean autohide) {
        super(autohide);
        init();
    }

    private void init() {
        setStyleName("k-popup-top-centered");
        addStyleName("k-opacity80");
        addStyleName("k-box-10shadow");
        addStyleName("k-bottom-10corners");
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
        int y = 0;
        setPopupPosition(x, y);
    }

    @Override
    public void show() {
        super.show();
        setCenterPosition();
    }
}
