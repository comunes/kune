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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.Query;
import org.ourproject.kune.platf.server.domain.Container;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.I18nLanguage;
import org.ourproject.kune.platf.server.manager.impl.DefaultManager;
import org.ourproject.kune.platf.server.manager.impl.SearchResult;

import com.google.gwt.user.client.rpc.SerializableException;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class ContainerManagerDefault extends DefaultManager<Container, Long> implements ContainerManager {

    @Inject
    public ContainerManagerDefault(final Provider<EntityManager> provider) {
        super(provider, Container.class);
    }

    public Container createRootFolder(final Group group, final String toolName, final String name, final String type) {
        Container container = new Container(name, group, toolName);
        container.setTypeId(type);
        List<Container> absolutePath = new ArrayList<Container>();
        absolutePath.add(container);
        container.setAbsolutePath(absolutePath);
        return persist(container);
    }

    public Container createFolder(final Group group, final Container parent, final String name,
            final I18nLanguage language) {
        List<Container> parentAbsolutePath = parent.getAbsolutePath();
        List<Container> childAbsolutePath = new ArrayList<Container>();

        for (Iterator<Container> iterator = parentAbsolutePath.iterator(); iterator.hasNext();) {
            Container parentRef = iterator.next();
            childAbsolutePath.add(parentRef);
        }
        Container child = new Container(name, group, parent.getToolName());
        childAbsolutePath.add(child);
        child.setLanguage(language);
        child.setAbsolutePath(childAbsolutePath);
        parent.addChild(child);
        // child.setParent(parent);
        persist(child);
        return child;
    }

    public String renameFolder(final Group group, final Container container, final String newName)
            throws SerializableException {
        if (container.isRoot()) {
            throw new RuntimeException("Root folder cannot be renamed");
        }
        container.setName(newName);
        persist(container);
        return newName;
    }

    public SearchResult<Container> search(final String search) {
        return this.search(search, null, null);
    }

    public SearchResult<Container> search(final String search, final Integer firstResult, final Integer maxResults) {
        MultiFieldQueryParser parser = new MultiFieldQueryParser(new String[] { "name" }, new StandardAnalyzer());
        Query query;
        try {
            query = parser.parse(search);
        } catch (ParseException e) {
            throw new RuntimeException("Error parsing search");
        }
        return super.search(query, firstResult, maxResults);
    }

}
