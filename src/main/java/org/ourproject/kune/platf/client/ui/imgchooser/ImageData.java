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
/*
 * GWT-Ext Widget Library
 * Copyright(c) 2007-2008, GWT-Ext.
 * licensing@gwt-ext.com
 *
 * http://www.gwt-ext.com/license
 */
package org.ourproject.kune.platf.client.ui.imgchooser;

public class ImageData {
    private String name = null;
    private String url = null;
    private String fileName = null;
    private long size = 0;
    private String searchString = null;
    private String foundLocation = null;
    private String keyword[] = null;
    private String lastModified = null;

    public void clear() {
        name = null;
        url = null;
        fileName = null;
        size = 0;
        searchString = null;
        foundLocation = null;
        keyword = null;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFoundLocation() {
        return foundLocation;
    }

    public String[] getKeyword() {
        return keyword;
    }

    public String getLastModified() {
        return lastModified;
    }

    public String getName() {
        return name;
    }

    public String getSearchString() {
        return searchString;
    }

    public long getSize() {
        return size;
    }

    public String getUrl() {
        return url;
    }

    public void setFileName(final String fileName) {
        this.fileName = fileName;
    }

    public void setFoundLocation(final String foundLocation) {
        this.foundLocation = foundLocation;
    }

    public void setKeyword(final String[] keyword) {
        this.keyword = keyword;
    }

    public void setLastModified(final String lastModified) {
        this.lastModified = lastModified;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setSearchString(final String searchString) {
        this.searchString = searchString;
    }

    public void setSize(final long size) {
        this.size = size;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("ImageData[");
        buffer.append("fileName = ").append(fileName);
        buffer.append("; foundLocation = ").append(foundLocation);
        if (keyword == null) {
            buffer.append("; keyword = ").append("null");
        } else {
            for (int i = 0; i < keyword.length; i++) {
                if (i > 0) {
                    buffer.append(',');
                }
                buffer.append('[');
                buffer.append(i);
                buffer.append("]:");
                buffer.append(keyword[i]);
            }
        }
        buffer.append("; lastModified = ").append(lastModified);
        buffer.append("; name = ").append(name);
        buffer.append("; searchString = ").append(searchString);
        buffer.append("; size = ").append(size);
        buffer.append("; url = ").append(url);
        buffer.append("]");
        return buffer.toString();
    }
}
