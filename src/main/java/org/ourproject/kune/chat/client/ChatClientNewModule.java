package org.ourproject.kune.chat.client;

import org.ourproject.kune.platf.client.KunePlatform;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;

import com.calclab.emite.client.modular.Container;
import com.calclab.emite.client.modular.Module;
import com.calclab.emite.client.modular.ModuleBuilder;
import com.calclab.emite.client.modular.Provider;
import com.calclab.emite.client.modular.Scopes;
import com.calclab.emiteuimodule.client.EmiteUIDialog;

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
        }, Scopes.SINGLETON_EAGER);

        KunePlatform platform = builder.getInstance(KunePlatform.class);
        ChatClientTool chatClientTool = getChatClientTool(builder);
        platform.addTool(chatClientTool);

        Session session = builder.getInstance(Session.class);
        StateManager stateManager = builder.getInstance(StateManager.class);
        platform.install(new ChatClientModule(session, stateManager, chatClientTool));
    }
}
