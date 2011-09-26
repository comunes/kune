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
package cc.kune.core.client.state;

import java.util.HashMap;

import cc.kune.core.client.auth.Register;
import cc.kune.core.client.auth.SignIn;
import cc.kune.core.client.groups.newgroup.NewGroup;
import cc.kune.core.client.sitebar.AboutKuneDialog;
import cc.kune.core.client.sitebar.spaces.Space;
import cc.kune.core.client.sitebar.spaces.SpaceSelectEvent;
import cc.kune.core.client.sub.SubtitlesManager;
import cc.kune.gspace.client.i18n.I18nTranslator;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Provider;

@SuppressWarnings("serial")
public class SiteTokenListeners extends HashMap<String, HistoryTokenCallback> {
  private final Provider<AboutKuneDialog> aboutKuneDialog;
  private final EventBus eventBus;
  private final Provider<NewGroup> newGroup;
  private final Provider<Register> register;
  private final Provider<SignIn> signIn;
  private final Provider<SubtitlesManager> subProvider;
  private final Provider<I18nTranslator> translator;

  @Inject
  public SiteTokenListeners(final Session session, final EventBus eventBus,
      final Provider<SignIn> signIn, final Provider<Register> register,
      final Provider<NewGroup> newGroup, final Provider<AboutKuneDialog> aboutKuneDialog,
      final Provider<I18nTranslator> translator, final Provider<SubtitlesManager> subProvider) {
    this.eventBus = eventBus;
    this.signIn = signIn;
    this.register = register;
    this.newGroup = newGroup;
    this.aboutKuneDialog = aboutKuneDialog;
    this.translator = translator;
    this.subProvider = subProvider;
    putValues();
  }

  private void putValues() {
    put(SiteTokens.HOME, new HistoryTokenCallback() {
      @Override
      public void onHistoryToken(final String token) {
        SpaceSelectEvent.fire(eventBus, Space.homeSpace);
      }
    });
    put(SiteTokens.WAVEINBOX, new HistoryTokenCallback() {
      @Override
      public void onHistoryToken(final String token) {
        SpaceSelectEvent.fire(eventBus, Space.userSpace);
      }
    });
    put(SiteTokens.SIGNIN, new HistoryTokenCallback() {
      @Override
      public void onHistoryToken(final String token) {
        signIn.get().showSignInDialog();
      }
    });
    put(SiteTokens.REGISTER, new HistoryTokenCallback() {
      @Override
      public void onHistoryToken(final String token) {
        register.get().doRegister();
      }
    });
    put(SiteTokens.NEWGROUP, new HistoryTokenCallback() {
      @Override
      public void onHistoryToken(final String token) {
        newGroup.get().doNewGroup();
      }
    });
    put(SiteTokens.ABOUTKUNE, new HistoryTokenCallback() {
      @Override
      public void onHistoryToken(final String token) {
        // FIXME, something to come back
        aboutKuneDialog.get().showCentered();
      }
    });
    put(SiteTokens.TRANSLATE, new HistoryTokenCallback() {
      // FIXME, something to come back
      @Override
      public void onHistoryToken(final String token) {
        translator.get().show();
      }
    });
    put(SiteTokens.SUBTITLES, new HistoryTokenCallback() {
      @Override
      public void onHistoryToken(final String token) {
        subProvider.get().show(token);
      }
    });
  }
}
