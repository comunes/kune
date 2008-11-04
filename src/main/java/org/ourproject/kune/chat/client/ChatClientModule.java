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

import org.ourproject.kune.chat.client.cnt.ChatContent;
import org.ourproject.kune.chat.client.cnt.ChatContentPresenter;
import org.ourproject.kune.chat.client.cnt.info.ChatInfo;
import org.ourproject.kune.chat.client.cnt.info.ChatInfoPanel;
import org.ourproject.kune.chat.client.cnt.room.ChatRoom;
import org.ourproject.kune.chat.client.cnt.room.ChatRoomControl;
import org.ourproject.kune.chat.client.cnt.room.ChatRoomControlPanel;
import org.ourproject.kune.chat.client.cnt.room.ChatRoomControlPresenter;
import org.ourproject.kune.chat.client.cnt.room.ChatRoomPanel;
import org.ourproject.kune.chat.client.cnt.room.ChatRoomPresenter;
import org.ourproject.kune.chat.client.ctx.ChatContext;
import org.ourproject.kune.chat.client.ctx.ChatContextPresenter;
import org.ourproject.kune.chat.client.ctx.rooms.RoomsAdmin;
import org.ourproject.kune.chat.client.ctx.rooms.RoomsAdminPresenter;
import org.ourproject.kune.platf.client.actions.ContentActionRegistry;
import org.ourproject.kune.platf.client.actions.ContextActionRegistry;
import org.ourproject.kune.platf.client.app.Application;
import org.ourproject.kune.platf.client.app.ToolGroup;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.platf.client.tool.ToolSelector;
import org.ourproject.kune.workspace.client.ctxnav.ContextNavigator;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;
import org.ourproject.kune.workspace.client.socialnet.GroupMembersSummary;
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
                        $(ContentActionRegistry.class), $(ContextActionRegistry.class), $$(ChatClientTool.class));
            }
        });

        register(ToolGroup.class, new Factory<ChatClientTool>(ChatClientTool.class) {
            @Override
            public ChatClientTool create() {
                return new ChatClientTool($(Session.class), $(Application.class), $(I18nUITranslationService.class),
                        $(EmiteUIDialog.class), $(WorkspaceSkeleton.class), $$(GroupMembersSummary.class),
                        $(ToolSelector.class), $(WsThemePresenter.class), $$(ChatContent.class), $$(ChatContext.class));
            }
        });

        register(Singleton.class, new Factory<ChatInfo>(ChatInfo.class) {
            @Override
            public ChatInfo create() {
                return new ChatInfoPanel();
            }
        });

        register(Singleton.class, new Factory<ChatContent>(ChatContent.class) {
            @Override
            public ChatContent create() {
                final ChatContentPresenter presenter = new ChatContentPresenter($(EmiteUIDialog.class),
                        $$(ChatInfo.class), $$(ChatRoom.class));
                return presenter;
            }
        });

        register(Singleton.class, new Factory<ChatContext>(ChatContext.class) {
            @Override
            public ChatContext create() {
                final ChatContextPresenter presenter = new ChatContextPresenter($$(RoomsAdmin.class));
                return presenter;
            }
        });

        register(Singleton.class, new Factory<ChatRoomControl>(ChatRoomControl.class) {
            @Override
            public ChatRoomControl create() {
                final ChatRoomControlPresenter presenter = new ChatRoomControlPresenter();
                final ChatRoomControlPanel panel = new ChatRoomControlPanel($(I18nUITranslationService.class),
                        presenter);
                presenter.init(panel);
                return presenter;
            }
        });

        register(Singleton.class, new Factory<ChatRoom>(ChatRoom.class) {
            @Override
            public ChatRoom create() {
                final ChatRoomPanel panel = new ChatRoomPanel($(WorkspaceSkeleton.class));
                final ChatRoomPresenter presenter = new ChatRoomPresenter(panel);
                return presenter;
            }
        });

        register(Singleton.class, new Factory<RoomsAdmin>(RoomsAdmin.class) {
            @Override
            public RoomsAdmin create() {
                final RoomsAdminPresenter presenter = new RoomsAdminPresenter($(ContextNavigator.class),
                        $(I18nUITranslationService.class), $$(StateManager.class), $(Session.class),
                        $$(ContentServiceAsync.class));
                return presenter;
            }
        });

    }
}
