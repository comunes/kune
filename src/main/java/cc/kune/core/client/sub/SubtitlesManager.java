/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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
package cc.kune.core.client.sub;

import cc.kune.common.client.utils.Base64Utils;
import cc.kune.common.shared.utils.SimpleCallback;
import cc.kune.common.shared.utils.TextUtils;
import cc.kune.core.client.state.StateManager;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

public class SubtitlesManager extends
    Presenter<SubtitlesManager.SubtitlesView, SubtitlesManager.SubtitlesProxy> {

  @ProxyCodeSplit
  public interface SubtitlesProxy extends Proxy<SubtitlesManager> {
  }

  public interface SubtitlesView extends View {

    void setDescription(String descr);

    void setTitleText(String text);

    void show(final SimpleCallback atShowEnd);

  }
  private final StateManager stateManager;

  @Inject
  public SubtitlesManager(final EventBus eventBus, final SubtitlesView view, final SubtitlesProxy proxy,
      final StateManager stateManager) {
    super(eventBus, view, proxy);
    this.stateManager = stateManager;
  }

  @Override
  protected void revealInParent() {
    RevealRootContentEvent.fire(this, this);
  }

  public void show(final String token) {
    final String[] params = token.split("\\|");
    final int len = params.length;
    getView().setTitleText(new String(Base64Utils.fromBase64(params[0])));
    if (len > 1) {
      getView().setDescription(new String(Base64Utils.fromBase64(params[1])));
    }
    getView().show(new SimpleCallback() {
      @Override
      public void onCallback() {
        if (len > 2 && TextUtils.notEmpty(params[2])) {
          stateManager.gotoHistoryToken(params[2]);
        } else {
          stateManager.gotoHistoryToken("");
        }
      }
    });
  }
}
