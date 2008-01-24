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
import org.ourproject.kune.blogs.client.cnt.BlogContentListener;
import org.ourproject.kune.blogs.client.ctx.BlogContext;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.platf.client.tool.AbstractClientTool;
import org.ourproject.kune.workspace.client.component.WorkspaceComponent;
import org.ourproject.kune.workspace.client.dto.StateDTO;

public class BlogClientTool extends AbstractClientTool implements BlogContentListener {
    public static final String TYPE_ROOT = "blogs.root";
    public static final String TYPE_FOLDER = "blogs.blog";
    public static final String TYPE_DOCUMENT = "blogs.post";
    public static final String NAME = "blogs";
    private final BlogToolComponents components;

    public BlogClientTool() {
        super(Kune.I18N.t("blogs"));
        components = new BlogToolComponents(this);
    }

    public WorkspaceComponent getContent() {
        return components.getContent();
    }

    public WorkspaceComponent getContext() {
        return components.getContext();
    }

    public String getName() {
        return NAME;
    }

    public void setContent(final StateDTO state) {
        BlogContent docContent = components.getContent();
        docContent.setContent(state);

        // TODO: revistar el interface de trigger (setState)
        trigger.setState(state.getStateToken().toString());
    }

    public void setContext(final StateDTO state) {
        BlogContext context = components.getContext();
        context.setContext(state);
    }

    public void onEdit() {
        components.getContext().showAdmin();
    }

    public void onCancel() {
        components.getContext().showFolders();
    }

}
