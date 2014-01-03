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
package cc.kune.core.client.ui.dialogs.tabbed;

import cc.kune.common.client.tooltip.Tooltip;
import cc.kune.common.client.ui.IconLabel;
import cc.kune.common.shared.utils.TextUtils;

import com.google.gwt.i18n.client.HasDirection.Direction;
import com.google.gwt.resources.client.ImageResource;

// TODO: Auto-generated Javadoc
/**
 * The Class TabTitleGenerator.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class TabTitleGenerator {

  /**
   * Format.
   * 
   * @param title
   *          the title
   * @param maxLength
   *          the max length
   * @return the string
   */
  private static String format(final String title, final int maxLength) {
    return TextUtils.ellipsis(title, maxLength);
  }

  /**
   * Generate.
   * 
   * @param res
   *          the res
   * @param title
   *          the title
   * @param maxLength
   *          the max length
   * @param id
   *          the id
   * @return the icon label
   */
  public static IconLabel generate(final ImageResource res, final String title, final int maxLength,
      final String id) {
    final IconLabel tabTitle = new IconLabel(res, format(title, maxLength));
    setTooltip(title, maxLength, tabTitle);
    tabTitle.ensureDebugId(id);
    return tabTitle;
  }

  /**
   * Generate.
   * 
   * @param res
   *          the res
   * @param title
   *          the title
   * @param id
   *          the id
   * @return the icon label
   */
  public static IconLabel generate(final ImageResource res, final String title, final String id) {
    final IconLabel tabTitle = new IconLabel(res, title);
    tabTitle.ensureDebugId(id);
    return tabTitle;
  }

  /**
   * Sets the text.
   * 
   * @param tabTitle
   *          the tab title
   * @param title
   *          the title
   * @param maxLength
   *          the max length
   * @param direction
   *          the direction
   */
  public static void setText(final IconLabel tabTitle, final String title, final int maxLength,
      final Direction direction) {
    tabTitle.setText(format(title, maxLength), direction);
    setTooltip(title, maxLength, tabTitle);
  }

  /**
   * Sets the tooltip.
   * 
   * @param title
   *          the title
   * @param maxLength
   *          the max length
   * @param tabTitle
   *          the tab title
   */
  private static void setTooltip(final String title, final int maxLength, final IconLabel tabTitle) {
    if (title.length() > maxLength) {
      Tooltip.to(tabTitle, title);
    }
  }

}
