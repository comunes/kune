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

package org.ourproject.kune.blogs.client.actions;

public interface BlogsEvents {
    public static final String ADD_DOCUMENT = "docs.addDocument";
    public static final String ADD_FOLDER = "docs.AddFolder";
    public static final String GO_PARENT_FOLDER = "platf.GoParentFolder";
    public static final String SAVE_DOCUMENT = "docs.SaveDocument";
    public static final String ADD_AUTHOR = "docs.addAuthor";
    public static final String REMOVE_AUTHOR = "docs.removeAuthor";
    public static final String SET_LANGUAGE = "docs.setLanguage";
    public static final String SET_PUBLISHED_ON = "docs.setPublishedOn";
    public static final String SET_TAGS = "docs.setTags";
    public static final String RENAME_CONTENT = "docs.setTitle";
    public static final String RENAME_TOKEN = "docs.RenameToken";
    public static final String DEL_CONTENT = "docs.delContent";
}
