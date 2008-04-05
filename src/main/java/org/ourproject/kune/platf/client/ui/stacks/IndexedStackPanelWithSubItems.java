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

import org.ourproject.kune.platf.client.AbstractPresenter;
import org.ourproject.kune.platf.client.ui.IconLabel;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class IndexedStackPanelWithSubItems extends IndexedStackPanel {

    private static final int MAX_ELEMENTS_PER_STACKITEM = 5;

    public IndexedStackPanelWithSubItems() {
    }

    /* Stack subItems */

    public void addStackSubItem(final String parentItemName, final AbstractImagePrototype icon, final String name,
            final String title, final StackSubItemAction[] memberActions, final AbstractPresenter presenter) {
        StackSubItem stackSubItem = new StackSubItem(icon, name, title, memberActions, presenter);
        int indexOfStackItem = this.indexOf(parentItemName);
        ScrollPanel sp = (ScrollPanel) this.getWidget(indexOfStackItem);
        VerticalPanel vp = (VerticalPanel) sp.getWidget();
        vp.add(stackSubItem);
        StackItem stackItem = this.getItem(indexOfStackItem);
        stackItem.addSubItem(name);
        this.setStackText(indexOfStackItem, stackItem.getHtml(), true);
        updateScroll(indexOfStackItem, stackItem.getCount());
    }

    public void removeStackSubItem(final String parentItemName, final String name) {
        int indexOfStackItem = this.indexOf(parentItemName);
        int indexOfStackSubItem = this.getItem(indexOfStackItem).indexOfSubItem(name);

        ScrollPanel sp = (ScrollPanel) this.getWidget(indexOfStackItem);
        ((VerticalPanel) sp.getWidget()).remove(indexOfStackSubItem);
        StackItem stackItem = this.getItem(indexOfStackItem);
        stackItem.removeSubItem(name);
        this.setStackText(indexOfStackItem, stackItem.getHtml(), true);
        updateScroll(indexOfStackItem, stackItem.getCount());
    }

    public void clear() {
        super.clear();
    }

    private void updateScroll(final int member, final int count) {
        if (count > MAX_ELEMENTS_PER_STACKITEM) {
            this.getWidget(member).setHeight("6em");
        } else {
            this.getWidget(member).setHeight("100%");
        }
    }

    protected class StackSubItem extends MenuBar {
        private final MenuBar actions;
        private AbstractImagePrototype icon;
        private String name;
        private final AbstractPresenter presenter;

        public StackSubItem(final AbstractImagePrototype icon, final String name, final String title,
                final StackSubItemAction[] memberActions, final AbstractPresenter presenter) {
            super(false);
            this.icon = icon;
            this.name = name;
            this.presenter = presenter;
            IconLabel label = new IconLabel(icon, name);
            label.setTitle(title);
            actions = new MenuBar(true);
            addItem(label.toString(), true, actions);
            setAutoOpen(false);
            actions.setAutoOpen(true);
            setStyleName("kune-StackSubItemLabel");
            actions.setStyleName("kune-StackSubItemActions");
            for (int i = 0; i < memberActions.length; i++) {
                addAction(memberActions[i], name);
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

        public void addAction(final StackSubItemAction memberAction, final String param) {
            String itemHtml = "";
            AbstractImagePrototype icon = memberAction.getIcon();
            if (icon != null) {
                itemHtml = icon.getHTML();
            }
            itemHtml += memberAction.getText();
            actions.addItem(itemHtml, true, createCommand(memberAction.getAction(), param));
        }

        private void setMenu() {
            String label = icon.getHTML() + name;
            getItems().get(0).setText(label);
        }

        private Command createCommand(final String action, final String param) {
            return new Command() {
                public void execute() {
                    presenter.doAction(action, param);
                }
            };
        }
    }

}
