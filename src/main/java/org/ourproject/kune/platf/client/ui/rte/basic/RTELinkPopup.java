/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.ourproject.kune.platf.client.ui.rte.basic;

import org.ourproject.kune.platf.client.actions.Action;
import org.ourproject.kune.platf.client.actions.ActionEvent;
import org.ourproject.kune.platf.client.actions.ActionItem;
import org.ourproject.kune.platf.client.actions.ui.OldGuiActionDescrip;
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

    public void add(final OldGuiActionDescrip item) {
        final Label actionLabel = new Label();
        actionLabel.setText((String) item.getValue(Action.NAME));
        actionLabel.addStyleName("k-rte-changelink");
        KuneUiUtils.setUnselectable(actionLabel.getElement());
        actionLabel.addClickHandler(new ClickHandler() {
            public void onClick(final ClickEvent event) {
                item.fire(new ActionEvent(actionLabel, Event.as(event.getNativeEvent())));
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
