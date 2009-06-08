package org.ourproject.kune.platf.client.ui.dialogs;

import com.google.gwt.user.client.ui.Widget;

public class BasicPopupPanel extends AbstractPopupPanel {

    public BasicPopupPanel() {
        super();
    }

    public BasicPopupPanel(final boolean autohide) {
        super(autohide);
    }

    public BasicPopupPanel(final boolean autohide, final boolean modal) {
        super(autohide, modal);
    }

    public void setWidget(final Widget widget) {
        this.widget = widget;
    }

    @Override
    protected void createWidget() {
        // Nothing
    }
}
