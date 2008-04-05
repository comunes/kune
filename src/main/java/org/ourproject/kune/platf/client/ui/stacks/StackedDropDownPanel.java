/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
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
package org.ourproject.kune.platf.client.ui.stacks;

import java.util.ArrayList;
import java.util.Iterator;

import org.ourproject.kune.platf.client.AbstractPresenter;
import org.ourproject.kune.platf.client.ui.DropDownPanel;
import org.ourproject.kune.platf.client.ui.IconLabel;
import org.ourproject.kune.platf.client.ui.UIConstants;

import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class StackedDropDownPanel extends DropDownPanel implements UIConstants {
    private final AbstractPresenter presenter;
    private final IndexedStackPanelWithSubItems stack;
    private final VerticalPanel bottomLinksVP;
    private final ArrayList<String> bottomLinksIndex;
    private String headerText;
    private String headerTitle;
    private int headerCount;
    private boolean headerCountVisible;
    private final VerticalPanel commentsVP;

    public StackedDropDownPanel(final AbstractPresenter presenter, final String borderColor, final String headerText,
            final String headerTitle, final boolean headerCountVisible) {
        this.presenter = presenter;
        this.headerText = headerText;
        this.headerTitle = headerTitle;
        this.headerCountVisible = headerCountVisible;
        this.headerCount = 0;
        VerticalPanel generalVP = new VerticalPanel();
        commentsVP = new VerticalPanel();
        stack = new IndexedStackPanelWithSubItems();
        bottomLinksVP = new VerticalPanel();
        bottomLinksIndex = new ArrayList<String>();

        // Layout
        generalVP.add(commentsVP);
        generalVP.add(stack);
        generalVP.add(bottomLinksVP);
        setContent(generalVP);

        // Set properties
        super.setColor(borderColor);
        setContentVisible(false); // DropDown
        setHeaderText(headerText);
        setHeaderTitle(headerTitle);
        addStyleName("kune-StackedDropDownPanel");
        addStyleName("kune-Margin-Medium-t");
        stack.setStyleName("kune-StackedDropDownPanel");
    }

    /* Header */

    public void setHeaderText(final String headerText) {
        this.headerText = headerText;
    }

    public void setHeaderTitle(final String headerTitle) {
        this.headerTitle = headerTitle;
        super.setHeaderTitle(headerTitle);
    }

    public void setHeaderCountVisible(final boolean headerCountVisible) {
        this.headerCountVisible = headerCountVisible;
    }

    public void updateHeaderText() {
        if (headerCountVisible) {
            super.setHeaderText(headerText + " (" + headerCount + ")");
        } else {
            super.setHeaderText(headerText);
        }
    }

    public String getHeaderText() {
        return headerText;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public int getHeaderCount() {
        return headerCount;
    }

    public boolean isHeaderCountVisible() {
        return headerCountVisible;
    }

    /* Stack items */

    public void addStackItem(final String name, final String title, final boolean countVisible) {
        stack.addStackItem(name, title, countVisible);
    }

    public void addStackItem(final String name, final String title, final AbstractImagePrototype icon,
            final String iconAlign, final boolean countVisible) {
        stack.addStackItem(name, title, icon, iconAlign, countVisible);
    }

    public void removeStackItem(final String name) {
        stack.removeStackItem(name);
    }

    public void showStackItem(final String name) {
        stack.showStackItem(name);
    }

    /* Stack subItems */

    public void addStackSubItem(final String parentItemName, final AbstractImagePrototype icon, final String name,
            final String title, final StackSubItemAction[] memberActions) {
        stack.addStackSubItem(parentItemName, icon, name, title, memberActions, presenter);
        headerCount++;
        updateHeaderText();
    }

    public void removeStackSubItem(final String parentItemName, final String name) {
        stack.removeStackSubItem(parentItemName, name);
        headerCount--;
        updateHeaderText();
    }

    /* Bottom links */

    public void addBottomLink(final AbstractImagePrototype icon, final String text, final String title,
            final String action) {
        this.addBottomLink(icon, text, title, action, null);
    }

    public void addBottomLink(final AbstractImagePrototype icon, final String text, final String title,
            final String action, final Object value) {
        IconLabel link = new IconLabel(icon, text);
        link.setTitle(title);
        bottomLinksVP.add(link);
        link.addStyleName("kune-StackedDropDownPanelLink");
        link.addClickListener(new ClickListener() {
            public void onClick(final Widget arg0) {
                presenter.doAction(action, value);
            }
        });
        bottomLinksVP.setCellHorizontalAlignment(link, HorizontalPanel.ALIGN_CENTER);
        bottomLinksIndex.add(text);
    }

    public void cleanBottomLinks() {
        Iterator<String> iter = bottomLinksIndex.iterator();
        while (iter.hasNext()) {
            bottomLinksVP.remove(0);
        }
        bottomLinksIndex.clear();
    }

    public void removeBottomLink(final String text) {
        bottomLinksVP.remove(indexOfLink(text));
    }

    public void setDropDownContentVisible(final boolean visible) {
        setContentVisible(visible);
    }

    public void clear() {
        commentsVP.clear();
        commentsVP.setVisible(false);
        stack.clear();
        bottomLinksIndex.clear();
        bottomLinksVP.clear();
        headerCount = 0;
        updateHeaderText();
    }

    public void addComment(final String comment) {
        if (!commentsVP.isVisible()) {
            commentsVP.setVisible(true);
        }
        Label label = new Label(comment);
        commentsVP.add(label);
        label.addStyleName("kune-Margin-Small-trbl");
    }

    private int indexOfLink(final String text) {
        return bottomLinksIndex.indexOf(text);
    }

}
