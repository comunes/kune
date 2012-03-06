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
package cc.kune.core.server.manager.impl;

import javax.persistence.EntityManager;

import cc.kune.core.server.DataSourceKune;
import cc.kune.core.server.manager.TagManager;
import cc.kune.domain.Tag;
import cc.kune.domain.finders.TagFinder;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class TagManagerDefault extends DefaultManager<Tag, Long> implements TagManager {

  private final TagFinder tagFinder;

  @Inject
  public TagManagerDefault(@DataSourceKune final Provider<EntityManager> provider,
      final TagFinder tagFinder) {
    super(provider, Tag.class);
    this.tagFinder = tagFinder;
  }

  @Override
  public Tag findByTagName(final String tag) {
    return tagFinder.findByTagName(tag);
  }
}
