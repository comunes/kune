package cc.kune.common.client.ui;

import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.UIObject;

public abstract class AbstractAtBorderPopupPanel extends PopupPanel {

    private boolean showCentered = true;
    protected UIObject showNearObject;

    public AbstractAtBorderPopupPanel() {
        super(false, false);
    }

    public AbstractAtBorderPopupPanel(final boolean autohide) {
        this(autohide, false);
    }

    public AbstractAtBorderPopupPanel(final boolean autohide, final boolean modal) {
        super(autohide, modal);
        setGlassEnabled(modal);
        init();
    }

    public void defaultStyle() {
        addStyleName("k-opacity90");
        addStyleName("k-box-10shadow");
    }

    private void init() {
        Window.addResizeHandler(new ResizeHandler() {
            @Override
            public void onResize(final ResizeEvent event) {
                if (isShowing()) {
                    if (showCentered) {
                        setCenterPositionImpl();
                    } else {
                        showRelativeImpl();
                    }
                }
            }
        });
    }

    public void setCenterPosition() {
        setCenterPositionImpl();
    }

    protected abstract void setCenterPositionImpl();

    public void showCentered() {
        showCentered = true;
        setCenterPositionImpl();
    }

    public void showNear(final UIObject object) {
        this.showNearObject = object;
        showCentered = false;
        showRelativeImpl();
    }

    private void showRelativeImpl() {
        showRelativeTo(showNearObject);
    }
}
