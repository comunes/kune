package org.ourproject.kune.platf.server.content;

import org.ourproject.kune.platf.client.errors.DefaultException;
import org.ourproject.kune.platf.server.domain.Comment;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.Manager;

public interface CommentManager extends Manager<Comment, Long> {

    Comment addComment(User author, Long contentId, String commentText) throws DefaultException;

    Comment addComment(User author, Long contentId, String commentText, Long commentParentId) throws DefaultException;

    Comment markAsAbuse(User informer, Long contentId, Long commentId) throws DefaultException;

    Comment vote(User voter, Long contentId, Long commentId, boolean votePositive) throws DefaultException;

}
