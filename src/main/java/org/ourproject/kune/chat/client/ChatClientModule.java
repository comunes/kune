package org.ourproject.kune.chat.client;

import org.ourproject.kune.chat.client.cnt.ChatContent;
import org.ourproject.kune.chat.client.cnt.ChatContentPresenter;
import org.ourproject.kune.chat.client.cnt.info.ChatInfo;
import org.ourproject.kune.chat.client.cnt.info.ui.ChatInfoPanel;
import org.ourproject.kune.chat.client.cnt.room.ChatRoom;
import org.ourproject.kune.chat.client.cnt.room.ChatRoomControl;
import org.ourproject.kune.chat.client.cnt.room.ChatRoomControlPresenter;
import org.ourproject.kune.chat.client.cnt.room.ChatRoomPresenter;
import org.ourproject.kune.chat.client.cnt.room.ui.ChatRoomControlPanel;
import org.ourproject.kune.chat.client.cnt.room.ui.ChatRoomPanel;
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
import org.ourproject.kune.workspace.client.component.WorkspaceDeckPanel;
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

    public ChatClientModule() {
    }

    @Override
    public void onInstall() {

        register(Singleton.class, new Factory<ChatInfo>(ChatInfo.class) {
            @Override
            public ChatInfo create() {
                return new ChatInfoPanel();
            }
        });

        register(ToolGroup.class, new Factory<ChatClientActions>(ChatClientActions.class) {
            @Override
            public ChatClientActions create() {
                return new ChatClientActions($(I18nUITranslationService.class), $(Session.class),
                        $(ContentActionRegistry.class), $(ContextActionRegistry.class), $$(ChatClientTool.class));
            }
        });

        register(Singleton.class, new Factory<ChatContent>(ChatContent.class) {
            @Override
            public ChatContent create() {
                final WorkspaceDeckPanel panel = new WorkspaceDeckPanel();
                final ChatContentPresenter presenter = new ChatContentPresenter($(EmiteUIDialog.class), panel,
                        $$(ChatInfo.class), $$(ChatRoom.class));
                return presenter;
            }
        });

        register(Singleton.class, new Factory<ChatContext>(ChatContext.class) {
            @Override
            public ChatContext create() {
                final WorkspaceDeckPanel panel = new WorkspaceDeckPanel();
                final ChatContextPresenter presenter = new ChatContextPresenter(panel, $$(RoomsAdmin.class));
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

        register(ToolGroup.class, new Factory<ChatClientTool>(ChatClientTool.class) {
            @Override
            public ChatClientTool create() {
                return new ChatClientTool($(Session.class), $(Application.class), $(I18nUITranslationService.class),
                        $(EmiteUIDialog.class), $(WorkspaceSkeleton.class), $$(GroupMembersSummary.class),
                        $(ToolSelector.class), $(WsThemePresenter.class), $$(ChatContent.class), $$(ChatContext.class));
            }
        });

    }
}
