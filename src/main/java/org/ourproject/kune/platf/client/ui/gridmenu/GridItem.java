/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
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
 \*/
package org.ourproject.kune.platf.client.ui.gridmenu;

public class GridItem<T> {

    private GridGroup group;
    private String id;
    private String iconHtml;
    private String title;
    private String titleHtml;
    private String endIconHtml;
    private String tooltip;
    private String tooltipTitle;
    private T item;
    private CustomMenu<T> menu;

    public GridItem(final T item, final GridGroup group, final String id, final String iconHtml, final String title,
            final String titleHtml, final String endIconHtml, final String tooltipTitle, final String tooltip,
            final CustomMenu<T> menu) {
        this.item = item;
        this.group = group;
        this.id = id;
        this.iconHtml = iconHtml;
        this.title = title;
        this.titleHtml = titleHtml;
        this.endIconHtml = endIconHtml;
        this.tooltip = tooltip;
        this.tooltipTitle = tooltipTitle;
        this.menu = menu;
    }

    public String getEndIconHtml() {
        return endIconHtml;
    }

    public GridGroup getGroup() {
        return group;
    }

    public String getIconHtml() {
        return iconHtml;
    }

    public String getId() {
        return id;
    }

    public T getItem() {
        return item;
    }

    public CustomMenu<T> getMenu() {
        return menu;
    }

    public String getTitle() {
        return title;
    }

    public String getTitleHtml() {
        return titleHtml;
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

    public void setGroup(final GridGroup group) {
        this.group = group;
    }

    public void setIconHtml(final String iconHtml) {
        this.iconHtml = iconHtml;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public void setItem(final T item) {
        this.item = item;
    }

    public void setMenu(final CustomMenu<T> menu) {
        this.menu = menu;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public void setTitleHtml(final String titleHtml) {
        this.titleHtml = titleHtml;
    }

    public void setTooltip(final String tooltip) {
        this.tooltip = tooltip;
    }

    public void setTooltipTitle(final String tooltipTitle) {
        this.tooltipTitle = tooltipTitle;
    }

}
