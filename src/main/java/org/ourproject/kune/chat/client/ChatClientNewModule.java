package org.ourproject.kune.chat.client;

import org.ourproject.kune.platf.client.KunePlatform;
import org.ourproject.kune.platf.client.app.Application;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.ui.newtmp.skel.WorkspaceSkeleton;
import org.ourproject.kune.workspace.client.workspace.GroupMembersSummary;

import com.calclab.emiteuimodule.client.EmiteUIDialog;
import com.calclab.suco.client.modules.AbstractModule;
import com.calclab.suco.client.provider.Factory;
import com.calclab.suco.client.scopes.SingletonScope;

public class ChatClientNewModule extends AbstractModule {

    public ChatClientNewModule() {
	super(ChatClientNewModule.class);
    }

    @Override
    public void onLoad() {
	register(SingletonScope.class, new Factory<ChatClientTool>(ChatClientTool.class) {
	    public ChatClientTool create() {
		return new ChatClientTool($(Session.class), $(Application.class), $(I18nUITranslationService.class),
			$(EmiteUIDialog.class), $(WorkspaceSkeleton.class), $p(GroupMembersSummary.class));
	    }
	});

	final ChatClientTool chatClientTool = $(ChatClientTool.class);
	$(KunePlatform.class).addTool(chatClientTool);
	$(KunePlatform.class).install(new ChatClientModule($(Session.class), $(StateManager.class), chatClientTool));
    }
}
