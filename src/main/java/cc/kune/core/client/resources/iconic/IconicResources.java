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
package cc.kune.core.client.resources.iconic;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface IconicResources extends ClientBundle {

  @Source("barters.png")
  ImageResource bartersWhite();

  @Source("barters-g.png")
  ImageResource bartersGrey();

  @Source("blogs.png")
  ImageResource blogsWhite();

  @Source("blogs-g.png")
  ImageResource blogsGrey();

  @Source("chats.png")
  ImageResource chatsWhite();

  @Source("chats-g.png")
  ImageResource chatsGrey();

  @Source("docs.png")
  ImageResource docsWhite();

  @Source("docs-g.png")
  ImageResource docsGrey();

  @Source("events.png")
  ImageResource eventsWhite();

  @Source("events-g.png")
  ImageResource eventsGrey();

  @Source("lists.png")
  ImageResource listsWhite();

  @Source("lists-g.png")
  ImageResource listsGrey();

  @Source("tasks.png")
  ImageResource tasksWhite();

  @Source("tasks-g.png")
  ImageResource tasksGrey();

  @Source("trash.png")
  ImageResource trashWhite();

  @Source("trash-g.png")
  ImageResource trashGrey();

  @Source("wikis.png")
  ImageResource wikisWhite();

  @Source("wikis-g.png")
  ImageResource wikisGrey();
}
