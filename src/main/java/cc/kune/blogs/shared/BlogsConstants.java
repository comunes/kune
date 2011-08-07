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
package cc.kune.blogs.shared;

import cc.kune.core.shared.ToolConstants;

public final class BlogsConstants {

  public static final String NAME = "blogs";
  public static final String ROOT_NAME = "blogs";
  public static final String TYPE_BLOG = NAME + "." + "blog";
  public static final String TYPE_POST = NAME + "." + "post";
  public static final String TYPE_ROOT = NAME + "." + "root";
  public static final String TYPE_UPLOADEDFILE = NAME + "." + ToolConstants.UPLOADEDFILE_SUFFIX;

  private BlogsConstants() {
  }
}
