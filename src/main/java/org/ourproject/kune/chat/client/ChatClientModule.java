/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
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
 */package org.ourproject.kune.chat.client;

import org.ourproject.kune.chat.client.cnt.room.ChatRoom;
import org.ourproject.kune.chat.client.cnt.room.ChatRoomPanel;
import org.ourproject.kune.chat.client.cnt.room.ChatRoomPresenter;
import org.ourproject.kune.chat.client.ctx.ChatContext;
import org.ourproject.kune.chat.client.ctx.ChatContextPresenter;
import org.ourproject.kune.chat.client.ctx.room.AddRoom;
import org.ourproject.kune.chat.client.ctx.room.AddRoomPanel;
import org.ourproject.kune.chat.client.ctx.room.AddRoomPresenter;
import org.ourproject.kune.platf.client.actions.ContentActionRegistry;
import org.ourproject.kune.platf.client.actions.ContextActionRegistry;
import org.ourproject.kune.platf.client.actions.toolbar.ActionContentToolbar;
import org.ourproject.kune.platf.client.app.Application;
import org.ourproject.kune.platf.client.app.ToolGroup;
import org.ourproject.kune.platf.client.registry.ContentCapabilitiesRegistry;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.platf.client.tool.ToolSelector;
import org.ourproject.kune.workspace.client.ctxnav.ContextNavigator;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;
import org.ourproject.kune.workspace.client.themes.WsThemePresenter;

import com.calclab.emiteuimodule.client.EmiteUIDialog;
import com.calclab.suco.client.ioc.decorator.Singleton;
import com.calclab.suco.client.ioc.module.AbstractModule;
import com.calclab.suco.client.ioc.module.Factory;

public class ChatClientModule extends AbstractModule {

    @Override
    public void onInstall() {

        register(ToolGroup.class, new Factory<ChatClientActions>(ChatClientActions.class) {
            @Override
            public ChatClientActions create() {
                return new ChatClientActions($(I18nUITranslationService.class), $(Session.class),
                        $(ContentActionRegistry.class), $(ContextActionRegistry.class), $$(ChatEngine.class),
                        $$(AddRoom.class));
            }
        });

        register(ToolGroup.class, new Factory<ChatClientTool>(ChatClientTool.class) {
            @Override
            public ChatClientTool create() {
                return new ChatClientTool($(I18nUITranslationService.class), $(WorkspaceSkeleton.class),
                        $(ToolSelector.class), $(WsThemePresenter.class), $(ContentCapabilitiesRegistry.class));
            }
        });

        register(ToolGroup.class, new Factory<ChatContext>(ChatContext.class) {
            @Override
            public ChatContext create() {
                final ChatContextPresenter presenter = new ChatContextPresenter($(StateManager.class),
                        $$(ContextNavigator.class));
                return presenter;
            }
        });

        register(ToolGroup.class, new Factory<ChatEngine>(ChatEngine.class) {
            @Override
            public ChatEngine create() {
                return new ChatEngineDefault($(I18nUITranslationService.class), $(WorkspaceSkeleton.class),
                        $(Application.class), $(Session.class), $$(EmiteUIDialog.class));
            }
        });

        register(ToolGroup.class, new Factory<ChatRoom>(ChatRoom.class) {
            @Override
            public ChatRoom create() {
                final ChatRoomPresenter presenter = new ChatRoomPresenter($(StateManager.class), $(Session.class),
                        $(I18nUITranslationService.class), $(ActionContentToolbar.class),
                        $(ContentActionRegistry.class));
                final ChatRoomPanel panel = new ChatRoomPanel($(WorkspaceSkeleton.class),
                        $(I18nTranslationService.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(Singleton.class, new Factory<AddRoom>(AddRoom.class) {
            @Override
            public AddRoom create() {
                final AddRoomPresenter presenter = new AddRoomPresenter($(Session.class),
                        $$(ContentServiceAsync.class), $(StateManager.class));
                final AddRoomPanel panel = new AddRoomPanel(presenter, $(I18nTranslationService.class));
                presenter.init(panel);
                return presenter;
            }
        });

    }
}
