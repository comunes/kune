/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
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
 \*/
package org.ourproject.kune.platf.client.services;

import org.ourproject.kune.platf.client.app.ApplicationComponentGroup;
import org.ourproject.kune.platf.client.app.GroupOptionsCollection;
import org.ourproject.kune.platf.client.app.HistoryWrapper;
import org.ourproject.kune.platf.client.app.HistoryWrapperDefault;
import org.ourproject.kune.platf.client.app.ToolGroup;
import org.ourproject.kune.platf.client.app.UserOptionsCollection;
import org.ourproject.kune.platf.client.i18n.I18nUITranslationService;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.platf.client.rpc.I18nService;
import org.ourproject.kune.platf.client.rpc.I18nServiceAsync;
import org.ourproject.kune.platf.client.rpc.SiteService;
import org.ourproject.kune.platf.client.rpc.SiteServiceAsync;
import org.ourproject.kune.platf.client.rpc.UserService;
import org.ourproject.kune.platf.client.rpc.UserServiceAsync;
import org.ourproject.kune.platf.client.state.ContentProvider;
import org.ourproject.kune.platf.client.state.ContentProviderDefault;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.SessionDefault;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.platf.client.state.StateManagerDefault;
import org.ourproject.kune.platf.client.ui.rte.insertimg.InsertImageGroup;
import org.ourproject.kune.platf.client.ui.rte.insertlink.InsertLinkGroup;
import org.ourproject.kune.platf.client.ui.rte.insertmedia.InsertMediaGroup;
import org.ourproject.kune.platf.client.ui.rte.insertspecialchar.InsertSpecialCharGroup;

import com.calclab.suco.client.events.Listener0;
import com.calclab.suco.client.ioc.decorator.Singleton;
import com.calclab.suco.client.ioc.module.Factory;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public class CoreModule extends AbstractExtendedModule {

    private final Listener0 onI18nReady;

    public CoreModule(final Listener0 onI18nReady) {
        this.onI18nReady = onI18nReady;
    }

    @Override
    protected void onInstall() {

        register(Singleton.class, new Factory<Session>(Session.class) {
            @Override
            public Session create() {
                return new SessionDefault(Cookies.getCookie(Session.USERHASH), p(UserServiceAsync.class));
            }
        }, new Factory<I18nServiceAsync>(I18nServiceAsync.class) {
            @Override
            public I18nServiceAsync create() {
                final I18nServiceAsync service = (I18nServiceAsync) GWT.create(I18nService.class);
                ((ServiceDefTarget) service).setServiceEntryPoint(GWT.getModuleBaseURL() + "I18nService");
                return service;
            }
        }, new Factory<UserServiceAsync>(UserServiceAsync.class) {
            @Override
            public UserServiceAsync create() {
                final UserServiceAsync service = (UserServiceAsync) GWT.create(UserService.class);
                ((ServiceDefTarget) service).setServiceEntryPoint(GWT.getModuleBaseURL() + "UserService");
                return service;
            }
        }, new Factory<SiteServiceAsync>(SiteServiceAsync.class) {
            @Override
            public SiteServiceAsync create() {
                final SiteServiceAsync service = (SiteServiceAsync) GWT.create(SiteService.class);
                ((ServiceDefTarget) service).setServiceEntryPoint(GWT.getModuleBaseURL() + "SiteService");
                return service;
            }
        });

        register(Singleton.class, new Factory<I18nUITranslationService>(I18nUITranslationService.class) {
            @Override
            public I18nUITranslationService create() {
                final I18nUITranslationService i18n = new I18nUITranslationService();
                i18n.init(i(I18nServiceAsync.class), i(Session.class), onI18nReady);
                return i18n;
            }
        });

        i(I18nUITranslationService.class);

        register(Singleton.class, new Factory<HistoryWrapper>(HistoryWrapper.class) {
            @Override
            public HistoryWrapper create() {
                return new HistoryWrapperDefault();
            }
        }, new Factory<ContentProvider>(ContentProvider.class) {
            @Override
            public ContentProvider create() {
                return new ContentProviderDefault(i(ContentServiceAsync.class));
            }
        }, new Factory<StateManager>(StateManager.class) {
            @Override
            public StateManager create() {
                final StateManagerDefault stateManager = new StateManagerDefault(i(ContentProvider.class),
                        i(Session.class), i(HistoryWrapper.class));
                History.addValueChangeHandler(stateManager);
                return stateManager;
            }
        });

        registerDecorator(ApplicationComponentGroup.class, new ApplicationComponentGroup(container));
        registerDecorator(ToolGroup.class, new ToolGroup(container));
        registerDecorator(GroupOptionsCollection.class, new GroupOptionsCollection(container));
        registerDecorator(UserOptionsCollection.class, new UserOptionsCollection(container));
        registerDecorator(InsertImageGroup.class, new InsertImageGroup(container));
        registerDecorator(InsertMediaGroup.class, new InsertMediaGroup(container));
        registerDecorator(InsertLinkGroup.class, new InsertLinkGroup(container));
        registerDecorator(InsertSpecialCharGroup.class, new InsertSpecialCharGroup(container));
    }
}
