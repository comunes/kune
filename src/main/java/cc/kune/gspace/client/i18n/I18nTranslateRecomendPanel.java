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
package cc.kune.gspace.client.i18n;

import cc.kune.common.shared.i18n.I18nTranslationService;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class I18nTranslateRecomendPanel.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class I18nTranslateRecomendPanel extends Composite {

  /** The recomm frame. */
  private final Frame recommFrame;

  /** The tab title. */
  private final Label tabTitle;

  /**
   * Instantiates a new i18n translate recomend panel.
   * 
   * @param i18n
   *          the i18n
   */
  @Inject
  public I18nTranslateRecomendPanel(final I18nTranslationService i18n) {
    tabTitle = new Label(i18n.t("Recommendations"));
    recommFrame = new Frame("ws/i18n-recom.html");
    // recommFrame.setHeight("auto");
    recommFrame.setStyleName("k-i18n-recommend");
    initWidget(recommFrame);
  }

  /**
   * Gets the tab title.
   * 
   * @return the tab title
   */
  public IsWidget getTabTitle() {
    return tabTitle;
  }

  /**
   * Sets the size.
   * 
   * @param width
   *          the width
   * @param height
   *          the height
   */
  public void setSize(final int width, final int height) {
    final String widthS = width + "px";
    final String heightS = height + "px";
    recommFrame.setWidth(widthS);
    recommFrame.setHeight(heightS);
    // super.setSize(widthS, heightS);
  }
}
