package org.ourproject.kune.chat.client;

import org.ourproject.kune.docs.client.DocumentClientTool;
import org.ourproject.kune.platf.client.actions.ActionButtonDescriptor;
import org.ourproject.kune.platf.client.actions.ActionButtonSeparator;
import org.ourproject.kune.platf.client.actions.ActionPosition;
import org.ourproject.kune.platf.client.actions.ContentActionRegistry;
import org.ourproject.kune.platf.client.actions.ContextActionRegistry;
import org.ourproject.kune.platf.client.dto.AccessRolDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;

import com.calclab.suco.client.ioc.Provider;
import com.calclab.suco.client.listener.Listener;

public class ChatClientActions {

    private final I18nUITranslationService i18n;
    private final Session session;
    private final Provider<ChatClientTool> chatClientToolProvider;
    private final ContentActionRegistry contentActionRegistry;

    public ChatClientActions(final I18nUITranslationService i18n, final Session session,
	    final ContentActionRegistry contentActionRegistry, final ContextActionRegistry contextActionRegistry,
	    final Provider<ChatClientTool> chatClientToolProvider) {
	this.i18n = i18n;
	this.session = session;
	this.contentActionRegistry = contentActionRegistry;
	this.chatClientToolProvider = chatClientToolProvider;
	createActions();
    }

    private void createActions() {
	final ActionButtonDescriptor<StateToken> chatAbout = new ActionButtonDescriptor<StateToken>(
		AccessRolDTO.Viewer, ActionPosition.topbar, new Listener<StateToken>() {
		    public void onEvent(final StateToken token) {
			chatClientToolProvider.get().getChat().joinRoom(token.toString().replaceAll("\\.", "-"),
				session.getCurrentUserInfo().getShortName());
			chatClientToolProvider.get().getChat().show();
		    }
		});
	// chatAbout.setTextDescription(i18n.t("Chat about"));
	chatAbout.setIconUrl("images/emite-room.png");
	chatAbout.setToolTip("Chat online about this");
	chatAbout.setLeftSeparator(ActionButtonSeparator.fill);

	contentActionRegistry.addAction(DocumentClientTool.TYPE_DOCUMENT, chatAbout);
	contentActionRegistry.addAction(DocumentClientTool.TYPE_GALLERY, chatAbout);
	contentActionRegistry.addAction(DocumentClientTool.TYPE_WIKIPAGE, chatAbout);
	contentActionRegistry.addAction(DocumentClientTool.TYPE_POST, chatAbout);
	contentActionRegistry.addAction(DocumentClientTool.TYPE_UPLOADEDFILE, chatAbout);
    }
}
