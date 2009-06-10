package org.ourproject.kune.platf.client.ui.rte.basic;

import org.ourproject.kune.platf.client.actions.Action;
import org.ourproject.kune.platf.client.actions.ActionEvent;
import org.ourproject.kune.platf.client.actions.ActionItem;
import org.ourproject.kune.platf.client.actions.ui.GuiActionDescrip;
import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.platf.client.ui.KuneUiUtils;
import org.ourproject.kune.platf.client.ui.dialogs.AbstractPopupPanel;

import com.calclab.suco.client.events.Listener0;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

public class RTELinkPopup extends AbstractPopupPanel {
    private final HorizontalPanel hpanel;

    public RTELinkPopup() {
        super(false, false);
        hpanel = new HorizontalPanel();
        hpanel.setSpacing(5);
        KuneUiUtils.setUnselectable(hpanel.getElement());
        final Image close = new Image();
        Images.App.getInstance().kuneClose().applyTo(close);
        close.addClickHandler(new ClickHandler() {
            public void onClick(final ClickEvent event) {
                hide();
            }
        });
        hpanel.add(close);
    }

    public void add(final GuiActionDescrip item) {
        final Label actionLabel = new Label();
        actionLabel.setText((String) item.getValue(Action.NAME));
        actionLabel.addStyleName("k-rte-changelink");
        KuneUiUtils.setUnselectable(actionLabel.getElement());
        actionLabel.addClickHandler(new ClickHandler() {
            public void onClick(final ClickEvent event) {
                item.fire(new ActionEvent(actionLabel, (Event) event.getNativeEvent()));
            }
        });
        hpanel.insert(actionLabel, 0);
    }

    @Deprecated
    public void addAction(final ActionItem<Object> item, final Listener0 onClick) {
        final Label actionLabel = new Label();
        actionLabel.setText(item.getAction().getText());
        actionLabel.addStyleName("k-rte-changelink");
        KuneUiUtils.setUnselectable(actionLabel.getElement());
        actionLabel.addClickHandler(new ClickHandler() {
            public void onClick(final ClickEvent event) {
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
