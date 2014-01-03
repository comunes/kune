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
package cc.kune.gspace.client.tags;

import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.gspace.client.armor.GSpaceArmor;
import cc.kune.gspace.client.tags.TagsSummaryPresenter.TagsSummaryView;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

// TODO: Auto-generated Javadoc
/**
 * The Class TagsSummaryPanel.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class TagsSummaryPanel extends ViewImpl implements TagsSummaryView {

  /** The flow panel. */
  private final FlowPanel flowPanel;

  /** The i18n. */
  private final I18nTranslationService i18n;

  /** The main panel. */
  private final FlowPanel mainPanel;

  /**
   * Instantiates a new tags summary panel.
   * 
   * @param i18n
   *          the i18n
   * @param ws
   *          the ws
   */
  @Inject
  public TagsSummaryPanel(final I18nTranslationService i18n, final GSpaceArmor ws) {
    this.i18n = i18n;
    mainPanel = new FlowPanel();
    flowPanel = new FlowPanel();
    flowPanel.setWidth("100%");
    final Label header = new Label(i18n.t("Tags"));
    header.setTitle(i18n.t("Keywords or terms associated with this group"));
    // super.setBorderStylePrimaryName("k-dropdownouter-tags");
    flowPanel.addStyleName("kune-Margin-Small-trbl");
    flowPanel.addStyleName("k-tsp-cloud");
    header.addStyleName("k-sn-maintitle");
    mainPanel.addStyleName("k-sn-mainpanel");
    mainPanel.add(header);
    mainPanel.add(flowPanel);
    // ws.getEntityToolsSouth().add(mainPanel);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.tags.TagsSummaryPresenter.TagsSummaryView#addTag(
   * java.lang.String, java.lang.Long, java.lang.String,
   * com.google.gwt.event.dom.client.ClickHandler)
   */
  @Override
  public void addTag(final String name, final Long count, final String style,
      final ClickHandler clickHandler) {
    final Label label = new Label(name);
    // i18n pluralization
    if (count > 1) {
      label.setTitle(i18n.t("There are [%d] items with this tag", count));
    } else {
      label.setTitle(i18n.t("There are [%d] item with this tag", count));
    }
    label.addClickHandler(clickHandler);
    label.addStyleName("k-tsp-tag");
    label.addStyleName(style);
    flowPanel.add(label);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.gwtplatform.mvp.client.View#asWidget()
   */
  @Override
  public Widget asWidget() {
    return mainPanel;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.tags.TagsSummaryPresenter.TagsSummaryView#clear()
   */
  @Override
  public void clear() {
    flowPanel.clear();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.tags.TagsSummaryPresenter.TagsSummaryView#setVisible
   * (boolean)
   */
  @Override
  public void setVisible(final boolean visible) {
    mainPanel.setVisible(visible);
  }
}
