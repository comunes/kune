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

package org.ourproject.kune.platf.server.content;

import javax.persistence.EntityManager;

import org.ourproject.kune.platf.server.domain.Content;
import org.ourproject.kune.platf.server.domain.Container;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.Revision;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.impl.DefaultManager;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class ContentManagerDefault extends DefaultManager<Content, Long> implements ContentManager {

    @Inject
    public ContentManagerDefault(final Provider<EntityManager> provider) {
	super(provider, Content.class);
    }

    public Content createContent(final String title, final User user, final Container container) {
	Content descriptor = new Content();
	descriptor.setCreator(user.getUserGroup());
	descriptor.setFolder(container);
	container.addContent(descriptor);
	Revision revision = new Revision();
	revision.setTitle(title);
	descriptor.addRevision(revision);
	return persist(descriptor);
    }

    public Content save(final Group userGroup, final Content descriptor, final String content) {
	Revision revision = new Revision();
	revision.setTitle(descriptor.getTitle());
	revision.setDataContent(content);
	descriptor.addRevision(revision);
	return persist(descriptor);
    }
}
