/**
 * 
 */
package org.ourproject.kune.docs.client.actions;

import org.ourproject.kune.platf.client.dto.AccessRolDTO;
import org.ourproject.kune.platf.client.dto.ContentStatusDTO;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;

import com.calclab.suco.client.provider.Provider;

public class ContentPublishAction extends ContentSetStatusAction {
    public ContentPublishAction(final Session session, final Provider<ContentServiceAsync> provider,
	    final I18nUITranslationService i18n) {
	super(session, provider, i18n, AccessRolDTO.Administrator, i18n.t("Published online"),
		ContentStatusDTO.publishedOnline);
    }
}