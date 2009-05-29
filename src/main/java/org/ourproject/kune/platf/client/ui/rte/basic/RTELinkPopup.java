package org.ourproject.kune.platf.client.ui.rte.basic;

import org.ourproject.kune.platf.client.actions.ActionItem;
import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.platf.client.ui.KuneUiUtils;
import org.ourproject.kune.platf.client.ui.dialogs.AbstractPopupPanel;

import com.calclab.suco.client.events.Listener0;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class RTELinkPopup extends AbstractPopupPanel {
    private final transient HorizontalPanel hpanel;

    public RTELinkPopup() {
        super(false, false);
        hpanel = new HorizontalPanel();
        hpanel.setSpacing(5);
        KuneUiUtils.setUnselectable(hpanel.getElement());
        final Image close = new Image();
        Images.App.getInstance().kuneClose().applyTo(close);
        close.addClickListener(new ClickListener() {
            public void onClick(final Widget sender) {
                hide();
            }
        });
        hpanel.add(close);
    }

    public void addAction(final ActionItem<Object> item, final Listener0 onClick) {
        final Label actionLabel = new Label();
        actionLabel.setText(item.getAction().getText());
        actionLabel.addStyleName("k-rte-changelink");
        KuneUiUtils.setUnselectable(actionLabel.getElement());
        actionLabel.addClickListener(new ClickListener() {
            public void onClick(final Widget arg0) {
                onClick.onEvent();
            }
        });
        hpanel.insert(actionLabel, 0);
    }

    @Override
    protected void createWidget() {
        widget = hpanel;
        super.addStyleName("k-rte-changelink-popup");
    }
}
