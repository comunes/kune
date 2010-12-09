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
package org.ourproject.kune.chat.client;

import org.ourproject.kune.chat.client.cnt.ChatRoom;
import org.ourproject.kune.chat.client.cnt.ChatRoomPanel;
import org.ourproject.kune.chat.client.cnt.ChatRoomPresenter;
import org.ourproject.kune.chat.client.ctx.ChatContext;
import org.ourproject.kune.chat.client.ctx.ChatContextPresenter;
import org.ourproject.kune.chat.client.ctx.room.AddRoom;
import org.ourproject.kune.chat.client.ctx.room.AddRoomPanel;
import org.ourproject.kune.chat.client.ctx.room.AddRoomPresenter;
import org.ourproject.kune.platf.client.app.Application;
import org.ourproject.kune.platf.client.app.ToolGroup;
import org.ourproject.kune.platf.client.registry.ContentCapabilitiesRegistry;
import org.ourproject.kune.platf.client.services.AbstractExtendedModule;
import org.ourproject.kune.platf.client.shortcuts.GlobalShortcutRegister;
import org.ourproject.kune.platf.client.ui.download.FileDownloadUtils;
import org.ourproject.kune.platf.client.ui.rte.insertmedia.abstractmedia.MediaUtils;
import org.ourproject.kune.platf.client.utils.DeferredCommandWrapper;
import org.ourproject.kune.workspace.client.cnt.ActionContentToolbar;
import org.ourproject.kune.workspace.client.cnt.ContentActionRegistry;
import org.ourproject.kune.workspace.client.ctxnav.ContextNavigator;
import org.ourproject.kune.workspace.client.cxt.ContextActionRegistry;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;
import org.ourproject.kune.workspace.client.themes.WsThemeManager;
import org.ourproject.kune.workspace.client.tool.ToolSelector;

import cc.kune.core.client.i18n.I18nUITranslationService;
import cc.kune.core.client.rpcservices.ContentServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.calclab.emiteuimodule.client.EmiteUIDialog;
import com.calclab.suco.client.ioc.decorator.Singleton;
import com.calclab.suco.client.ioc.module.Factory;

public class ChatClientModule extends AbstractExtendedModule {

    @Override
    public void onInstall() {

        register(ToolGroup.class, new Factory<ChatClientActions>(ChatClientActions.class) {
            @Override
            public ChatClientActions create() {
                return new ChatClientActions(i(I18nUITranslationService.class), i(Session.class),
                        i(ContentActionRegistry.class), i(ContextActionRegistry.class), p(ChatEngine.class),
                        p(AddRoom.class), p(DeferredCommandWrapper.class));
            }
        });

        register(ToolGroup.class, new Factory<ChatClientTool>(ChatClientTool.class) {
            @Override
            public ChatClientTool create() {
                return new ChatClientTool(i(I18nUITranslationService.class), i(WorkspaceSkeleton.class),
                        i(ToolSelector.class), i(WsThemeManager.class), i(ContentCapabilitiesRegistry.class));
            }
        });

        register(ToolGroup.class, new Factory<ChatContext>(ChatContext.class) {
            @Override
            public ChatContext create() {
                final ChatContextPresenter presenter = new ChatContextPresenter(i(StateManager.class),
                        p(ContextNavigator.class));
                return presenter;
            }
        });

        register(ToolGroup.class, new Factory<ChatEngine>(ChatEngine.class) {
            @Override
            public ChatEngine create() {
                final ChatEngineDefault chatEngineDefault = new ChatEngineDefault(i(I18nUITranslationService.class),
                        i(WorkspaceSkeleton.class), i(Application.class), i(Session.class), p(EmiteUIDialog.class),
                        p(FileDownloadUtils.class), i(GlobalShortcutRegister.class));
                return chatEngineDefault;
            }
        });

        register(ToolGroup.class, new Factory<ChatRoom>(ChatRoom.class) {
            @Override
            public ChatRoom create() {
                final ChatRoomPresenter presenter = new ChatRoomPresenter(i(StateManager.class), i(Session.class),
                        i(I18nUITranslationService.class), i(ActionContentToolbar.class),
                        i(ContentActionRegistry.class), p(FileDownloadUtils.class), p(MediaUtils.class));
                final ChatRoomPanel panel = new ChatRoomPanel(i(WorkspaceSkeleton.class),
                        i(I18nTranslationService.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(Singleton.class, new Factory<AddRoom>(AddRoom.class) {
            @Override
            public AddRoom create() {
                final AddRoomPresenter presenter = new AddRoomPresenter(i(Session.class), p(ContentServiceAsync.class),
                        i(StateManager.class));
                final AddRoomPanel panel = new AddRoomPanel(presenter, i(I18nTranslationService.class));
                presenter.init(panel);
                return presenter;
            }
        });

    }
}
