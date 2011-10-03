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
 */
package cc.kune.domain.finders;

import static cc.kune.domain.Content.GROUP;
import static cc.kune.domain.Content.MIMETYPE;
import static cc.kune.domain.Content.TITLE;

import java.util.ArrayList;
import java.util.List;

import cc.kune.core.shared.domain.ContentStatus;
import cc.kune.domain.Container;
import cc.kune.domain.Content;

import com.google.inject.name.Named;
import com.google.inject.persist.finder.Finder;
import com.google.inject.persist.finder.FirstResult;
import com.google.inject.persist.finder.MaxResults;

public interface ContentFinder {
  @Finder(query = "FROM Content WHERE lastRevision.title LIKE :title AND (mimeType.mimetype=:mimetype OR mimeType.mimetype=:mimetype2) AND container.owner.shortName=:group AND deletedOn=null ORDER BY lastRevision.title ASC", returnAs = ArrayList.class)
  public List<Content> find2Mime(@Named(GROUP) final String groupShortName,
      @Named(TITLE) final String title, @Named(MIMETYPE) final String mimetype,
      @Named("mimetype2") final String mimetype2, @FirstResult final int offset,
      @MaxResults final int limit);

  @Finder(query = "SELECT count(id) FROM Content WHERE lastRevision.title LIKE :title AND (mimeType.mimetype=:mimetype OR mimeType.mimetype=:mimetype2) AND container.owner.shortName=:group AND deletedOn=null")
  public Long find2MimeCount(@Named(GROUP) final String groupShortName,
      @Named(TITLE) final String title, @Named(MIMETYPE) final String mimetype,
      @Named("mimetype2") final String mimetype2);

  @Finder(query = "SELECT count(*) from Container ctx, Content ctn where ctn.container.id = ctx.id and ctx = :container and ctn.lastRevision.title LIKE :title")
  public Long findIfExistsTitle(@Named("container") final Container container,
      @Named(TITLE) final String title);

  @Finder(query = "FROM Content WHERE lastRevision.title LIKE :title AND mimeType.mimetype=:mimetype AND container.owner.shortName=:group AND deletedOn=null ORDER BY lastRevision.title ASC", returnAs = ArrayList.class)
  public List<Content> findMime(@Named(GROUP) final String groupShortName,
      @Named(TITLE) final String title, @Named(MIMETYPE) final String mimetype,
      @FirstResult final int offset, @MaxResults final int limit);

  @Finder(query = "SELECT count(id) FROM Content WHERE lastRevision.title LIKE :title AND mimeType.mimetype=:mimetype AND container.owner.shortName=:group AND deletedOn=null")
  public Long findMimeCount(@Named(GROUP) final String groupShortName, @Named(TITLE) final String title,
      @Named(MIMETYPE) final String mimetype);

  @Finder(query = "FROM Content c WHERE c.status = :status ORDER BY c.modifiedOn DESC", returnAs = ArrayList.class)
  public List<Content> lastModifiedContents(@MaxResults final int limit,
      @Named("status") final ContentStatus status);

  @Finder(query = "FROM Content c WHERE " + "((c.container.owner.id IN (SELECT ed.id FROM "
      + "c.container.owner.socialNetwork.accessLists.editors.list AS ed WHERE ed.id = :groupid)) OR"
      + "(c.container.owner.id IN (SELECT ad.id FROM "
      + "c.container.owner.socialNetwork.accessLists.admins.list AS ad WHERE ad.id = :groupid)))"
      + "ORDER BY c.modifiedOn DESC", returnAs = ArrayList.class)
  public List<Content> lastModifiedContentsInUserGroup(@MaxResults final int limit,
      @Named("groupid") Long groupId);
}
