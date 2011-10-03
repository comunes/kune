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
package cc.kune.hspace.client;

import cc.kune.common.client.notify.NotifyUser;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.dto.HomeStatsDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

public class HSpacePresenter extends Presenter<HSpacePresenter.HSpaceView, HSpacePresenter.HSpaceProxy> {

  @ProxyCodeSplit
  public interface HSpaceProxy extends Proxy<HSpacePresenter> {
  }
  public interface HSpaceView extends View {
  }

  private final I18nTranslationService i18n;

  @Inject
  public HSpacePresenter(final Session session, final EventBus eventBus, final HSpaceView view,
      final HSpaceProxy proxy, final I18nTranslationService i18n,
      final Provider<ClientStatsServiceAsync> statsService) {
    super(eventBus, view, proxy);
    this.i18n = i18n;
    final AsyncCallbackSimple<HomeStatsDTO> callback = new AsyncCallbackSimple<HomeStatsDTO>() {
      @Override
      public void onSuccess(final HomeStatsDTO result) {
        NotifyUser.info(
            "Total groups: " + result.getTotalGroups() + " total users: " + result.getTotalUsers(), true);
      }
    };
    if (session.isLogged()) {
      statsService.get().getHomeStats(session.getUserHash(), callback);
    } else {
      statsService.get().getHomeStats(callback);
    }
  }

  @Override
  protected void onReveal() {
    super.onReveal();
  }

  @Override
  protected void revealInParent() {
    RevealRootContentEvent.fire(this, this);
  }

}
