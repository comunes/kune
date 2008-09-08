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
import org.ourproject.kune.platf.client.app.Application;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.platf.client.tool.ToolSelector;
import org.ourproject.kune.workspace.client.component.WorkspaceDeckPanel;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;
import org.ourproject.kune.workspace.client.socialnet.GroupMembersSummary;
import org.ourproject.kune.workspace.client.themes.WsThemePresenter;
import org.ourproject.kune.workspace.client.ui.ctx.nav.ContextNavigator;

import com.calclab.emiteuimodule.client.EmiteUIDialog;
import com.calclab.suco.client.module.AbstractModule;
import com.calclab.suco.client.provider.Factory;
import com.calclab.suco.client.scope.SingletonScope;

public class ChatClientModule extends AbstractModule {

    public ChatClientModule() {
    }

    @Override
    public void onLoad() {

	register(SingletonScope.class, new Factory<ChatInfo>(ChatInfo.class) {
	    public ChatInfo create() {
		return new ChatInfoPanel();
	    }
	});

	register(SingletonScope.class, new Factory<ChatContent>(ChatContent.class) {
	    public ChatContent create() {
		final WorkspaceDeckPanel panel = new WorkspaceDeckPanel();
		final ChatContentPresenter presenter = new ChatContentPresenter($(EmiteUIDialog.class), panel,
			$$(ChatInfo.class), $$(ChatRoom.class));
		return presenter;
	    }
	});

	register(SingletonScope.class, new Factory<ChatContext>(ChatContext.class) {
	    public ChatContext create() {
		final WorkspaceDeckPanel panel = new WorkspaceDeckPanel();
		final ChatContextPresenter presenter = new ChatContextPresenter(panel, $$(RoomsAdmin.class));
		return presenter;
	    }
	});

	register(SingletonScope.class, new Factory<ChatRoomControl>(ChatRoomControl.class) {
	    public ChatRoomControl create() {
		final ChatRoomControlPresenter presenter = new ChatRoomControlPresenter();
		final ChatRoomControlPanel panel = new ChatRoomControlPanel($(I18nUITranslationService.class),
			presenter);
		presenter.init(panel);
		return presenter;
	    }
	});

	register(SingletonScope.class, new Factory<ChatRoom>(ChatRoom.class) {
	    public ChatRoom create() {
		final ChatRoomPanel panel = new ChatRoomPanel($(WorkspaceSkeleton.class));
		final ChatRoomPresenter presenter = new ChatRoomPresenter(panel);
		return presenter;
	    }
	});
	register(SingletonScope.class, new Factory<RoomsAdmin>(RoomsAdmin.class) {
	    public RoomsAdmin create() {
		final RoomsAdminPresenter presenter = new RoomsAdminPresenter($(ContextNavigator.class),
			$(I18nUITranslationService.class), $$(StateManager.class), $(Session.class),
			$$(ContentServiceAsync.class));
		return presenter;
	    }
	});

	register(SingletonScope.class, new Factory<ChatClientTool>(ChatClientTool.class) {
	    public ChatClientTool create() {
		return new ChatClientTool($(Session.class), $(Application.class), $(I18nUITranslationService.class),
			$(EmiteUIDialog.class), $(WorkspaceSkeleton.class), $$(GroupMembersSummary.class),
			$(ToolSelector.class), $(WsThemePresenter.class), $$(ChatContent.class), $$(ChatContext.class));
	    }
	});

	$(ChatClientTool.class);

    }
}
