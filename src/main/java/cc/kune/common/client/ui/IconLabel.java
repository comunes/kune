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
package cc.kune.common.client.ui;

import java.util.Iterator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.i18n.client.HasDirection.Direction;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HasDirectionalText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;

public class IconLabel extends Composite implements HasWidgets, HasDirectionalText {

    interface IconTitleUiBinder extends UiBinder<Widget, IconLabel> {
    }

    private static IconTitleUiBinder uiBinder = GWT.create(IconTitleUiBinder.class);

    @UiField
    HorizontalPanel hp;
    @UiField
    InlineLabel icon;
    @UiField
    InlineLabel label;
    @UiField
    FocusPanel self;

    public IconLabel() {
        this("");
    }

    public IconLabel(final String text) {
        initWidget(uiBinder.createAndBindUi(this));
        label.setText(text);
    }

    @Override
    public void add(final Widget w) {
        hp.add(w);
    }

    @Override
    public void addStyleName(final String style) {
        hp.addStyleName(style);
    }

    public void addTextStyleName(final String style) {
        label.addStyleName(style);
    }

    @Override
    public void clear() {
        hp.clear();
    }

    public HasClickHandlers getFocus() {
        return self;
    }

    @Override
    public String getText() {
        return label.getText();
    }

    @Override
    public Direction getTextDirection() {
        return label.getTextDirection();
    }

    @Override
    public Iterator<Widget> iterator() {
        return hp.iterator();
    }

    @Override
    public boolean remove(final Widget w) {
        return hp.remove(w);
    }

    public void setIcon(final String imgCss) {
        icon.setStyleName(imgCss);
        icon.addStyleName("oc-ico-pad");
    }

    public void setLabelText(final String text) {
        label.setText(text);
    }

    @Override
    public void setText(final String text) {
        label.setText(text);
    }

    @Override
    public void setText(final String text, final Direction dir) {
        label.setText(text, dir);
    }
}
