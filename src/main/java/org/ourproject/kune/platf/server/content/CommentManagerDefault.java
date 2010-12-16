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
 \*/
package org.ourproject.kune.platf.server.content;

import javax.persistence.EntityManager;

import org.ourproject.kune.platf.server.access.FinderService;
import org.ourproject.kune.platf.server.manager.impl.DefaultManager;

import cc.kune.core.client.errors.ContentNotFoundException;
import cc.kune.core.client.errors.DefaultException;
import cc.kune.domain.Comment;
import cc.kune.domain.Content;
import cc.kune.domain.User;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class CommentManagerDefault extends DefaultManager<Comment, Long> implements CommentManager {

    private final FinderService finder;

    @Inject
    public CommentManagerDefault(final Provider<EntityManager> provider, final FinderService finder) {
        super(provider, Comment.class);
        this.finder = finder;
    }

    public Comment addComment(final User author, final Long contentId, final String commentText)
            throws DefaultException {
        Comment comment = createComment(author, commentText, contentId);
        return persist(comment);
    }

    public Comment addComment(final User author, final Long contentId, final String commentText,
            final Long commentParentId) throws DefaultException {
        Comment comment = createComment(author, commentText, contentId);
        Comment parent = finder.getComment(commentParentId);
        comment.setParent(parent);
        return persist(comment);
    }

    public Comment markAsAbuse(final User informer, final Long contentId, final Long commentId) throws DefaultException {
        Comment comment = finder.getComment(commentId);
        checkCommentContent(contentId, comment);
        comment.addAbuseInformer(informer);
        return persist(comment);
    }

    public Comment vote(final User voter, final Long contentId, final Long commentId, final boolean votePositive)
            throws DefaultException {
        Comment comment = finder.getComment(commentId);
        checkCommentContent(contentId, comment);
        if (votePositive) {
            comment.addPositiveVoter(voter);
            return persist(comment);
        } else {
            comment.addNegativeVoter(voter);
            return persist(comment);
        }
    }

    private void checkCommentContent(final Long contentId, final Comment comment) throws ContentNotFoundException {
        if (!comment.getContent().getId().equals(contentId)) {
            throw new ContentNotFoundException();
        }
    }

    private Comment createComment(final User author, final String commentText, final Long contentId)
            throws DefaultException {
        final Content content = finder.getContent(contentId);
        Comment comment = new Comment();
        comment.setAuthor(author);
        comment.setText(commentText);
        comment.setContent(content);
        return comment;
    }

}
