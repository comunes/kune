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
package cc.kune.core.shared.domain;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

// TODO: Auto-generated Javadoc
/**
 * The Class TagCloudResult.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class TagCloudResult implements IsSerializable {

  /** The max value. */
  private int maxValue;

  /** The min value. */
  private int minValue;

  /** The tag count list. */
  private List<TagCount> tagCountList;

  /**
   * Instantiates a new tag cloud result.
   */
  public TagCloudResult() {
    this(new ArrayList<TagCount>(), 0, 0);
  }

  /**
   * Instantiates a new tag cloud result.
   * 
   * @param tagCountList
   *          the tag count list
   * @param maxValue
   *          the max value
   * @param minValue
   *          the min value
   */
  public TagCloudResult(final List<TagCount> tagCountList, final int maxValue, final int minValue) {
    this.tagCountList = tagCountList;
    this.maxValue = maxValue;
    this.minValue = minValue;
  }

  /**
   * Gets the max value.
   * 
   * @return the max value
   */
  public int getMaxValue() {
    return maxValue;
  }

  /**
   * Gets the min value.
   * 
   * @return the min value
   */
  public int getMinValue() {
    return minValue;
  }

  /**
   * Gets the tag count list.
   * 
   * @return the tag count list
   */
  public List<TagCount> getTagCountList() {
    return tagCountList;
  }

  /**
   * Sets the max value.
   * 
   * @param maxValue
   *          the new max value
   */
  public void setMaxValue(final int maxValue) {
    this.maxValue = maxValue;
  }

  /**
   * Sets the min value.
   * 
   * @param minValue
   *          the new min value
   */
  public void setMinValue(final int minValue) {
    this.minValue = minValue;
  }

  /**
   * Sets the tag count list.
   * 
   * @param tagCountList
   *          the new tag count list
   */
  public void setTagCountList(final List<TagCount> tagCountList) {
    this.tagCountList = tagCountList;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "TagCloudResult[list:" + tagCountList + "; maxValue: " + maxValue + "; minValue: " + minValue
        + "]";
  }
}
