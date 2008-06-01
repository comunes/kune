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

package org.ourproject.kune.docs.client.cnt.reader.ui;

import org.ourproject.kune.docs.client.cnt.reader.DocumentReaderControlView;
import org.ourproject.kune.docs.client.cnt.reader.DocumentReaderListener;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.ui.CustomPushButton;
import org.ourproject.kune.workspace.client.sitebar.Site;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

public class DocumentReaderControlPanel extends HorizontalPanel implements DocumentReaderControlView {
    private final CustomPushButton editBtn;
    private final CustomPushButton deleteBtn;
    private final CustomPushButton translateBtn;

    public DocumentReaderControlPanel(final DocumentReaderListener listener, final I18nTranslationService i18n) {
        editBtn = new CustomPushButton(i18n.tWithNT("Edit", "used in button"), new ClickListener() {
            public void onClick(final Widget sender) {
                listener.onEdit();
                editBtn.removeStyleDependentName("up-hovering");
            }
        });

        deleteBtn = new CustomPushButton(i18n.tWithNT("Delete", "used in button"), new ClickListener() {
            public void onClick(final Widget sender) {
                listener.onDelete();
                deleteBtn.removeStyleDependentName("up-hovering");
            }
        });

        translateBtn = new CustomPushButton(i18n.tWithNT("Translate", "used in button"), new ClickListener() {
            public void onClick(final Widget sender) {
                listener.onTranslate();
                translateBtn.removeStyleDependentName("up-hovering");
                Site.showAlertMessage(i18n.t("Sorry, this functionality is currently in development"));
            }
        });

        add(editBtn);
        add(deleteBtn);
        add(translateBtn);
        deleteBtn.addStyleName("kune-Button-Small-lSpace");
        translateBtn.addStyleName("kune-Button-Small-lSpace");
        setEditEnabled(false);
        setDeleteEnabled(false);
        setTranslateEnabled(false);
    }

    public void setDeleteEnabled(final boolean isEnabled) {
        deleteBtn.setVisible(isEnabled);
    }

    public void setEditEnabled(final boolean isEnabled) {
        editBtn.setVisible(isEnabled);
    }

    public void setTranslateEnabled(final boolean isEnabled) {
        translateBtn.setVisible(isEnabled);
    }

}
