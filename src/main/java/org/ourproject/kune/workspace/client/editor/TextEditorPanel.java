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
package org.ourproject.kune.workspace.client.editor;

import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.ui.palette.ColorWebSafePalette;
import org.ourproject.kune.workspace.client.skel.Toolbar;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.gwtext.client.widgets.BoxComponent;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.event.ContainerListenerAdapter;

public class TextEditorPanel implements TextEditorView {
    private static final String BACKCOLOR_ENABLED = "#FFF";
    private static final String BACKCOLOR_DISABLED = "#CCC";
    public static final String TEXT_AREA = "k-tep-ta";
    private final RichTextArea gwtRTarea;
    private final TextEditorToolbar textEditorToolbar;
    private final TextEditorPresenter presenter;
    private final Timer saveTimer;
    private final I18nTranslationService i18n;
    private final VerticalPanel mainPanel;
    private final WorkspaceSkeleton ws;

    public TextEditorPanel(final TextEditorPresenter presenter, final I18nTranslationService i18n,
            final WorkspaceSkeleton ws, final ColorWebSafePalette colorPalette) {
        this.presenter = presenter;
        this.i18n = i18n;
        this.ws = ws;
        mainPanel = new VerticalPanel();
        mainPanel.setWidth("100%");

        gwtRTarea = new RichTextArea();
        gwtRTarea.setWidth("97%");
        gwtRTarea.setHeight("100%");
        gwtRTarea.addStyleName("kune-TexEditorPanel-TextArea");
        gwtRTarea.ensureDebugId(TEXT_AREA);

        final Toolbar editorTopBar = new Toolbar();
        editorTopBar.getPanel().setWidth("auto");
        textEditorToolbar = new TextEditorToolbar(gwtRTarea, presenter, colorPalette, i18n);
        editorTopBar.add(textEditorToolbar);
        editorTopBar.addStyleName("k-toolbar-bottom-line");

        mainPanel.add(editorTopBar.getPanel());
        mainPanel.add(gwtRTarea);

        adjustSize(ws.getEntityWorkspace().getContentHeight());
        ws.getEntityWorkspace().addContentListener(new ContainerListenerAdapter() {
            @Override
            public void onResize(final BoxComponent component, final int adjWidth, final int adjHeight,
                    final int rawWidth, final int rawHeight) {
                adjustSize(adjHeight);
            }
        });

        saveTimer = new Timer() {
            @Override
            public void run() {
                presenter.onSave();
            }
        };
    }

    public void attach() {
        ws.getEntityWorkspace().setContent(mainPanel);
    }

    public void detach() {
        mainPanel.removeFromParent();
    }

    public void editHTML(final boolean edit) {
        textEditorToolbar.editHTML(edit);
    }

    public String getHTML() {
        return gwtRTarea.getHTML();
    }

    public String getText() {
        return gwtRTarea.getText();
    }

    public void saveTimerCancel() {
        saveTimer.cancel();
    }

    public void scheduleSave(final int delayMillis) {
        saveTimer.schedule(delayMillis);
    }

    public void setEnabled(final boolean enabled) {
        final String bgColor = enabled ? BACKCOLOR_ENABLED : BACKCOLOR_DISABLED;
        DOM.setStyleAttribute(gwtRTarea.getElement(), "backgroundColor", bgColor);
        gwtRTarea.setEnabled(enabled);
    }

    public void setHeight(final String height) {
        gwtRTarea.setHeight(height);
    }

    public void setHTML(final String html) {
        gwtRTarea.setHTML(html);
    }

    public void setText(final String text) {
        gwtRTarea.setText(text);
    }

    public void showSaveBeforeDialog() {
        MessageBox.confirm(i18n.t("Save confirmation"), i18n.t("Save before closing the editor?"),
                new MessageBox.ConfirmCallback() {
                    public void execute(final String btnID) {
                        if (btnID.equals("yes")) {
                            presenter.onSaveAndClose();
                        } else {
                            presenter.onCancelConfirmed();
                        }
                    }
                });

    }

    private void adjustSize(final int height) {
        int newHeight = height - WorkspaceSkeleton.DEF_TOOLBAR_HEIGHT - 27;
        gwtRTarea.setHeight("" + newHeight);
        mainPanel.setCellHeight(gwtRTarea, "" + newHeight);
    }
}
