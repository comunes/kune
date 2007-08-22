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

package org.ourproject.kune.docs.client;

import org.ourproject.kune.docs.client.ui.DocumentFactory;
import org.ourproject.kune.docs.client.ui.cnt.DocumentContent;
import org.ourproject.kune.docs.client.ui.ctx.DocumentContext;

class DocToolComponents {
    private DocumentContent content;
    private DocumentContext context;
    private final DocumentClientTool documentClientTool;

    public DocToolComponents(final DocumentClientTool documentClientTool) {
	this.documentClientTool = documentClientTool;
    }

    public DocumentContent getContent() {
	if (content == null) {
	    content = DocumentFactory.createDocumentContent(documentClientTool);
	}
	return content;
    }

    public DocumentContext getContext() {
	if (context == null) {
	    context = DocumentFactory.createDocumentContext();
	}
	return context;
    }

}
