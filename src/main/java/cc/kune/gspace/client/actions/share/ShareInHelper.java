/*
 *
 * Copyright (C) 2007-2013 Licensed to the Comunes Association (CA) under
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

package cc.kune.gspace.client.actions.share;

import java.util.ArrayList;
import java.util.List;

import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.SessionInstance;
import cc.kune.core.client.state.StateTokenUtils;
import cc.kune.core.shared.dto.StateAbstractDTO;
import cc.kune.core.shared.dto.StateContainerDTO;
import cc.kune.core.shared.dto.StateContentDTO;

import com.google.gwt.http.client.URL;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

/**
 * The Class ShareInHelper used to get common texts/urls to share menu actions
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class ShareInHelper {

  public static String getCommonText() {
    return URL.encodeQueryString(getTitle(SessionInstance.get())
        + " "
        + I18n.tWithNT("via [%s]", "used in references 'something via @someone'",
            I18n.getSiteCommonName()));
  }

  public static String getCommonUrl() {
    return URL.encodeQueryString(getCurrentUrl(SessionInstance.get()));
  }

  public static String getCurrentUrl(final Session session) {
    return getCurrentUrl(session, false);
  }

  public static String getCurrentUrl(final Session session, final boolean embeded) {
    return StateTokenUtils.getGroupSpaceUrl(session.getCurrentState().getStateToken(), embeded);
  }

  public static String getTitle(final Session session) {
    final StateAbstractDTO state = session.getCurrentState();
    final String prefix = session.getCurrentGroupShortName() + ", ";
    if (!(state instanceof StateContentDTO)) {
      return prefix
          + (((StateContainerDTO) state).getContainer().isRoot() ? I18n.t(state.getTitle())
              : state.getTitle());
    } else {
      return prefix + session.getCurrentState().getTitle();
    }
  }

  private final List<Provider<? extends GuiActionDescrip>> shareInAllList;
  private final ArrayList<Provider<? extends GuiActionDescrip>> shareInWavesList;

  @Inject
  public ShareInHelper(final Provider<ShareInTwitterMenuItem> shareInTwitter,
      final Provider<ShareInGPlusMenuItem> shareInGPlus,
      final Provider<ShareInIdenticaMenuItem> shareInIdentica,
      final Provider<ShareInFacebookMenuItem> shareInFacebook,
      final Provider<ShareInLinkedinMenuItem> shareInLinked,
      final Provider<ShareInTumblrMenuItem> shareInTumblr,
      final Provider<ShareInDiggMenuItem> shareInDigg,
      final Provider<ShareInRedditMenuItem> shareInReddit,
      final Provider<ShareInEmbededMenuItem> shareInEmbed) {
    shareInAllList = new ArrayList<Provider<? extends GuiActionDescrip>>();
    shareInWavesList = new ArrayList<Provider<? extends GuiActionDescrip>>();

    shareInAllList.add(shareInTwitter);
    shareInAllList.add(shareInGPlus);
    shareInAllList.add(shareInFacebook);
    shareInAllList.add(shareInReddit);
    shareInAllList.add(shareInDigg);
    shareInAllList.add(shareInLinked);
    shareInAllList.add(shareInTumblr);

    shareInWavesList.add(shareInEmbed);
  }

  public List<Provider<? extends GuiActionDescrip>> getShareInAll() {
    return shareInAllList;
  }

  public List<Provider<? extends GuiActionDescrip>> getShareInWaves() {
    return shareInWavesList;
  }
}