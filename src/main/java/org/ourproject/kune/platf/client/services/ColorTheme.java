/*
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

package org.ourproject.kune.platf.client.services;

import com.google.gwt.i18n.client.Dictionary;

public class ColorTheme {

    private Dictionary theme;

    public ColorTheme() {
        this("defaultKuneTheme");
    }

    public ColorTheme(final String theme) {
        this.theme = Dictionary.getDictionary(theme);
    }

    public void setTheme(final String theme) {
        this.theme = Dictionary.getDictionary(theme);
    }

    public String getThemeName() {
        return theme.get("themeName");
    }

    public String getToolSelected() {
        return theme.get("toolSelected");
    }

    public String getToolUnselected() {
        return theme.get("toolUnselected");
    }

    public String getContentMainBorder() {
        return theme.get("contentMainBorder");
    }

    public String getContentTitle() {
        return theme.get("contentTitle");
    }

    public String getContentTitleText() {
        return theme.get("contentTitleText");
    }

    public String getContentSubTitleText() {
        return theme.get("contentSubTitleText");
    }

    public String getContentBottomText() {
        return theme.get("contentBottomText");
    }

    public String getContext() {
        return theme.get("context");
    }

    public String getSplitter() {
        return theme.get("splitter");
    }

    public String getGroupMembersDD() {
        return theme.get("groupMembersDD");
    }

    public String getParticipationDD() {
        return theme.get("participationDD");
    }

    public String getSummaryDD() {
        return theme.get("summaryDD");
    }

    public String getTagsDD() {
        return theme.get("tagsDD");
    }

}