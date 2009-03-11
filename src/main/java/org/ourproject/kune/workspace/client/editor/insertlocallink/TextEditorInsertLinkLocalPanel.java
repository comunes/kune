/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
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
package org.ourproject.kune.workspace.client.editor.insertlocallink;

import org.ourproject.kune.platf.client.dto.LinkDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.i18n.I18nTranslationService;
import org.ourproject.kune.platf.client.ui.WindowUtils;
import org.ourproject.kune.platf.client.ui.download.FileDownloadUtils;
import org.ourproject.kune.platf.client.ui.rte.insertlink.TextEditorInsertElementView;
import org.ourproject.kune.workspace.client.search.AbstractLiveSearcherPanel;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.calclab.suco.client.events.Listener;

public class TextEditorInsertLinkLocalPanel extends AbstractLiveSearcherPanel implements TextEditorInsertLinkLocalView {

    private static final String DATA_PROXY_URL = "/kune/json/ContentJSONService/search";

    public TextEditorInsertLinkLocalPanel(final TextEditorInsertLinkLocalPresenter presenter,
            final WorkspaceSkeleton ws, I18nTranslationService i18n, FileDownloadUtils downloadUtils) {
        super(i18n, TEMPLATE_TEXT_PREFIX + downloadUtils.getLogoImageUrl(new StateToken("{shortName}"))
                + TEMPLATE_TEXT_SUFFIX, DATA_PROXY_URL, new Listener<LinkDTO>() {
            public void onEvent(LinkDTO link) {
                // FIXME
                presenter.onInsert("", WindowUtils.getLocation().getHref() + link.getLink());
            }
        });
        super.setTitle(i18n.t("Local link"));
        super.setFrame(true);
        super.setHeight(TextEditorInsertElementView.HEIGHT);
        super.setPaddings(20);
    }
}
