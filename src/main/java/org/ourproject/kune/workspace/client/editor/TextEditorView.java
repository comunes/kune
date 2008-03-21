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

package org.ourproject.kune.workspace.client.editor;

import org.ourproject.kune.platf.client.View;

public interface TextEditorView extends View {

    public void editHTML(boolean edit);

    public String getHTML();

    public String getText();

    public void setEnabled(boolean enabled);

    public void setEnabledCancelButton(boolean enabled);

    public void setEnabledSaveButton(boolean enabled);

    public void setHeight(String height);

    public void setHTML(String html);

    public void setText(String text);

    public void setTextSaveButton(String text);

    public void showSaveBeforeDialog();

    public void scheduleSave(int delayMillis);

    public void saveTimerCancel();

    public View getToolBar();

}