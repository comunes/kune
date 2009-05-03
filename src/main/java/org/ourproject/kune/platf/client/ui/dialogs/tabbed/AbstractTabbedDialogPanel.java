/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
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
package org.ourproject.kune.platf.client.ui.dialogs.tabbed;

import java.util.ArrayList;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.platf.client.ui.dialogs.BasicDialog;
import org.ourproject.kune.platf.client.ui.dialogs.DefaultForm;
import org.ourproject.kune.platf.client.ui.dialogs.MessageToolbar;
import org.ourproject.kune.platf.client.ui.noti.NotifyUser.Level;

import com.allen_sauer.gwt.log.client.Log;
import com.calclab.suco.client.events.Listener0;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Component;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.TabPanel;
import com.gwtext.client.widgets.event.WindowListenerAdapter;

public abstract class AbstractTabbedDialogPanel implements AbstractTabbedDialogView {
    private BasicDialog dialog;
    private final String dialogId;
    private TabPanel tabPanel;
    private MessageToolbar messageErrorBar;
    private String title;
    private final int width;
    private final int height;
    private final int minWidth;
    private final int minHeight;
    private final boolean modal;
    private final Images images;
    private final String errorLabelId;
    private String iconCls;
    private Listener0 onHideListener;
    private final ArrayList<Button> buttons;

    public AbstractTabbedDialogPanel(final String dialogId, final String title, final int width, final int height,
            final int minWidth, final int minHeight, final boolean modal, final Images images, final String errorLabelId) {
        this.dialogId = dialogId;
        this.title = title;
        this.width = width;
        this.height = height;
        this.minWidth = minWidth;
        this.minHeight = minHeight;
        this.modal = modal;
        this.images = images;
        this.errorLabelId = errorLabelId;
        buttons = new ArrayList<Button>();
    }

    public void activateTab(final int index) {
        createDialogIfNecessary();
        tabPanel.activate(index);
    }

    /**
     * User before show/render
     * 
     * @param button
     */
    public void addButton(final Button button) {
        buttons.add(button);
    }

    public void addHideListener(final Listener0 onHideListener) {
        this.onHideListener = onHideListener;
        if (dialog != null) {
            addHideListener();
        }
    }

    public void addTab(final View view) {
        addTabPanel(castPanel(view));
        doLayoutIfNeeded();
    }

    public void createAndShow() {
        show();
        setFirstTabActive();
    }

    public void destroy() {
        if (dialog != null) {
            dialog.destroy();
            dialog = null;
        }
    }

    public void doLayoutIfNeeded() {
        if (dialog.isRendered()) {
            dialog.doLayout(false);
        }
    }

    public void hide() {
        if (dialog != null) {
            dialog.hide();
        }
    }

    public void hideMessages() {
        if (dialog != null) {
            messageErrorBar.hideErrorMessage();
        }
    }

    public void insertTab(final int index, final View view) {
        insertTabPanel(index, castPanel(view));
        doLayoutIfNeeded();
    }

    public boolean isVisible() {
        createDialogIfNecessary();
        return dialog.isVisible();
    }

    public void setErrorMessage(final String message, final Level level) {
        messageErrorBar.setErrorMessage(message, level);
    }

    public void setFirstTabActive() {
        tabPanel.setActiveTab(0);
    }

    public void setIconCls(final String iconCls) {
        this.iconCls = iconCls;
        if (dialog != null) {
            dialog.setIconCls(iconCls);
        }
    }

    public void setTitle(final String title) {
        this.title = title;
        if (dialog != null) {
            dialog.setTitle(title);
        }
    }

    public void show() {
        createDialogIfNecessary();
        dialog.show();
    }

    private void addHideListener() {
        dialog.addListener(new WindowListenerAdapter() {
            @Override
            public void onClose(final Panel panel) {
                onHideListener.onEvent();
            }

            @Override
            public void onHide(final Component component) {
                onHideListener.onEvent();
            }
        });
    }

    private void addTabPanel(final Panel newTab) {
        createDialogIfNecessary();
        tabPanel.add(newTab);
    }

    private Panel castPanel(final View view) {
        Panel panel;
        if (view instanceof Panel) {
            panel = (Panel) view;
        } else if (view instanceof DefaultForm) {
            panel = ((DefaultForm) view).getFormPanel();
        } else {
            panel = null;
            Log.error("Programatic error: Unexpected element added to GroupOptions");
        }
        return panel;
    }

    private void createDialog() {
        dialog = new BasicDialog(dialogId, title, modal, true, width, height, minWidth, minHeight);
        dialog.setCollapsible(false);
        messageErrorBar = new MessageToolbar(images, errorLabelId);
        dialog.setBottomToolbar(messageErrorBar.getToolbar());
        tabPanel = new TabPanel();
        tabPanel.setBorder(false);
        dialog.add(tabPanel);
        if (iconCls != null) {
            dialog.setIconCls(iconCls);
        }
        if (onHideListener != null) {
            addHideListener();
        }
        for (Button button : buttons) {
            dialog.addButton(button);
        }
    }

    private void createDialogIfNecessary() {
        if (dialog == null) {
            createDialog();
        }
    }

    private void insertTabPanel(final int index, final Panel newTab) {
        createDialogIfNecessary();
        tabPanel.insert(index, newTab);
    }
}
