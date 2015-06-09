/*
 *
 * Copyright (C) 2007-2015 Licensed to the Comunes Association (CA) under
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
package cc.kune.wave.client.kspecific.inboxcount;

import org.gwtbootstrap3.client.ui.Badge;

import cc.kune.common.client.tooltip.Tooltip;
import cc.kune.common.client.ui.BlinkAnimation;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.sitebar.spaces.Space;
import cc.kune.core.client.sitebar.spaces.SpaceSelectEvent;
import cc.kune.gspace.client.armor.GSpaceArmor;
import cc.kune.polymer.client.PolymerId;
import cc.kune.wave.client.kspecific.inboxcount.InboxCountPresenter.InboxCountView;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;

// TODO: Auto-generated Javadoc
/**
 * The Class InboxCountPanel.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class InboxCountPanel extends Badge implements InboxCountView {

  /** The blink animation. */
  private final BlinkAnimation blinkAnimation;

  /** The i18n. */
  private final I18nTranslationService i18n;

  /**
   * Instantiates a new inbox count panel.
   *
   * @param i18n
   *          the i18n
   * @param eventBus
   *          the event bus
   */
  @Inject
  public InboxCountPanel(final I18nTranslationService i18n, final EventBus eventBus,
      final GSpaceArmor armor) {
    this.i18n = i18n;
    setStylePrimaryName("k-space-sel-inbox-count");
    blinkAnimation = new BlinkAnimation(this, 400);

    setVisible(false);
    armor.wrapDiv(PolymerId.INBOX_SITEBAR_ICON_GROUP).add(this);

    addDomHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        SpaceSelectEvent.fire(eventBus, Space.userSpace, true);
      }
    }, ClickEvent.getType());

  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.wave.client.kspecific.inboxcount.InboxCountPresenter.InboxCountView
   * #blink()
   */
  @Override
  public void blink() {
    blinkAnimation.animate(4);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.wave.client.kspecific.inboxcount.InboxCountPresenter.InboxCountView
   * #setTotal(int)
   */
  @Override
  public void setTotal(final int total) {
    super.setText(String.valueOf(total));
    Tooltip.to(
        this,
        total == 1 ? i18n.t("One recent conversation unread") : i18n.t(
            "[%d] recent conversations unread", total));
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.wave.client.kspecific.inboxcount.InboxCountPresenter.InboxCountView
   * #showCount(boolean)
   */
  @Override
  public void showCount(final boolean show) {
    setVisible(show);
  }
}
