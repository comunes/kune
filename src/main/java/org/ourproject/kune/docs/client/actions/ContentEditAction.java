package org.ourproject.kune.docs.client.actions;

import org.ourproject.kune.platf.client.actions.ActionButtonDescriptor;
import org.ourproject.kune.platf.client.actions.ActionPosition;
import org.ourproject.kune.platf.client.dto.AccessRolDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;

import com.calclab.suco.client.signal.Slot;

public class ContentEditAction extends ActionButtonDescriptor<StateToken> {

    public ContentEditAction(final I18nUITranslationService i18n) {
	super(AccessRolDTO.Editor, ActionPosition.topbar, new Slot<StateToken>() {
	    public void onEvent(final StateToken stateToken) {
	    }
	});
	this.setTextDescription(i18n.tWithNT("Edit", "used in button"));
	// this.setIconUrl("images/");
    }
}
