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
package cc.kune.core.client.notify.confirm;

import cc.kune.common.client.notify.ConfirmAskEvent;
import cc.kune.core.client.notify.confirm.UserConfirmPresenter.UserConfirmProxy;
import cc.kune.core.client.notify.confirm.UserConfirmPresenter.UserConfirmView;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.ProxyEvent;
import com.gwtplatform.mvp.client.proxy.Proxy;

public class UserConfirmPresenter extends Presenter<UserConfirmView, UserConfirmProxy> {
  @ProxyCodeSplit
  public interface UserConfirmProxy extends Proxy<UserConfirmPresenter> {
  }

  public interface UserConfirmView extends View {
    public void confirmAsk(ConfirmAskEvent ask);
  }

  @Inject
  public UserConfirmPresenter(final EventBus eventBus, final UserConfirmView view,
      final UserConfirmProxy proxy) {
    super(eventBus, view, proxy);
  }

  @ProxyEvent
  public void onConfirmAsk(final ConfirmAskEvent event) {
    getView().confirmAsk(event);
  }

  @Override
  protected void revealInParent() {
    RootPanel.get().add(getWidget());
  }

}
