/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
package cc.kune.core.server.content;


import cc.kune.core.client.errors.DefaultException;
import cc.kune.core.server.manager.Manager;
import cc.kune.domain.Comment;
import cc.kune.domain.User;

public interface CommentManager extends Manager<Comment, Long> {

    Comment addComment(User author, Long contentId, String commentText) throws DefaultException;

    Comment addComment(User author, Long contentId, String commentText, Long commentParentId) throws DefaultException;

    Comment markAsAbuse(User informer, Long contentId, Long commentId) throws DefaultException;

    Comment vote(User voter, Long contentId, Long commentId, boolean votePositive) throws DefaultException;

}
