package org.ourproject.kune.chat.client;

import org.ourproject.kune.platf.client.KunePlatform;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;

import com.calclab.emiteuimodule.client.EmiteUIDialog;
import com.calclab.suco.client.container.Container;
import com.calclab.suco.client.container.Provider;
import com.calclab.suco.client.modules.Module;
import com.calclab.suco.client.modules.ModuleBuilder;
import com.calclab.suco.client.scopes.SingletonScope;

public class ChatClientNewModule implements Module {
    public static ChatClientTool getChatClientTool(final Container components) {
	return components.getInstance(ChatClientTool.class);
    }

    public Class<? extends Module> getType() {
	return ChatClientNewModule.class;
    }

    public void onLoad(final ModuleBuilder builder) {
	builder.registerProvider(ChatClientTool.class, new Provider<ChatClientTool>() {
	    public ChatClientTool get() {
		return new ChatClientTool(builder.getInstance(I18nUITranslationService.class), builder
			.getInstance(EmiteUIDialog.class));
	    }
	}, SingletonScope.class);

	final KunePlatform platform = builder.getInstance(KunePlatform.class);
	final ChatClientTool chatClientTool = getChatClientTool(builder);
	platform.addTool(chatClientTool);

	final Session session = builder.getInstance(Session.class);
	final StateManager stateManager = builder.getInstance(StateManager.class);
	platform.install(new ChatClientModule(session, stateManager, chatClientTool));
    }
}
