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
package cc.kune.core.client.notify.spiner;

import cc.kune.core.client.notify.spiner.SpinerPresenter.SpinerView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PopupViewWithUiHandlers;
import com.gwtplatform.mvp.client.UiHandlers;

public class SpinerViewImpl extends PopupViewWithUiHandlers<UiHandlers> implements SpinerView {

    interface SpinerViewImplUiBinder extends UiBinder<Widget, SpinerViewImpl> {
    }

    private static SpinerViewImplUiBinder uiBinder = GWT.create(SpinerViewImplUiBinder.class);

    @UiField
    Image img;

    @UiField
    InlineLabel label;

    @UiField
    HorizontalPanel panel;
    private final PopupPanel popup;
    Widget widget;

    @Inject
    public SpinerViewImpl(EventBus eventBus) {
        super(eventBus);
        widget = uiBinder.createAndBindUi(this);
        popup = new PopupPanel(false, false);
        popup.add(widget);
        popup.setPopupPosition(0, 0);
        popup.setStyleName("k-spiner-popup");
        popup.show();
    }

    @Override
    public Widget asWidget() {
        return popup;
    }

    @Override
    public void fade() {
        popup.hide();
    }

    @Override
    public void show(String message) {
        if (message == null || message.isEmpty()) {
            label.setText("");
        } else {
            label.setText(message);
        }
        popup.show();
    }
}
