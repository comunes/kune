/*
 *
 * Copyright (C) 2007-2013 Licensed to the Comunes Association (CA) under
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

import java.util.ArrayList;
import java.util.List;

import cc.kune.core.shared.domain.TagCount;
import cc.kune.domain.Content;
import cc.kune.domain.Group;
import cc.kune.domain.Tag;
import cc.kune.domain.TagUserContent;
import cc.kune.domain.User;

import com.google.inject.name.Named;
import com.google.inject.persist.finder.Finder;

public interface TagUserContentFinder {

  @Finder(query = "FROM TagUserContent t WHERE t.user = :user AND t.content = :content", returnAs = ArrayList.class)
  public List<TagUserContent> find(@Named("user") final User user,
      @Named("content") final Content content);

  @Finder(query = "SELECT t.tag FROM TagUserContent t WHERE t.user = :user AND t.content = :content", returnAs = ArrayList.class)
  public List<Tag> findTags(@Named("user") final User user, @Named("content") final Content content);

  @Finder(query = "SELECT Count(tuc.content.id) FROM TagUserContent tuc JOIN tuc.tag t WHERE tuc.content.container.owner = :group GROUP BY t.name ORDER BY count(*) ASC LIMIT 0,1")
  public Long getMaxGrouped(@Named("group") final Group group);

  @Finder(query = "SELECT Count(tuc.content.id) FROM TagUserContent tuc JOIN tuc.tag t WHERE tuc.content.container.owner = :group GROUP BY t.name ORDER BY count(*) DESC LIMIT 0,1")
  public Long getMinGrouped(@Named("group") final Group group);

  @Finder(query = "SELECT NEW cc.kune.core.shared.domain.TagCount(t.name, COUNT(tuc.content.id)) "
      + "FROM TagUserContent tuc JOIN tuc.tag t WHERE tuc.content.container.owner = :group "
      + "GROUP BY t.name ORDER BY t.name", returnAs = ArrayList.class)
  public List<TagCount> getTagsGroups(@Named("group") final Group group);
}
