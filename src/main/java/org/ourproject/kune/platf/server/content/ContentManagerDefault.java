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

package org.ourproject.kune.platf.server.content;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.Query;
import org.ourproject.kune.platf.client.errors.DefaultException;
import org.ourproject.kune.platf.client.errors.I18nNotFoundException;
import org.ourproject.kune.platf.client.errors.UserNotFoundException;
import org.ourproject.kune.platf.client.ui.KuneStringUtils;
import org.ourproject.kune.platf.server.access.FinderService;
import org.ourproject.kune.platf.server.domain.Container;
import org.ourproject.kune.platf.server.domain.Content;
import org.ourproject.kune.platf.server.domain.ContentStatus;
import org.ourproject.kune.platf.server.domain.I18nLanguage;
import org.ourproject.kune.platf.server.domain.Rate;
import org.ourproject.kune.platf.server.domain.Revision;
import org.ourproject.kune.platf.server.domain.Tag;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.TagManager;
import org.ourproject.kune.platf.server.manager.impl.DefaultManager;
import org.ourproject.kune.platf.server.manager.impl.SearchResult;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class ContentManagerDefault extends DefaultManager<Content, Long> implements ContentManager {

    private final FinderService finder;
    private final User userFinder;
    private final I18nLanguage languageFinder;
    private final TagManager tagManager;

    @Inject
    public ContentManagerDefault(final Provider<EntityManager> provider, final FinderService finder,
            final User userFinder, final I18nLanguage languageFinder, final TagManager tagManager) {
        super(provider, Content.class);
        this.finder = finder;
        this.userFinder = userFinder;
        this.languageFinder = languageFinder;
        this.tagManager = tagManager;
    }

    public void addAuthor(final User user, final Long contentId, final String authorShortName) throws DefaultException {
        final Content content = finder.getContent(contentId);
        final User author = userFinder.getByShortName(authorShortName);
        if (author == null) {
            throw new UserNotFoundException();
        }
        content.addAuthor(author);
    }

    public Content createContent(final String title, final String body, final User author, final Container container) {
        final Content newContent = new Content();
        newContent.addAuthor(author);
        newContent.setLanguage(author.getLanguage());
        // FIXME: remove this when UI take publishing into account
        newContent.setPublishedOn(new Date());
        container.addContent(newContent);
        newContent.setContainer(container);
        final Revision revision = new Revision(newContent);
        revision.setTitle(title);
        revision.setBody(body);
        newContent.addRevision(revision);
        return persist(newContent);
    }

    public void delContent(final User user, final Long contentId) throws DefaultException {
        final Content content = finder.getContent(contentId);
        content.setStatus(ContentStatus.inTheDustbin);
        content.setDeletedOn(new Date());
    }

    public Double getRateAvg(final Content content) {
        return finder.getRateAvg(content);
    }

    public Long getRateByUsers(final Content content) {
        return finder.getRateByUsers(content);
    }

    public Double getRateContent(final User rater, final Content content) {
        final Rate rate = finder.getRate(rater, content);
        if (rate != null) {
            return rate.getValue();
        } else {
            return null;
        }
    }

    public void rateContent(final User rater, final Long contentId, final Double value) throws DefaultException {
        final Content content = finder.getContent(contentId);
        final Rate oldRate = finder.getRate(rater, content);
        if (oldRate == null) {
            final Rate rate = new Rate(rater, content, value);
            super.persist(rate, Rate.class);
        } else {
            oldRate.setValue(value);
            super.persist(oldRate, Rate.class);
        }
    }

    public void removeAuthor(final User user, final Long contentId, final String authorShortName)
            throws DefaultException {
        final Content content = finder.getContent(contentId);
        final User author = userFinder.getByShortName(authorShortName);
        if (author == null) {
            throw new UserNotFoundException();
        }
        content.removeAuthor(author);
    }

    public String renameContent(final User user, final Long contentId, final String newTitle) throws DefaultException {
        final Content content = finder.getContent(contentId);
        content.getLastRevision().setTitle(newTitle);
        return newTitle;
    }

    public Content save(final User editor, final Content content, final String body) {
        final Revision revision = new Revision(content);
        revision.setEditor(editor);
        revision.setTitle(content.getTitle());
        revision.setBody(body);
        content.addRevision(revision);
        return persist(content);
    }

    public SearchResult<Content> search(final String search) {
        return this.search(search, null, null);
    }

    public SearchResult<Content> search(final String search, final Integer firstResult, final Integer maxResults) {
        final MultiFieldQueryParser parser = new MultiFieldQueryParser(new String[] { "authors.name",
                "authors.shortName", "container.name", "language.code", "language.englishName", "language.nativeName",
                "lastRevision.body", "lastRevision.title", "tags.name" }, new StandardAnalyzer());
        Query query;
        try {
            query = parser.parse(search);
        } catch (final ParseException e) {
            throw new RuntimeException("Error parsing search");
        }
        return super.search(query, firstResult, maxResults);
    }

    public I18nLanguage setLanguage(final User user, final Long contentId, final String languageCode)
            throws DefaultException {
        final Content content = finder.getContent(contentId);
        final I18nLanguage language = languageFinder.findByCode(languageCode);
        if (language == null) {
            throw new I18nNotFoundException();
        }
        content.setLanguage(language);
        return language;
    }

    public void setPublishedOn(final User user, final Long contentId, final Date publishedOn) throws DefaultException {
        final Content content = finder.getContent(contentId);
        content.setPublishedOn(publishedOn);
    }

    public void setStatus(final Long contentId, final ContentStatus status) {
        final Content content = finder.getContent(contentId);
        content.setStatus(status);
    }

    public void setTags(final User user, final Long contentId, final String tags) throws DefaultException {
        final Content content = finder.getContent(contentId);
        final ArrayList<String> tagsStripped = KuneStringUtils.splitTags(tags);
        final ArrayList<Tag> tagList = new ArrayList<Tag>();
        for (String tagString : tagsStripped) {
            Tag tag;
            try {
                tag = tagManager.findByTagName(tagString);
            } catch (final NoResultException e) {
                tag = new Tag(tagString);
                tagManager.persist(tag);
            }
            if (!tagList.contains(tag)) {
                tagList.add(tag);
            }
        }
        content.setTags(tagList);
    }

}
