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
package org.ourproject.kune.platf.client.ui.rte.edithtml;

import org.ourproject.kune.platf.client.ui.dialogs.tabbed.AbstractTabbedDialogPanel;
import org.ourproject.kune.platf.client.ui.rte.img.RTEImgResources;

import cc.kune.common.client.notify.NotifyLevel;
import cc.kune.common.client.notify.NotifyLevelImages;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.gwt.user.client.Window;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;

public class EditHtmlDialogPanel extends AbstractTabbedDialogPanel implements EditHtmlDialogView {

    public static final String EDIG_HTML_DIALOG_ERROR_ID = "ehtml-dialgo-error";
    public static final String EDIT_HTML_DIALOG = "ehtml-dialog";
    private Button cancel;
    private final EditHtmlGroup editHtmlGroup;
    private final I18nTranslationService i18n;
    private final EditHtmlDialogPresenter presenter;

    public EditHtmlDialogPanel(final EditHtmlDialogPresenter presenter, final I18nTranslationService i18n,
            final RTEImgResources resources, final NotifyLevelImages images, final EditHtmlGroup editHtmlGroup) {
        super(EDIT_HTML_DIALOG, i18n.tWithNT("Edit the HTML",
                "Option in a content editor to edit the html internal code (for advance users)"),
                Window.getClientWidth() - 100, HEIGHT + 80, Window.getClientWidth() - 100, HEIGHT + 80, true, images,
                EDIG_HTML_DIALOG_ERROR_ID);
        this.presenter = presenter;
        this.i18n = i18n;
        this.editHtmlGroup = editHtmlGroup;
        // super.setIconCls(RTEImgResources.SUFFIX +
        // resources.edithtml().getName());
    }

    @Override
    public void createAndShow() {
        if (cancel == null) {
            cancel = new Button(i18n.t("Cancel"));
            final Button update = new Button(i18n.t("Update"));
            cancel.addListener(new ButtonListenerAdapter() {
                @Override
                public void onClick(final Button button, final EventObject e) {
                    presenter.onCancel();
                }
            });
            update.addListener(new ButtonListenerAdapter() {
                @Override
                public void onClick(final Button button, final EventObject e) {
                    presenter.onUpdate();
                }
            });
            super.addButton(update);
            super.addButton(cancel);
        }
        editHtmlGroup.createAll();
        super.createAndShow();
        super.setErrorMessage(i18n.t("This option is only for advanced users"), NotifyLevel.info);
    }
}
