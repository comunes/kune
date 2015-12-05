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

package cc.kune.wave.client.kspecific.tutorial;

import static com.google.gwt.query.client.GQuery.*;

import org.gwtbootstrap3.client.ui.base.button.CustomButton;
import org.gwtbootstrap3.client.ui.constants.ButtonSize;
import org.gwtbootstrap3.client.ui.constants.ButtonType;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.HandlerRegistration;

import cc.kune.common.shared.i18n.I18n;
import cc.kune.core.client.cookies.CookieUtils;
import cc.kune.core.client.sitebar.spaces.Space;
import cc.kune.core.client.sitebar.spaces.SpaceSelectEvent;
import cc.kune.core.client.sitebar.spaces.SpaceSelectEvent.SpaceSelectHandler;
import cc.kune.core.client.state.TokenMatcher;
import cc.kune.core.client.state.impl.HistoryUtils;
import cc.kune.gspace.client.armor.GSpaceArmor;
import cc.kune.polymer.client.PolymerId;
import cc.kune.wave.client.kspecific.AfterOpenWaveEvent;
import cc.kune.wave.client.kspecific.AfterOpenWaveEvent.AfterOpenWaveHandler;
import cc.kune.wave.client.kspecific.WaveClientProvider;

public class InboxTutorial extends Composite {

  private static final String COOKIE_NAME = "kune-inbox-tutorial";
  private static InboxTutorialUiBinder uiBinder = GWT.create(InboxTutorialUiBinder.class);

  interface InboxTutorialUiBinder extends UiBinder<Widget, InboxTutorial> {
  }

  @UiField
  Label titleLabel;
  @UiField
  Label subtitleLabel;
  @UiField
  InlineLabel createLabel;
  @UiField
  InlineLabel addContactLabel;
  @UiField
  InlineLabel publishLabel;
  @UiField
  InlineLabel notificationsLabel;
  @UiField
  InlineLabel orderedLabel;
  @UiField
  CustomButton btn;
  private HandlerRegistration selectHandler;
  private HandlerRegistration openHandler;

  @Inject
  public InboxTutorial(GSpaceArmor armor, EventBus eventBus, final WaveClientProvider waveClient) {
    final String cookie = Cookies.getCookie(COOKIE_NAME);
    if (cookie == null) {
      initWidget(uiBinder.createAndBindUi(this));
      armor.wrapDiv(PolymerId.INBOX_TUTORIAL).add(this);
      selectHandler = eventBus.addHandler(SpaceSelectEvent.getType(), new SpaceSelectHandler() {
        @Override
        public void onSpaceSelect(SpaceSelectEvent event) {
          if (event.getSpace().equals(Space.userSpace)) {
            final String encodedToken = HistoryUtils.undoHashbang(History.getToken());
            if (encodedToken != null && !encodedToken.isEmpty()
                && TokenMatcher.isInboxToken(encodedToken)) {
              // Inbox without wave opened
              setVisible(true);
              waveClient.get().clear();
            }
          }
        }
      });
      openHandler = eventBus.addHandler(AfterOpenWaveEvent.getType(), new AfterOpenWaveHandler() {
        @Override
        public void onAfterOpenWave(AfterOpenWaveEvent event) {
          setVisible(false);
        }});
      subtitleLabel.setText(
          I18n.tWithNT("This is your", "Followed by 'inbox', so 'This is your inbox'"));
      titleLabel.setText(I18n.t("Inbox"));
      createLabel.setText(I18n.t("Here you can create, edit and comment documents"));
      addContactLabel.setText(I18n.t("Share them with your contacts"));
      publishLabel.setText(I18n.t("Publish them in your groups"));
      notificationsLabel.setText(
          I18n.t("Get notifications of changes, coments or new documents shared with you"));
      orderedLabel.setText(I18n.t(
          "And to have a cronological ordered list of all the contents you are participating in"));
      btn.setText(I18n.t("I got it!"));
      btn.setType(ButtonType.PRIMARY);
      btn.setSize(ButtonSize.LARGE);
      btn.addClickHandler(new ClickHandler() {
        @Override
        public void onClick(ClickEvent event) {
          $(InboxTutorial.this).as(Effects).fadeTo(400, 0);
          Cookies.setCookie(COOKIE_NAME, null, CookieUtils.expireInYears(), CookieUtils.getDomain(), "/",
              false);
          selectHandler.removeHandler();
          openHandler.removeHandler();
        }
      });
    }
  }
}
