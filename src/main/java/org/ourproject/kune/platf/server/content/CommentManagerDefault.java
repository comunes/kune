package org.ourproject.kune.platf.server.content;

import javax.persistence.EntityManager;

import org.ourproject.kune.platf.client.errors.DefaultException;
import org.ourproject.kune.platf.server.access.FinderService;
import org.ourproject.kune.platf.server.domain.Comment;
import org.ourproject.kune.platf.server.domain.Content;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.impl.DefaultManager;

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

    public Comment markAsAbuse(final User informer, final Long commentId) throws DefaultException {
        Comment comment = finder.getComment(commentId);
        comment.addAbuseInformer(informer);
        return persist(comment);
    }

    public Comment vote(final User voter, final Long commentId, final boolean votePositive) throws DefaultException {
        Comment comment = finder.getComment(commentId);
        if (votePositive) {
            comment.addPositiveVoter(voter);
            return persist(comment);
        } else {
            comment.addNegativeVoter(voter);
            return persist(comment);
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
