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

package org.ourproject.kune.platf.server.manager.impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.ourproject.kune.platf.server.manager.SearchManager;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class SearchManagerDefault implements SearchManager {
    private final Provider<EntityManager> provider;

    @Inject
    public SearchManagerDefault(final Provider<EntityManager> provider) {
        this.provider = provider;
    }

    private EntityManager getEntityManager() {
        return provider.get();
    }

    public List search(final String search) {
        return this.search(search, null, null);
    }

    public List search(final String search, final Integer firstResult, final Integer maxResults) {
        Query query;
        QueryParser parser = new QueryParser("name", new StandardAnalyzer());
        try {
            query = parser.parse(search);
        } catch (ParseException e) {
            throw new RuntimeException("Error parsing search");

        }
        return search(query, firstResult, maxResults);
    }

    private List search(final Query query, final Integer firstResult, final Integer maxResults) {
        // TODO: Inject this
        FullTextEntityManager fullTextEm = org.hibernate.search.jpa.Search
                .createFullTextEntityManager(getEntityManager());
        FullTextQuery emQuery = fullTextEm.createFullTextQuery(query);
        if (firstResult != null && maxResults != null) {
            emQuery.setFirstResult(firstResult);
            emQuery.setMaxResults(maxResults);
        }
        return emQuery.getResultList();

    }

}
