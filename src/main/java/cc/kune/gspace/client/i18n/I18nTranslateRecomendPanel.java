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
package cc.kune.gspace.client.i18n;

import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.inject.Inject;

public class I18nTranslateRecomendPanel extends Composite {

  private final Frame recommFrame;
  private final Label tabTitle;

  @Inject
  public I18nTranslateRecomendPanel(final I18nTranslationService i18n) {
    tabTitle = new Label(i18n.t("Recommendations"));
    recommFrame = new Frame("ws/i18n-recom.html");
    // recommFrame.setHeight("auto");
    recommFrame.setStyleName("k-i18n-recommend");
    initWidget(recommFrame);
  }

  public IsWidget getTabTitle() {
    return tabTitle;
  }

  public void setSize(final int width, final int height) {
    final String widthS = width + "px";
    final String heightS = height + "px";
    recommFrame.setWidth(widthS);
    recommFrame.setHeight(heightS);
    // super.setSize(widthS, heightS);
  }
}
