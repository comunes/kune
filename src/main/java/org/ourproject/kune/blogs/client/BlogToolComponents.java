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

import org.ourproject.kune.blogs.client.cnt.BlogContent;
import org.ourproject.kune.blogs.client.ctx.BlogContext;
import org.ourproject.kune.blogs.client.ui.BlogFactory;

class BlogToolComponents {
    private BlogContent content;
    private BlogContext context;
    private final BlogClientTool blogClientTool;

    public BlogToolComponents(final BlogClientTool blogClientTool) {
        this.blogClientTool = blogClientTool;
    }

    public BlogContent getContent() {
        if (content == null) {
            content = BlogFactory.createDocumentContent(blogClientTool);
        }
        return content;
    }

    public BlogContext getContext() {
        if (context == null) {
            context = BlogFactory.createDocumentContext();
        }
        return context;
    }

}
