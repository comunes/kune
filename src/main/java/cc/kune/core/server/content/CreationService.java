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
package cc.kune.core.server.content;

import cc.kune.domain.Container;
import cc.kune.domain.Content;
import cc.kune.domain.Group;
import cc.kune.domain.I18nLanguage;
import cc.kune.domain.User;

public interface CreationService {

  void addGadgetToContent(User user, Content content, String gadgetName);

  Content createContent(String title, String body, User user, Container container, String typeId);

  Container createFolder(Group group, Long parentFolderId, String name, I18nLanguage language,
      String contentTypeId);

  Content createGadget(User user, Container container, String gadgetname, String typeId, String title,
      String body);

  Container createRootFolder(Group group, String name, String rootName, String typeRoot);

  Content saveContent(User editor, Content descriptor, String content);

}
