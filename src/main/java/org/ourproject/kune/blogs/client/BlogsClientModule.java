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

package org.ourproject.kune.blogs.client;

import org.ourproject.kune.blogs.client.actions.AddDocument;
import org.ourproject.kune.blogs.client.actions.AddFolder;
import org.ourproject.kune.blogs.client.actions.ContentAddAuthorAction;
import org.ourproject.kune.blogs.client.actions.ContentDelContentAction;
import org.ourproject.kune.blogs.client.actions.ContentRemoveAuthorAction;
import org.ourproject.kune.blogs.client.actions.ContentSetLanguageAction;
import org.ourproject.kune.blogs.client.actions.ContentSetPublishedOnAction;
import org.ourproject.kune.blogs.client.actions.ContentSetTagsAction;
import org.ourproject.kune.blogs.client.actions.ContentRenameAction;
import org.ourproject.kune.blogs.client.actions.BlogsEvents;
import org.ourproject.kune.blogs.client.actions.GoParentFolder;
import org.ourproject.kune.blogs.client.actions.RenameTokenAction;
import org.ourproject.kune.blogs.client.actions.SaveDocument;
import org.ourproject.kune.platf.client.extend.ClientModule;
import org.ourproject.kune.platf.client.extend.Register;

public class BlogsClientModule implements ClientModule {
    public void configure(final Register register) {
        register.addTool(new BlogClientTool());
        register.addAction(BlogsEvents.SAVE_DOCUMENT, new SaveDocument());
        register.addAction(BlogsEvents.ADD_DOCUMENT, new AddDocument());
        register.addAction(BlogsEvents.ADD_FOLDER, new AddFolder());
        register.addAction(BlogsEvents.GO_PARENT_FOLDER, new GoParentFolder());
        register.addAction(BlogsEvents.ADD_AUTHOR, new ContentAddAuthorAction());
        register.addAction(BlogsEvents.REMOVE_AUTHOR, new ContentRemoveAuthorAction());
        register.addAction(BlogsEvents.SET_LANGUAGE, new ContentSetLanguageAction());
        register.addAction(BlogsEvents.SET_PUBLISHED_ON, new ContentSetPublishedOnAction());
        register.addAction(BlogsEvents.SET_TAGS, new ContentSetTagsAction());
        register.addAction(BlogsEvents.RENAME_CONTENT, new ContentRenameAction());
        register.addAction(BlogsEvents.DEL_CONTENT, new ContentDelContentAction());
        register.addAction(BlogsEvents.RENAME_TOKEN, new RenameTokenAction());
    }
}
