/*
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.workspace.client.workspace.ui;

import java.util.ArrayList;
import java.util.Iterator;

import org.ourproject.kune.platf.client.AbstractPresenter;
import org.ourproject.kune.platf.client.ui.DropDownPanel;
import org.ourproject.kune.platf.client.ui.IconLabel;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.StackPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class StackedDropDownPanel extends DropDownPanel {

    private static final int MAX_ELEMENTS_PER_STACKITEM = 5;
    public final static String ICON_HORIZ_ALIGN_RIGHT = "right";
    public final static String ICON_HORIZ_ALIGN_LEFT = "left";
    private final AbstractPresenter presenter;
    private final StackPanel stack;
    private final ArrayList stackList;
    private final VerticalPanel bottomLinksVP;
    private final ArrayList bottomLinksIndex;
    private String headerText;
    private String headerTitle;
    private int headerCount;
    private boolean headerCountVisible;

    public StackedDropDownPanel(final AbstractPresenter presenter, final String borderColor, final String headerText,
            final String headerTitle, final boolean headerCountVisible) {
        this.presenter = presenter;
        this.headerText = headerText;
        this.headerTitle = headerTitle;
        this.headerCountVisible = headerCountVisible;
        this.headerCount = 0;
        VerticalPanel generalVP = new VerticalPanel();
        stack = new StackPanel();
        stackList = new ArrayList();
        bottomLinksVP = new VerticalPanel();
        bottomLinksIndex = new ArrayList();

        // Layout
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
        super.setTitle(headerTitle);
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
        addStackItem(name, title, null, null, countVisible);
    }

    public void addStackItem(final String name, final String title, final AbstractImagePrototype icon,
            final String iconAlign, final boolean countVisible) {
        ScrollPanel siSP = new ScrollPanel();
        VerticalPanel siVP = new VerticalPanel();
        siSP.add(siVP);
        StackItem stackItem = new StackItem(name, title, icon, iconAlign, countVisible);
        stack.add(siSP, stackItem.getHtml(), true);
        stackList.add(stackItem);
    }

    public void removeStackItem(final String name) {
        int idx = indexInArray(name);
        stack.remove(idx);
        stackList.remove(idx);
    }

    public void showStackItem(final String name) {
        int idx = indexInArray(name);
        stack.showStack(idx);
    }

    private int indexInArray(final String name) {
        final Iterator iter = stackList.iterator();
        int i = 0;
        while (iter.hasNext()) {
            final StackItem stackItem = (StackItem) iter.next();
            if (stackItem.getName() == name) {
                return i;
            } else {
                i++;
            }
        }
        throw new IndexOutOfBoundsException();
    }

    /* Stack subItems */

    public void addStackSubItem(final String parentItemName, final AbstractImagePrototype icon, final String name,
            final String title, final StackSubItemAction[] memberActions) {
        StackSubItem stackSubItem = new StackSubItem(icon, name, title, memberActions);
        int indexOfStackItem = indexInArray(parentItemName);
        ScrollPanel sp = (ScrollPanel) stack.getWidget(indexOfStackItem);
        VerticalPanel vp = (VerticalPanel) sp.getWidget();
        vp.add(stackSubItem);
        StackItem stackItem = (StackItem) stackList.get(indexOfStackItem);
        stackItem.addSubItem(name);
        stack.setStackText(indexOfStackItem, stackItem.getHtml(), true);
        headerCount++;
        updateHeaderText();
        updateScroll(indexOfStackItem, stackItem.getCount());
    }

    private void updateScroll(final int member, final int count) {
        if (count > MAX_ELEMENTS_PER_STACKITEM) {
            stack.getWidget(member).setHeight("6em");
        } else {
            stack.getWidget(member).setHeight("100%");
        }

    }

    public void removeStackSubItem(final String parentItemName, final String name) {
        int indexOfStackItem = indexInArray(parentItemName);
        int indexOfStackSubItem = ((StackItem) stackList.get(indexOfStackItem)).indexOfSubItem(name);

        ScrollPanel sp = (ScrollPanel) stack.getWidget(indexOfStackItem);
        ((VerticalPanel) sp.getWidget()).remove(indexOfStackSubItem);
        StackItem stackItem = (StackItem) stackList.get(indexOfStackItem);
        stackItem.removeSubItem(name);
        stack.setStackText(indexOfStackItem, stackItem.getHtml(), true);
        headerCount--;
        updateHeaderText();
        updateScroll(indexOfStackItem, stackItem.getCount());
    }

    public void addBottomLink(final AbstractImagePrototype icon, final String text, final String action) {
        this.addBottomLink(icon, text, action, null, null);
    }

    public void addBottomLink(final AbstractImagePrototype icon, final String text, final String action,
            final Object value, final Object extra) {
        IconLabel link = new IconLabel(icon, text);
        bottomLinksVP.add(link);
        link.addStyleName("kune-StackedDropDownPanelLink");
        link.addClickListener(new ClickListener() {
            public void onClick(final Widget arg0) {
                presenter.doAction(action, value, extra);
            }
        });
        bottomLinksVP.setCellHorizontalAlignment(link, HorizontalPanel.ALIGN_CENTER);
        bottomLinksIndex.add(text);
    }

    public void cleanBottomLinks() {
        Iterator iter = bottomLinksIndex.iterator();
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

    private int indexOfLink(final String text) {
        return bottomLinksIndex.indexOf(text);
    }

    /*
     * Unattached stack item. We update the stack using this object and
     * generating the Html with getHtml
     */

    class StackItem {
        private String text;
        private String title;
        private AbstractImagePrototype icon;
        private String iconAlign;
        private boolean countVisible;
        private final ArrayList subItems;

        public StackItem(final String text, final String title, final AbstractImagePrototype icon,
                final String iconAlign, final boolean countVisible) {
            this.text = text;
            this.title = title;
            this.icon = icon;
            this.iconAlign = iconAlign;
            this.countVisible = countVisible;
            subItems = new ArrayList();
        }

        public int getCount() {
            return subItems.size();
        }

        public int indexOfSubItem(final String name) {
            return subItems.indexOf(name);
        }

        public void addSubItem(final String name) {
            subItems.add(name);
        }

        public void removeSubItem(final String name) {
            subItems.remove(name);
        }

        public String getName() {
            return text;
        }

        public void setText(final String text) {
            this.text = text;
        }

        public void setTitle(final String title) {
            this.title = title;
        }

        public void setIcon(final AbstractImagePrototype icon) {
            this.icon = icon;
        }

        public void setIconAlign(final String iconAlign) {
            this.iconAlign = iconAlign;
        }

        public boolean isCountVisible() {
            return countVisible;
        }

        public void setCountVisible(final boolean visible) {
            this.countVisible = visible;
        }

        public String getHtml() {
            Element div = DOM.createDiv();
            Element labelElem = DOM.createSpan();
            Element iconElement = null;
            DOM.setElementAttribute(div, "title", title);
            boolean insertIcon = icon != null && iconAlign != null;
            if (insertIcon) {
                iconElement = icon.createImage().getElement();
                if (iconAlign == StackedDropDownPanel.ICON_HORIZ_ALIGN_LEFT) {
                    DOM.appendChild(div, iconElement);
                }
            }
            DOM.setInnerText(labelElem, text + (countVisible ? " (" + subItems.size() + ")" : ""));
            DOM.appendChild(div, labelElem);
            if (insertIcon) {
                if (iconAlign == StackedDropDownPanel.ICON_HORIZ_ALIGN_RIGHT) {
                    DOM.appendChild(div, iconElement);
                }
            }
            return div.toString();
        }
    }

    class StackSubItem extends MenuBar {
        private final MenuBar actions;
        private AbstractImagePrototype icon;
        private String name;

        public StackSubItem(final AbstractImagePrototype icon, final String name, final String title,
                final StackSubItemAction[] memberActions) {
            super(false);
            this.icon = icon;
            this.name = name;
            String label = icon.getHTML() + name;
            setTitle(title);
            actions = new MenuBar(true);
            addItem(label, true, actions);
            setAutoOpen(false);
            actions.setAutoOpen(true);
            setStyleName("kune-StackSubItemLabel");
            actions.setStyleName("kune-StackSubItemActions");
            for (int i = 0; i < memberActions.length; i++) {
                addAction(memberActions[i], name, null);
            }
        }

        public void setName(final String name) {
            this.name = name;
            setMenu();
        }

        public void setImage(final AbstractImagePrototype icon) {
            this.icon = icon;
            setMenu();
        }

        public void addAction(final StackSubItemAction memberAction, final String param1, final String param2) {
            String itemHtml = "";
            AbstractImagePrototype icon = memberAction.getIcon();
            if (icon != null) {
                itemHtml = icon.getHTML();
            }
            itemHtml += memberAction.getText();
            actions.addItem(itemHtml, true, createCommand(memberAction.getAction(), param1, param2));
        }

        private void setMenu() {
            String label = icon.getHTML() + name;
            ((MenuItem) getItems().get(0)).setText(label);
        }

        private Command createCommand(final String action, final String param1, final String param2) {
            return new Command() {
                public void execute() {
                    presenter.doAction(action, param1, param2);
                }
            };
        }
    }

    public void clear() {
        stackList.clear();
        stack.clear();
        bottomLinksIndex.clear();
        bottomLinksVP.clear();
        headerCount = 0;
        updateHeaderText();
    }

}
