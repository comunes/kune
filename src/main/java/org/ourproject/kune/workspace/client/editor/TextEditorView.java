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

import org.ourproject.kune.platf.client.View;

public interface TextEditorView extends View {

    public void editHTML(boolean edit);

    public String getHTML();

    public String getText();

    public View getToolBar();

    public void saveTimerCancel();

    public void scheduleSave(int delayMillis);

    public void setEnabled(boolean enabled);

    public void setEnabledCancelButton(boolean enabled);

    public void setEnabledSaveButton(boolean enabled);

    public void setHeight(String height);

    public void setHTML(String html);

    public void setText(String text);

    public void setTextSaveButton(String text);

    public void setToolBarVisible(boolean visible);

    public void showSaveBeforeDialog();

}