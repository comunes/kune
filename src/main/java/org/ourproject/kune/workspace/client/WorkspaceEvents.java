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

package org.ourproject.kune.workspace.client;

public interface WorkspaceEvents {
    public static final String SHOW_TRANSLATOR = "i18n.ShowTranslator";
    public static final String RELOAD_CONTEXT = "ws.ReoloadContext";
    public static final String WS_SPLITTER_STARTRESIZING = "ws.SplitterStartResizing";
    public static final String WS_SPLITTER_STOPRESIZING = "ws.SplitterStopResizing";
    public static final String RECALCULATE_WORKSPACE_SIZE = "ws.RecalculateWorkspaceSize";
}
