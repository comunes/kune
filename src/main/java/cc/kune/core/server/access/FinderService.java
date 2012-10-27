/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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
package cc.kune.core.server.access;

import cc.kune.core.client.errors.ContentNotFoundException;
import cc.kune.core.client.errors.DefaultException;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.domain.Container;
import cc.kune.domain.Content;
import cc.kune.domain.Group;
import cc.kune.domain.Rate;
import cc.kune.domain.User;

public interface FinderService {

  Content findByRootOnGroup(String groupName, String toolName) throws DefaultException;

  Container getContainer(Long folderId) throws DefaultException;

  Container getContainer(String folderId);

  Content getContainerByWaveRef(String waveRef);

  Content getContent(Long contentId) throws DefaultException;

  Content getContent(String contentId) throws ContentNotFoundException;

  Content getContentOrDefContent(StateToken token, Group defaultGroup) throws DefaultException;

  Container getFolder(Long folderId) throws DefaultException;

  Rate getRate(User user, Content content);

  Double getRateAvg(Content content);

  Long getRateByUsers(Content content);

}
