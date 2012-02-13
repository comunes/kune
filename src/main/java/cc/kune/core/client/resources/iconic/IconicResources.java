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
  ImageResource barters();

  @Source("blogs.png")
  ImageResource blogs();

  @Source("chats.png")
  ImageResource chats();

  @Source("docs.png")
  ImageResource docs();

  @Source("events.png")
  ImageResource events();

  @Source("lists.png")
  ImageResource lists();

  @Source("tasks.png")
  ImageResource tasks();

  @Source("wikis.png")
  ImageResource wikis();
}
