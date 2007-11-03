/*
 *
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

package org.ourproject.kune.workspace.client.ui.ctx.items;

import org.ourproject.kune.platf.client.ui.RoundedBorderDecorator;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

class ContextTopBar extends VerticalPanel {

    public final Label currentFolder;
    public final PushButton btnGoParent;
    public final HorizontalPanel firstRow;

    public ContextTopBar(final ContextItemsPresenter presenter) {
        final ContextItemsImages Img = ContextItemsImages.App.getInstance();

        firstRow = new HorizontalPanel();
        final HorizontalPanel secondRow = new HorizontalPanel();
        final HorizontalPanel iconBarHP = new HorizontalPanel();
        final HorizontalPanel currentFolderHP = new HorizontalPanel();
        btnGoParent = new PushButton(Img.folderGoUp().createImage(), Img.folderGoUpLight().createImage());
        btnGoParent.addClickListener(new ClickListener() {
            public void onClick(final Widget sender) {
                presenter.onGoUp();
            }
        });
        final MenuBar pathMenu = new MenuBar();
        final MenuBar pathSubmenu = new MenuBar(true);

        // Layout
        add(firstRow);
        add(secondRow);
        firstRow.add(iconBarHP);
        secondRow.add(currentFolderHP);
        // iconBarHP.add(btnGoParent);
        final RoundedBorderDecorator buttonRounded = new RoundedBorderDecorator(pathMenu, RoundedBorderDecorator.ALL,
                RoundedBorderDecorator.SIMPLE);
        iconBarHP.add(buttonRounded);
        pathMenu.addItem(Img.folderpathmenu().getHTML(), true, pathSubmenu);
        pathSubmenu.addItem(Img.folder().getHTML() + "&nbsp;Container", true, new Command() {
            public void execute() {
                // FIXME
                Window.alert("jump!");
            }
        });
        pathSubmenu.addItem(Img.folder().getHTML() + "&nbsp;Container 2", true, new Command() {
            public void execute() {
                // FIXME
                Window.alert("jump too!");
            }
        });
        currentFolderHP.add(btnGoParent);
        currentFolder = new Label("Current Container");
        currentFolderHP.add(currentFolder);

        // Set properties
        addStyleName("kune-NavigationBar");
        firstRow.addStyleName("topBar");
        firstRow.addStyleName("topBar-margin");
        secondRow.addStyleName("topBar");
        firstRow.setWidth("100%");
        secondRow.setWidth("100%");
        setCellWidth(firstRow, "100%");
        setCellWidth(secondRow, "100%");
        firstRow.setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);
        iconBarHP.addStyleName("kune-topBar-iconBar");
        iconBarHP.setCellVerticalAlignment(btnGoParent, VerticalPanel.ALIGN_MIDDLE);
        iconBarHP.setCellVerticalAlignment(buttonRounded, VerticalPanel.ALIGN_MIDDLE);
        pathMenu.setStyleName("pathMenu");
        buttonRounded.setColor("AAA");
        btnGoParent.addStyleName("kune-pointer");
    }

}
