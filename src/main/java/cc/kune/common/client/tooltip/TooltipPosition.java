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
package cc.kune.common.client.tooltip;

// TODO: Auto-generated Javadoc
/**
 * The Class TooltipPosition.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class TooltipPosition {

  /**
   * The Enum ArrowPosition.
   *
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public enum ArrowPosition {
    
    /** The e. */
    E, 
 /** The n. */
 N, 
 /** The ne. */
 NE, 
 /** The nw. */
 NW, 
 /** The s. */
 S, 
 /** The se. */
 SE, 
 /** The sw. */
 SW, 
 /** The w. */
 W
  }

  /** The Constant ARROW_DEF_MARGIN. */
  public static final int ARROW_DEF_MARGIN = 6;
  
  /** The Constant ARROW_SIZE. */
  public static final int ARROW_SIZE = 7;
  
  /** The Constant TOOLTIP_DISTANCE. */
  public static final int TOOLTIP_DISTANCE = 12;

  /** The arrow left. */
  private int arrowLeft;
  
  /** The arrow position. */
  private ArrowPosition arrowPosition;
  
  /** The arrow top. */
  private int arrowTop;
  
  /** The left. */
  private int left;
  
  /** The top. */
  private int top;

  /**
   * Instantiates a new tooltip position.
   *
   * @param left the left
   * @param top the top
   * @param arrowPosition the arrow position
   * @param arrowLeft the arrow left
   * @param arrowTop the arrow top
   */
  public TooltipPosition(final int left, final int top, final ArrowPosition arrowPosition,
      final int arrowLeft, final int arrowTop) {
    super();
    this.left = left;
    this.top = top;
    this.arrowPosition = arrowPosition;
    this.arrowLeft = arrowLeft;
    this.arrowTop = arrowTop;
  }

  /**
   * Gets the arrow left.
   *
   * @return the arrow left
   */
  public int getArrowLeft() {
    return arrowLeft;
  }

  /**
   * Gets the arrow position.
   *
   * @return the arrow position
   */
  public ArrowPosition getArrowPosition() {
    return arrowPosition;
  }

  /**
   * Gets the arrow top.
   *
   * @return the arrow top
   */
  public int getArrowTop() {
    return arrowTop;
  }

  /**
   * Gets the left.
   *
   * @return the left
   */
  public int getLeft() {
    return left;
  }

  /**
   * Gets the top.
   *
   * @return the top
   */
  public int getTop() {
    return top;
  }

  /**
   * Sets the arrow left.
   *
   * @param arrowLeft the new arrow left
   */
  public void setArrowLeft(final int arrowLeft) {
    this.arrowLeft = arrowLeft;
  }

  /**
   * Sets the arrow position.
   *
   * @param arrowPosition the new arrow position
   */
  public void setArrowPosition(final ArrowPosition arrowPosition) {
    this.arrowPosition = arrowPosition;
  }

  /**
   * Sets the arrow top.
   *
   * @param arrowTop the new arrow top
   */
  public void setArrowTop(final int arrowTop) {
    this.arrowTop = arrowTop;
  }

  /**
   * Sets the left.
   *
   * @param left the new left
   */
  public void setLeft(final int left) {
    this.left = left;
  }

  /**
   * Sets the top.
   *
   * @param top the new top
   */
  public void setTop(final int top) {
    this.top = top;
  }

}
