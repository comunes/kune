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

import org.ourproject.kune.docs.client.actions.AddDocument;
import org.ourproject.kune.docs.client.actions.AddFolder;
import org.ourproject.kune.docs.client.actions.DocsEvents;
import org.ourproject.kune.docs.client.actions.GoParentFolder;
import org.ourproject.kune.docs.client.actions.SaveDocument;
import org.ourproject.kune.platf.client.extend.ClientModule;
import org.ourproject.kune.platf.client.extend.Register;

public class DocsClientModule implements ClientModule {
    public void configure(final Register register) {
	register.addTool(new DocumentClientTool());
	register.addAction(DocsEvents.SAVE_DOCUMENT, new SaveDocument());
	register.addAction(DocsEvents.ADD_DOCUMENT, new AddDocument());
	register.addAction(DocsEvents.ADD_FOLDER, new AddFolder());
	register.addAction(DocsEvents.GO_PARENT_FOLDER, new GoParentFolder());
    }
}
