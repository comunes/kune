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

package org.ourproject.kune.docs.client.cnt.reader;

import org.ourproject.kune.docs.client.DocumentClientTool;
import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.BasicMimeTypeDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;

public class DocumentReaderPresenter implements DocumentReader {
    private final DocumentReaderView view;
    private final I18nUITranslationService i18n;
    private final Session session;

    public DocumentReaderPresenter(final Session session, final DocumentReaderView view,
	    final I18nUITranslationService i18n) {
	this.session = session;
	this.view = view;
	this.i18n = i18n;
    }

    public View getView() {
	return view;
    }

    public void showDocument(final StateToken token, final String text, final String typeId,
	    final BasicMimeTypeDTO mimeType) {
	if (typeId.equals(DocumentClientTool.TYPE_UPLOADEDFILE)) {
	    if (mimeType != null) {
		final String url = "/kune/servlets/FileDownloadManager?token=" + token + "&hash="
			+ session.getUserHash();
		if (mimeType.getType().equals("image")) {
		    view.setContent("<img src=\"" + url + "\">");
		} else if (mimeType.toString().equals("text/plain")) {
		    view.setContent(text);
		} else {
		    view.setContent("<a href=\"" + url + "\">" + i18n.t("Download") + "</a>");
		}
	    } else {
		view.setContent("<a href=\"" + "dd" + "\">" + i18n.t("Download") + "</a>");
	    }
	} else {
	    view.setContent(text);
	}
    }
}
