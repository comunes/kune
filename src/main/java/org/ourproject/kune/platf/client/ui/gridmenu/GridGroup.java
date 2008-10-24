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
 */package org.ourproject.kune.platf.client.ui.gridmenu;

public class GridGroup {

    public static GridGroup NoGridGroup = new GridGroup("", "", "", "", false);

    private String name;
    private String tooltipTitle;
    private String tooltip;
    private String endIconHtml;

    public GridGroup(final String adminsTitle, final String tooltipTitle, final String tooltip,
            final boolean countVisible) {
        this(adminsTitle, tooltipTitle, tooltip, "", countVisible);
    }

    public GridGroup(final String name, final String tooltipTitle, final String tooltip, final String endIconHtml,
            final boolean countVisible) {
        this.name = name;
        this.tooltipTitle = tooltipTitle;
        this.tooltip = tooltip;
        this.endIconHtml = endIconHtml;
    }

    GridGroup(final String adminsTitle) {
        this(adminsTitle, "", "", false);
    }

    GridGroup(final String adminsTitle, final String tooltip) {
        this(adminsTitle, "", tooltip, false);
    }

    GridGroup(final String adminsTitle, final String tooltipTitle, final String tooltip) {
        this(adminsTitle, tooltipTitle, tooltip, false);
    }

    public String getEndIconHtml() {
        return endIconHtml;
    }

    public String getName() {
        return name;
    }

    public String getTooltip() {
        return tooltip;
    }

    public String getTooltipTitle() {
        return tooltipTitle;
    }

    public void setEndIconHtml(final String endIconHtml) {
        this.endIconHtml = endIconHtml;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setTooltip(final String tooltip) {
        this.tooltip = tooltip;
    }

    public void setTooltipTitle(final String tooltipTitle) {
        this.tooltipTitle = tooltipTitle;
    }

}
