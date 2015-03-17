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
package cc.kune.client;

import cc.kune.barters.client.BartersParts;
import cc.kune.blogs.client.BlogsParts;
import cc.kune.bootstrap.client.BSGuiProvider;
import cc.kune.chat.client.ChatParts;
import cc.kune.common.client.events.EventBusWithLogging;
import cc.kune.common.client.shortcuts.GlobalShortcutRegister;
import cc.kune.core.client.CoreParts;
import cc.kune.core.client.actions.xml.XMLActionsParser;
import cc.kune.core.client.cookies.MotdManager;
import cc.kune.core.client.events.UserSignInOrSignOutEvent;
import cc.kune.core.client.events.UserSignInOrSignOutEvent.UserSignInOrSignOutHandler;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.sitebar.ErrorsDialog;
import cc.kune.core.client.state.SessionExpirationManager;
import cc.kune.core.client.state.SessionInstance;
import cc.kune.core.client.state.impl.SessionChecker;
import cc.kune.core.client.ws.CorePresenter;
import cc.kune.docs.client.DocsParts;
import cc.kune.events.client.EventsParts;
import cc.kune.gspace.client.GSpaceParts;
import cc.kune.gspace.client.tool.ContentViewerSelector;
import cc.kune.hspace.client.HSpaceParts;
import cc.kune.lists.client.ListsParts;
import cc.kune.polymer.client.PolymerId;
import cc.kune.polymer.client.PolymerUtils;
import cc.kune.tasks.client.TasksParts;
import cc.kune.trash.client.TrashParts;
import cc.kune.wiki.client.WikiParts;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.mvp.client.Bootstrapper;

public class KuneBootstrapper implements Bootstrapper {

  protected static PolymerId[] unresolvedIdList = new PolymerId[] { PolymerId.HOME_SCROLLER,
      PolymerId.GROUP_SPACE, PolymerId.USER_SPACE, PolymerId.SITEBAR_RIGHT_EXTENSIONBAR,
      PolymerId.HOME_TOOLBAR };

  private final ContentViewerSelector contentViewerSelector;

  private final CorePresenter corePresenter;

  private final GlobalShortcutRegister globalShortcutRegister;

  private final Provider<MotdManager> motdManager;

  private final SessionChecker sessionChecker;

  @Inject
  public KuneBootstrapper(final SessionChecker sessionChecker,
      final ContentViewerSelector contentViewerSelector,
      final GlobalShortcutRegister globalShortcutRegister,
      final SessionExpirationManager sessionExpirationManager,
      final EventBusWithLogging eventBusWithLogging, final ErrorsDialog errorsDialog,
      final DocsParts docs, final BlogsParts blogs, final WikiParts wiki, final EventsParts events,
      final TasksParts tasks, final ListsParts lists, final ChatParts chats, final BartersParts barters,
      final TrashParts trash, final CorePresenter corePresenter,
      final OnAppStartFactory onAppStartFactory,

      // Here you define the gui ui provider (gwt, gxt, bootstrap, polymer)
      final BSGuiProvider guiProvider,
      // GwtGuiProvider guiProvider

      final CoreParts coreParts, final GSpaceParts gSpaceParts, final HSpaceParts hSpaceParts,

      final Provider<MotdManager> motdManager, final XMLActionsParser xmlActionsParser) {

    this.sessionChecker = sessionChecker;
    this.contentViewerSelector = contentViewerSelector;
    this.globalShortcutRegister = globalShortcutRegister;
    this.corePresenter = corePresenter;
    this.motdManager = motdManager;
  }

  @Override
  public void onBootstrap() {

    corePresenter.forceReveal();

    sessionChecker.check(new AsyncCallbackSimple<Void>() {
      @Override
      public void onSuccess(final Void result) {
        // Do nothing
      }
    });

    contentViewerSelector.init();

    globalShortcutRegister.enable();

    SessionInstance.get().onUserSignInOrSignOut(true, new UserSignInOrSignOutHandler() {
      @Override
      public void onUserSignInOrSignOut(final UserSignInOrSignOutEvent event) {
        // TODO Auto-generated method stub
        // Polymer preventing FOUC
        // https://www.polymer-project.org/docs/polymer/styling.html#fouc-prevention
        for (final PolymerId id : unresolvedIdList) {
          PolymerUtils.resolved(id);
        }
        // PolymerUtils.resolved(RootPanel.getBodyElement());
        PolymerUtils.hideSpinner();
        motdManager.get();
      }
    });
  }
}