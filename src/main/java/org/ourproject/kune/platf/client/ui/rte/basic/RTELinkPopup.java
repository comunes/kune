package org.ourproject.kune.platf.client.ui.rte.basic;

import org.ourproject.kune.platf.client.actions.ActionItem;
import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.platf.client.ui.KuneUiUtils;

import com.calclab.suco.client.events.Listener0;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class RTELinkPopup {
    private final HorizontalPanel hp;
    private PopupPanel popupPalette;

    public RTELinkPopup() {
        hp = new HorizontalPanel();
        hp.setSpacing(5);
        KuneUiUtils.setUnselectable(hp.getElement());
        Image close = new Image();
        Images.App.getInstance().kuneClose().applyTo(close);
        close.addClickListener(new ClickListener() {
            public void onClick(final Widget sender) {
                hide();
            }
        });
        hp.add(close);
    }

    public void addAction(final ActionItem<Object> item, final Listener0 onClick) {
        Label actionLabel = new Label();
        actionLabel.setText(item.getAction().getText());
        actionLabel.addStyleName("k-rte-changelink");
        KuneUiUtils.setUnselectable(actionLabel.getElement());
        actionLabel.addClickListener(new ClickListener() {
            public void onClick(final Widget arg0) {
                onClick.onEvent();
            }
        });
        hp.insert(actionLabel, 0);
    }

    public void hide() {
        if (isVisible()) {
            popupPalette.hide();
        }
    }

    public boolean isVisible() {
        if (popupPalette != null && popupPalette.isVisible()) {
            return true;
        } else {
            return false;
        }
    }

    public void show(final int left, final int top) {
        popupPalette = new PopupPanel(false, false);
        popupPalette.addStyleName("k-rte-changelink-popup");
        popupPalette.setWidget(hp);
        popupPalette.show();
        popupPalette.setPopupPosition(left, top);
        popupPalette.setVisible(true);
    }
}
