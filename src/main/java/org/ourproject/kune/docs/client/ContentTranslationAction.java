package org.ourproject.kune.docs.client;

import org.ourproject.kune.platf.client.actions.ActionButtonDescriptor;
import org.ourproject.kune.platf.client.actions.ActionPosition;
import org.ourproject.kune.platf.client.dto.AccessRolDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.site.Site;

import com.calclab.suco.client.signal.Slot;

public class ContentTranslationAction extends ActionButtonDescriptor<StateToken> {

    public ContentTranslationAction(final I18nUITranslationService i18n) {
	super(AccessRolDTO.Editor, ActionPosition.topbar, new Slot<StateToken>() {
	    public void onEvent(final StateToken stateToken) {
		Site.important(i18n.t("Sorry, this functionality is currently in development"));
	    }
	});
	this.setTextDescription(i18n.tWithNT("Translate", "used in button"));
	this.setToolTip(i18n.t("Translate this document to other languages"));
	// this.setIconUrl("images/");
    }
}
