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

import org.ourproject.kune.platf.client.ui.rte.basic.RTEditor;

import cc.kune.common.client.actions.BeforeActionListener;

import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.events.Listener0;

public interface RTESavingEditor extends RTEditor {

    /**
     * Start to edit some html code
     * 
     * @param html
     *            the html text to edit
     * @param onSave
     *            the save action (when user click the save button)
     * @param onEditCancelled
     *            the cancel action (when user click the cancel/close button)
     */
    void edit(String html, Listener<String> onSave, Listener0 onEditCancelled);

    /**
     * This is used for listen to changes that affect the edition as url
     * changes, window close, to save/cancel or stop these actions
     * 
     * @return the listener
     */
    BeforeActionListener getBeforeSavingListener();

    /**
     * Checks if is save is pending.
     * 
     * @return true, if is save pending
     */
    boolean isSavePending();

    /**
     * Call this when your save action is successful
     */
    void onSavedSuccessful();

    /**
     * Call this when your save action fails
     */
    void onSaveFailed();

}
