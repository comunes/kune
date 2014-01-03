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
package cc.kune.core.server.manager.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import cc.kune.common.shared.utils.TextUtils;
import cc.kune.core.server.manager.TagManager;
import cc.kune.core.server.manager.TagUserContentManager;
import cc.kune.core.server.persist.DataSourceKune;
import cc.kune.core.shared.domain.TagCloudResult;
import cc.kune.core.shared.domain.TagCount;
import cc.kune.domain.Content;
import cc.kune.domain.Group;
import cc.kune.domain.Tag;
import cc.kune.domain.TagUserContent;
import cc.kune.domain.User;
import cc.kune.domain.finders.TagUserContentFinder;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

// TODO: Auto-generated Javadoc
/**
 * The Class TagUserContentManagerDefault.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class TagUserContentManagerDefault extends DefaultManager<TagUserContent, Long> implements
    TagUserContentManager {

  /** The finder. */
  private final TagUserContentFinder finder;

  /** The provider. */
  private final Provider<EntityManager> provider;

  /** The tag manager. */
  private final TagManager tagManager;

  /**
   * Instantiates a new tag user content manager default.
   * 
   * @param provider
   *          the provider
   * @param tagManager
   *          the tag manager
   * @param finder
   *          the finder
   */
  @Inject
  public TagUserContentManagerDefault(@DataSourceKune final Provider<EntityManager> provider,
      final TagManager tagManager, final TagUserContentFinder finder) {
    super(provider, TagUserContent.class);
    this.provider = provider;
    this.tagManager = tagManager;
    this.finder = finder;
  }

  /**
   * Find.
   * 
   * @param user
   *          the user
   * @param content
   *          the content
   * @return the list
   */
  public List<Tag> find(final User user, final Content content) {
    return finder.findTags(user, content);
  }

  /**
   * Gets the max count.
   * 
   * @param group
   *          the group
   * @return the max count
   */
  private int getMaxCount(final Group group) {
    return finder.getMaxGrouped(group).intValue();
  }

  /**
   * Gets the min count.
   * 
   * @param group
   *          the group
   * @return the min count
   */
  private int getMinCount(final Group group) {
    return finder.getMinGrouped(group).intValue();
  }

  /**
   * Gets the summary by group.
   * 
   * @param group
   *          the group
   * @return the summary by group
   */
  private List<TagCount> getSummaryByGroup(final Group group) {
    return finder.getTagsGroups(group);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.manager.TagUserContentManager#getTagCloudResultByGroup
   * (cc.kune.domain.Group)
   */
  @Override
  public TagCloudResult getTagCloudResultByGroup(final Group group) {
    try {
      return new TagCloudResult(getSummaryByGroup(group), getMaxCount(group), getMinCount(group));
    } catch (final NoResultException e) {
      return new TagCloudResult();
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.manager.TagUserContentManager#getTagsAsString(cc.kune
   * .domain.User, cc.kune.domain.Content)
   */
  @Override
  public String getTagsAsString(final User user, final Content content) {
    final StringBuffer tagConcatenated = new StringBuffer();
    if (user.getId() != null) {
      // FIXME: User must be persisted (this fails on tests)
      final List<Tag> tags = find(user, content);
      for (final Tag tag : tags) {
        tagConcatenated.append(" ").append(tag.getName());
      }
    }
    return tagConcatenated.toString().replaceFirst(" ", "");
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.manager.TagUserContentManager#remove(cc.kune.domain
   * .User, cc.kune.domain.Content)
   */
  @Override
  public void remove(final User user, final Content content) {
    for (final TagUserContent item : finder.find(user, content)) {
      provider.get().remove(item);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.manager.TagUserContentManager#setTags(cc.kune.domain
   * .User, cc.kune.domain.Content, java.lang.String)
   */
  @Override
  public void setTags(final User user, final Content content, final String tags) {
    final ArrayList<String> tagsStripped = TextUtils.splitTags(tags);
    final ArrayList<Tag> tagList = new ArrayList<Tag>();

    for (final String tagString : tagsStripped) {
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
    remove(user, content);
    for (final Tag tag : tagList) {
      final TagUserContent tagUserContent = new TagUserContent(tag, user, content);
      persist(tagUserContent);
    }
  }
}
