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
package cc.kune.core.client.notify.alerts;

import cc.kune.core.client.notify.alerts.UserAlerterPresenter.UserAlerterProxy;
import cc.kune.core.client.notify.alerts.UserAlerterPresenter.UserAlerterView;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.ProxyEvent;
import com.gwtplatform.mvp.client.proxy.Proxy;

public class UserAlerterPresenter extends Presenter<UserAlerterView, UserAlerterProxy> {
    @ProxyCodeSplit
    public interface UserAlerterProxy extends Proxy<UserAlerterPresenter> {
    }

    public interface UserAlerterView extends View {
        public void alert(String title, String message);
    }

    @Inject
    public UserAlerterPresenter(final EventBus eventBus, final UserAlerterView view, final UserAlerterProxy proxy) {
        super(eventBus, view, proxy);
    }

    @ProxyEvent
    public void onAlert(AlertEvent event) {
        getView().alert(event.getTitle(), event.getMessage());
    }

    @Override
    protected void revealInParent() {
        RootPanel.get().add(getWidget());
    }

}