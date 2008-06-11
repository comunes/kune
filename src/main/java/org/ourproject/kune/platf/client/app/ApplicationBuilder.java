/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.platf.client.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.ourproject.kune.chat.client.ChatClientNewModule;
import org.ourproject.kune.docs.client.DocumentClientNewModule;
import org.ourproject.kune.platf.client.KunePlatform;
import org.ourproject.kune.platf.client.PlatformClientModule;
import org.ourproject.kune.platf.client.dispatch.ActionEvent;
import org.ourproject.kune.platf.client.dispatch.DefaultDispatcher;
import org.ourproject.kune.platf.client.dto.I18nLanguageDTO;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.platf.client.services.KuneErrorHandler;
import org.ourproject.kune.platf.client.services.KuneModule;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.WorkspaceClientModule;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.sitebar.Site;

import com.calclab.emiteuimodule.client.EmiteUIModule;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.WindowCloseListener;

public class ApplicationBuilder {

    public ApplicationBuilder() {
    }

    public void build(final I18nLanguageDTO initialLang, final HashMap<String, String> lexicon) {
	final Kune kune = Kune.create(new KuneModule(initialLang, lexicon), new EmiteUIModule(),
		new DocumentClientNewModule(), new ChatClientNewModule());

	KuneErrorHandler errorHandler = kune.getInstance(KuneErrorHandler.class);
	AsyncCallbackSimple.init(errorHandler);

	final Session session = kune.getSession();

	Site.showProgressLoading();
	Site.mask();

	final KunePlatform platform = kune.getPlatform();
	final StateManager stateManager = kune.getStateManager();
	final Application application = kune.getInstance(Application.class);
	I18nUITranslationService i18n = kune.getI18N();

	// Testing providers:
	platform.install(new PlatformClientModule(session, kune.getProvider(StateManager.class)));
	platform.install(new WorkspaceClientModule(session, stateManager, application.getWorkspace(), i18n));

	final DefaultDispatcher dispatcher = DefaultDispatcher.getInstance();

	application.init(dispatcher, stateManager, platform.getIndexedTools());
	subscribeActions(dispatcher, platform.getActions());

	Window.addWindowCloseListener(new WindowCloseListener() {
	    public void onWindowClosed() {
		application.stop();
	    }

	    public String onWindowClosing() {
		return null;
	    }
	});
	application.start();
    }

    private void subscribeActions(final DefaultDispatcher dispatcher, final ArrayList<ActionEvent<?>> actions) {
	ActionEvent<?> actionEvent;

	for (final Iterator<ActionEvent<?>> it = actions.iterator(); it.hasNext();) {
	    actionEvent = it.next();
	    dispatcher.subscribe(actionEvent.event, actionEvent.action);
	}
    }

}
