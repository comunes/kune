/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
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

import static cc.kune.domain.Content.*;

import java.util.ArrayList;
import java.util.List;

import cc.kune.core.shared.domain.ContentStatus;
import cc.kune.core.shared.dto.GroupType;
import cc.kune.domain.Container;
import cc.kune.domain.Content;

import com.google.inject.name.Named;
import com.google.inject.persist.finder.Finder;
import com.google.inject.persist.finder.FirstResult;
import com.google.inject.persist.finder.MaxResults;

// TODO: Auto-generated Javadoc
/**
 * The Interface ContentFinder.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public interface ContentFinder {

  /**
   * All contents in user group.
   * 
   * @param groupId
   *          the group id
   * @return the list
   */
  @Finder(query = "FROM Content c WHERE " + "((:groupid IN (SELECT ed.id FROM "
      + "c.container.owner.socialNetwork.accessLists.editors.list AS ed)) OR"
      + "(:groupid IN (SELECT ad.id FROM "
      + "c.container.owner.socialNetwork.accessLists.admins.list AS ad)))"
      + "ORDER BY c.modifiedOn DESC", returnAs = ArrayList.class)
  public List<Content> allContentsInUserGroup(@Named("groupid") Long groupId);

  /**
   * Find2 mime.
   * 
   * @param groupShortName
   *          the group short name
   * @param title
   *          the title
   * @param mimetype
   *          the mimetype
   * @param mimetype2
   *          the mimetype2
   * @param offset
   *          the offset
   * @param limit
   *          the limit
   * @return the list
   */
  @Finder(query = "FROM Content WHERE lastRevision.title LIKE :title AND (mimeType.mimetype=:mimetype OR mimeType.mimetype=:mimetype2) AND container.owner.shortName=:group AND deletedOn=null ORDER BY lastRevision.title ASC", returnAs = ArrayList.class)
  public List<Content> find2Mime(@Named(GROUP) final String groupShortName,
      @Named(TITLE) final String title, @Named(MIMETYPE) final String mimetype,
      @Named("mimetype2") final String mimetype2, @FirstResult final int offset,
      @MaxResults final int limit);

  /**
   * Find2 mime count.
   * 
   * @param groupShortName
   *          the group short name
   * @param title
   *          the title
   * @param mimetype
   *          the mimetype
   * @param mimetype2
   *          the mimetype2
   * @return the long
   */
  @Finder(query = "SELECT count(id) FROM Content WHERE lastRevision.title LIKE :title AND (mimeType.mimetype=:mimetype OR mimeType.mimetype=:mimetype2) AND container.owner.shortName=:group AND deletedOn=null")
  public Long find2MimeCount(@Named(GROUP) final String groupShortName,
      @Named(TITLE) final String title, @Named(MIMETYPE) final String mimetype,
      @Named("mimetype2") final String mimetype2);

  /**
   * Find by wave id.
   * 
   * @param waveId
   *          the wave id
   * @return the content
   */
  @Finder(query = "FROM Content c WHERE waveId = :waveId")
  public Content findByWaveId(@Named("waveId") final String waveId);

  /**
   * Find if exists title.
   * 
   * @param container
   *          the container
   * @param title
   *          the title
   * @return the long
   */
  @Finder(query = "SELECT count(*) from Container ctx, Content ctn where ctn.container.id = ctx.id and ctx = :container and ctn.lastRevision.title LIKE :title")
  public Long findIfExistsTitle(@Named("container") final Container container,
      @Named(TITLE) final String title);

  /**
   * Find mime.
   * 
   * @param groupShortName
   *          the group short name
   * @param title
   *          the title
   * @param mimetype
   *          the mimetype
   * @param offset
   *          the offset
   * @param limit
   *          the limit
   * @return the list
   */
  @Finder(query = "FROM Content WHERE lastRevision.title LIKE :title AND mimeType.mimetype=:mimetype AND container.owner.shortName=:group AND deletedOn=null ORDER BY lastRevision.title ASC", returnAs = ArrayList.class)
  public List<Content> findMime(@Named(GROUP) final String groupShortName,
      @Named(TITLE) final String title, @Named(MIMETYPE) final String mimetype,
      @FirstResult final int offset, @MaxResults final int limit);

  /**
   * Find mime count.
   * 
   * @param groupShortName
   *          the group short name
   * @param title
   *          the title
   * @param mimetype
   *          the mimetype
   * @return the long
   */
  @Finder(query = "SELECT count(id) FROM Content WHERE lastRevision.title LIKE :title AND mimeType.mimetype=:mimetype AND container.owner.shortName=:group AND deletedOn=null")
  public Long findMimeCount(@Named(GROUP) final String groupShortName, @Named(TITLE) final String title,
      @Named(MIMETYPE) final String mimetype);

  /**
   * Last modified contents.
   * 
   * @param limit
   *          the limit
   * @param status
   *          the status
   * @param excludedGroupType
   *          the excluded group type
   * @return the list
   */
  @Finder(query = "FROM Content c WHERE c.status = :status AND c.container.owner.groupType != :notgrouptype ORDER BY c.modifiedOn DESC ", returnAs = ArrayList.class)
  public List<Content> lastModifiedContents(@MaxResults final int limit,
      @Named("status") final ContentStatus status,
      @Named("notgrouptype") final GroupType excludedGroupType);

  /**
   * Last modified contents in user group.
   * 
   * @param limit
   *          the limit
   * @param groupId
   *          the group id
   * @return the list
   */
  @Finder(query = "FROM Content c WHERE " + "((:groupid IN (SELECT ed.id FROM "
      + "c.container.owner.socialNetwork.accessLists.editors.list AS ed)) OR"
      + "(:groupid IN (SELECT ad.id FROM "
      + "c.container.owner.socialNetwork.accessLists.admins.list AS ad)))"
      + "ORDER BY c.modifiedOn DESC", returnAs = ArrayList.class)
  public List<Content> lastModifiedContentsInUserGroup(@MaxResults final int limit,
      @Named("groupid") Long groupId);

  // @Finder(query = "FROM Content c WHERE " +
  // "(c.container.owner.id IN (SELECT ad.id"
  // + "FROM Group ad WHERE ad.id IN (SELECT ad.id "
  // +
  // "FROM ad.socialNetwork.accessLists.admins.list adm WHERE adm.id = :groupid)) OR "
  // + "(c.container.owner.id IN (SELECT ed.id" +
  // "FROM Group ed WHERE ed.id IN (SELECT ed.id "
  // +
  // "FROM ed.socialNetwork.accessLists.editors.list eds WHERE eds.id = :groupid))))"
  // + "ORDER BY c.modifiedOn DESC", returnAs = ArrayList.class)
  // public List<Content> lastModifiedContentsInUserGroup(@MaxResults final int
  // limit,
  // @Named("groupid") Long groupId);
}
