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
package org.ourproject.kune.platf.client.ui.rte.saving;

import org.ourproject.kune.platf.client.actions.ui.GuiBindingsRegister;
import org.ourproject.kune.platf.client.shortcuts.GlobalShortcutRegister;
import org.ourproject.kune.platf.client.ui.noti.OldNotifyUser;
import org.ourproject.kune.platf.client.ui.rte.basic.RTEditorPanel;
import org.ourproject.kune.platf.client.ui.rte.basic.RTEditorPresenter;

import cc.kune.core.client.i18n.I18nUITranslationService;

import com.calclab.suco.client.events.Listener0;

public class RTESavingEditorPanel extends RTEditorPanel implements RTESavingEditorView {

    public RTESavingEditorPanel(final RTEditorPresenter presenter, final I18nUITranslationService i18n,
            final GlobalShortcutRegister globalShortcutReg, final GuiBindingsRegister bindReg) {
        super(presenter, i18n, globalShortcutReg, bindReg);
    }

    public void askConfirmation(final String confirmationTitle, final String confirmationText,
            final Listener0 onConfirm, final Listener0 onCancel) {
        OldNotifyUser.askConfirmation(confirmationTitle, confirmationText, onConfirm, onCancel);
    }

}
