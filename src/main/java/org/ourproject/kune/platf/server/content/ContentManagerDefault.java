/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
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

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.Query;
import org.ourproject.kune.platf.client.errors.DefaultException;
import org.ourproject.kune.platf.client.errors.I18nNotFoundException;
import org.ourproject.kune.platf.client.errors.NameInUseException;
import org.ourproject.kune.platf.client.errors.UserNotFoundException;
import org.ourproject.kune.platf.server.access.FinderService;
import org.ourproject.kune.platf.server.domain.Container;
import org.ourproject.kune.platf.server.domain.Content;
import org.ourproject.kune.platf.server.domain.ContentStatus;
import org.ourproject.kune.platf.server.domain.I18nLanguage;
import org.ourproject.kune.platf.server.domain.Rate;
import org.ourproject.kune.platf.server.domain.RateResult;
import org.ourproject.kune.platf.server.domain.Revision;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.TagUserContentManager;
import org.ourproject.kune.platf.server.manager.file.FileUtils;
import org.ourproject.kune.platf.server.manager.impl.DefaultManager;
import org.ourproject.kune.platf.server.manager.impl.SearchResult;
import org.ourproject.kune.platf.server.utils.FilenameUtils;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class ContentManagerDefault extends DefaultManager<Content, Long> implements ContentManager {

    private final FinderService finder;
    private final User userFinder;
    private final I18nLanguage languageFinder;
    private final Content contentFinder;
    private final Container containerFinder;
    private final TagUserContentManager tagManager;

    @Inject
    public ContentManagerDefault(final Content contentFinder, final Container containerFinder,
            final Provider<EntityManager> provider, final FinderService finder, final User userFinder,
            final I18nLanguage languageFinder, final TagUserContentManager tagManager) {
        super(provider, Content.class);
        this.contentFinder = contentFinder;
        this.containerFinder = containerFinder;
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

    public Content createContent(final String title, final String body, final User author, final Container container,
            final String typeId) {
        FilenameUtils.checkBasicFilename(title);
        String newtitle = findInexistentTitle(container, title);
        final Content newContent = new Content();
        newContent.addAuthor(author);
        newContent.setLanguage(author.getLanguage());
        newContent.setTypeId(typeId);
        container.addContent(newContent);
        newContent.setContainer(container);
        final Revision revision = new Revision(newContent);
        revision.setTitle(newtitle);
        revision.setBody(body);
        newContent.addRevision(revision);
        return persist(newContent);
    }

    public boolean findIfExistsTitle(final Container container, final String title) {
        return (contentFinder.findIfExistsTitle(container, title) > 0)
                || (containerFinder.findIfExistsTitle(container, title) > 0);
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

    public RateResult rateContent(final User rater, final Long contentId, final Double value) throws DefaultException {
        final Content content = finder.getContent(contentId);
        final Rate oldRate = finder.getRate(rater, content);
        if (oldRate == null) {
            final Rate rate = new Rate(rater, content, value);
            super.persist(rate, Rate.class);
        } else {
            oldRate.setValue(value);
            super.persist(oldRate, Rate.class);
        }
        Double rateAvg = getRateAvg(content);
        Long rateByUsers = getRateByUsers(content);
        return new RateResult(rateAvg != null ? rateAvg : 0D, rateByUsers != null ? rateByUsers.intValue() : 0, value);
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

    public Content renameContent(final User user, final Long contentId, final String newTitle) throws DefaultException {
        String newTitleWithoutNL = FilenameUtils.chomp(newTitle);
        FilenameUtils.checkBasicFilename(newTitleWithoutNL);
        final Content content = finder.getContent(contentId);
        if (findIfExistsTitle(content.getContainer(), newTitleWithoutNL)) {
            throw new NameInUseException();
        }
        content.getLastRevision().setTitle(newTitleWithoutNL);
        return content;
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
        final MultiFieldQueryParser parser = createMultiFieldParser();
        Query query;
        try {
            query = parser.parse(search);
        } catch (final ParseException e) {
            throw new RuntimeException("Error parsing search");
        }
        return super.search(query, firstResult, maxResults);
    }

    public SearchResult<Content> searchMime(final String search, final Integer firstResult, final Integer maxResults,
            final String groupShortName, final String mimetype) {
        List<Content> list = contentFinder.findMime(groupShortName, "%" + search + "%", mimetype, firstResult,
                maxResults);
        int count = contentFinder.findMimeCount(groupShortName, "%" + search + "%", mimetype);
        return new SearchResult<Content>(count, list);
    }

    public SearchResult<?> searchMime(final String search, final Integer firstResult, final Integer maxResults,
            final String groupShortName, final String mimetype, final String mimetype2) {
        List<Content> list = contentFinder.find2Mime(groupShortName, "%" + search + "%", mimetype, mimetype2,
                firstResult, maxResults);
        int count = contentFinder.find2MimeCount(groupShortName, "%" + search + "%", mimetype, mimetype2);
        return new SearchResult<Content>(count, list);
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

    public Content setStatus(final Long contentId, final ContentStatus status) {
        final Content content = finder.getContent(contentId);
        content.setStatus(status);
        switch (status) {
        case publishedOnline:
            content.setPublishedOn(new Date());
            content.setDeletedOn(null);
            break;
        case inTheDustbin:
            content.setDeletedOn(new Date());
            content.setPublishedOn(null);
            break;
        default:
            break;
        }
        return content;
    }

    public void setTags(final User user, final Long contentId, final String tags) throws DefaultException {
        final Content content = finder.getContent(contentId);
        tagManager.setTags(user, content, tags);
    }

    private MultiFieldQueryParser createMultiFieldParser() {
        final MultiFieldQueryParser parser = new MultiFieldQueryParser(DEF_GLOBAL_SEARCH_FIELDS, new StandardAnalyzer());
        return parser;
    }

    private String findInexistentTitle(final Container container, final String title) {
        String initialTitle = new String(title);
        while (findIfExistsTitle(container, initialTitle)) {
            initialTitle = FileUtils.getNextSequentialFileName(initialTitle);
        }
        return initialTitle;
    }
}
