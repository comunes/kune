package org.ourproject.kune.chat.client;

import org.ourproject.kune.docs.client.DocumentClientTool;
import org.ourproject.kune.platf.client.actions.ActionToolbarButtonDescriptor;
import org.ourproject.kune.platf.client.actions.ActionToolbarButtonSeparator;
import org.ourproject.kune.platf.client.actions.ActionToolbarPosition;
import org.ourproject.kune.platf.client.actions.ContentActionRegistry;
import org.ourproject.kune.platf.client.actions.ContextActionRegistry;
import org.ourproject.kune.platf.client.dto.AccessRolDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;

import com.calclab.suco.client.ioc.Provider;
import com.calclab.suco.client.listener.Listener;

public class ChatClientActions {

    private final Session session;
    private final Provider<ChatClientTool> chatClientToolProvider;
    private final ContentActionRegistry contentActionRegistry;

    public ChatClientActions(final I18nUITranslationService i18n, final Session session,
	    final ContentActionRegistry contentActionRegistry, final ContextActionRegistry contextActionRegistry,
	    final Provider<ChatClientTool> chatClientToolProvider) {
	this.session = session;
	this.contentActionRegistry = contentActionRegistry;
	this.chatClientToolProvider = chatClientToolProvider;
	createActions();
    }

    private void createActions() {
	final ActionToolbarButtonDescriptor<StateToken> chatAbout = new ActionToolbarButtonDescriptor<StateToken>(
		AccessRolDTO.Viewer, ActionToolbarPosition.topbar, new Listener<StateToken>() {
		    public void onEvent(final StateToken token) {
			chatClientToolProvider.get().getChat().joinRoom(token.toString().replaceAll("\\.", "-"),
				session.getCurrentUserInfo().getShortName());
			chatClientToolProvider.get().getChat().show();
		    }
		});
	// chatAbout.setTextDescription(i18n.t("Chat about"));
	chatAbout.setIconUrl("images/emite-room.png");
	chatAbout.setToolTip("Chat and comment this");
	chatAbout.setLeftSeparator(ActionToolbarButtonSeparator.fill);

	contentActionRegistry.addAction(chatAbout, DocumentClientTool.TYPE_DOCUMENT);
	contentActionRegistry.addAction(chatAbout, DocumentClientTool.TYPE_GALLERY);
	contentActionRegistry.addAction(chatAbout, DocumentClientTool.TYPE_WIKIPAGE);
	contentActionRegistry.addAction(chatAbout, DocumentClientTool.TYPE_POST);
	contentActionRegistry.addAction(chatAbout, DocumentClientTool.TYPE_UPLOADEDFILE);
    }
}
