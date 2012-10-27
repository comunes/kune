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
package cc.kune.core.server.manager;

import java.util.List;

public class SearchResult<T> {
  List<T> list;
  int size;

  public SearchResult() {
    this(0, null);
  }

  public SearchResult(final int count, final List<T> list) {
    this.list = list;
    this.size = count;
  }

  public List<T> getList() {
    return list;
  }

  /**
   * Gets the size of total results (may differ from list size).
   * 
   * @return the size
   */
  public int getSize() {
    return size;
  }

  public void setList(final List<T> list) {
    this.list = list;
  }

  public void setSize(final int size) {
    this.size = size;
  }

  @Override
  public String toString() {
    return "SearchResult[(" + getSize() + "): " + list + "]";
  }
}
