package org.ourproject.kune.platf.client.ui.rte.basic;

import org.ourproject.kune.platf.client.actions.ActionItem;

import com.calclab.suco.client.events.Listener0;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class RTELinkPopup {
    private final HorizontalPanel hp;
    private PopupPanel popupPalette;

    public RTELinkPopup() {
        hp = new HorizontalPanel();
        hp.setSpacing(5);
    }

    public void addAction(final ActionItem<Object> item, final Listener0 onClick) {
        Label actionLabel = new Label();
        actionLabel.setText(item.getAction().getText());
        actionLabel.addStyleName("k-rte-changelink");
        actionLabel.addClickListener(new ClickListener() {
            public void onClick(final Widget arg0) {
                onClick.onEvent();
            }
        });
        hp.add(actionLabel);
    }

    public void hide() {
        if (popupPalette != null) {
            popupPalette.hide();
        }
    }

    public void show(final int left, final int top) {
        popupPalette = new PopupPanel(false, false);
        popupPalette.addStyleName("k-rte-changelink-popup");
        popupPalette.setVisible(false);
        popupPalette.show();
        popupPalette.setPopupPosition(left, top);
        popupPalette.setWidget(hp);
        popupPalette.setVisible(true);
    }
}
