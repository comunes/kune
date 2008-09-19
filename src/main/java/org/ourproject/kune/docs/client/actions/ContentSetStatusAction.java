package org.ourproject.kune.docs.client.actions;

import org.ourproject.kune.platf.client.actions.ActionMenuDescriptor;
import org.ourproject.kune.platf.client.actions.ActionPosition;
import org.ourproject.kune.platf.client.dto.AccessRolDTO;
import org.ourproject.kune.platf.client.dto.ContentStatusDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;

import com.calclab.suco.client.provider.Provider;
import com.calclab.suco.client.signal.Slot;

public abstract class ContentSetStatusAction extends ActionMenuDescriptor<StateToken> {

    public ContentSetStatusAction(final Session session, final Provider<ContentServiceAsync> contentServiceProvider,
	    final I18nUITranslationService i18n, final AccessRolDTO rol, final String textDescription,
	    final ContentStatusDTO status) {
	super(rol, ActionPosition.topbarAndItemMenu, new Slot<StateToken>() {
	    public void onEvent(final StateToken stateToken) {
		final AsyncCallbackSimple<Object> callback = new AsyncCallbackSimple<Object>() {
		    public void onSuccess(final Object result) {
			session.getCurrentState().setStatus(status);
		    }
		};
		if (status.equals(ContentStatusDTO.publishedOnline) || status.equals(ContentStatusDTO.rejected)) {
		    contentServiceProvider.get().setStatusAsAdmin(session.getUserHash(), stateToken, status, callback);
		} else {
		    contentServiceProvider.get().setStatus(session.getUserHash(), stateToken, status, callback);
		}
	    }
	});
	this.setTextDescription(textDescription);
	this.setParentMenuTitle(i18n.t("File"));
	this.setParentSubMenuTitle(i18n.t("Change the status"));
    }
}
