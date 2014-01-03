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
package cc.kune.core.server.manager;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class SearchResult.
 * 
 * @param <T>
 *          the generic type
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class SearchResult<T> {

  /** The list. */
  List<T> list;

  /** The size. */
  int size;

  /**
   * Instantiates a new search result.
   */
  public SearchResult() {
    this(0, null);
  }

  /**
   * Instantiates a new search result.
   * 
   * @param count
   *          the count
   * @param list
   *          the list
   */
  public SearchResult(final int count, final List<T> list) {
    this.list = list;
    this.size = count;
  }

  /**
   * Gets the list.
   * 
   * @return the list
   */
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

  /**
   * Sets the list.
   * 
   * @param list
   *          the new list
   */
  public void setList(final List<T> list) {
    this.list = list;
  }

  /**
   * Sets the size.
   * 
   * @param size
   *          the new size
   */
  public void setSize(final int size) {
    this.size = size;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "SearchResult[(" + getSize() + "): " + list + "]";
  }
}
