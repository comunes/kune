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

import org.ourproject.kune.platf.client.services.SearcherConstants;
import org.ourproject.kune.platf.client.ui.download.FileDownloadUtils;
import org.ourproject.kune.platf.client.ui.rte.insertlink.abstractlink.InsertLinkAbstractPanel;
import org.ourproject.kune.workspace.client.search.AbstractLiveSearcherField;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.LinkDTO;
import cc.kune.core.shared.dto.StateTokenUtils;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.calclab.suco.client.events.Listener;

public class InsertLinkLocalPanel extends InsertLinkAbstractPanel implements InsertLinkLocalView {

    private String href;

    public InsertLinkLocalPanel(final InsertLinkLocalPresenter presenter, final WorkspaceSkeleton ws,
            final I18nTranslationService i18n, final FileDownloadUtils downloadUtils,
            final StateTokenUtils stateTokenUtils) {
        super(i18n.t("Local link"), presenter);

        AbstractLiveSearcherField cb = new AbstractLiveSearcherField(i18n,
                SearcherConstants.CONTENT_TEMPLATE_TEXT_PREFIX
                        + downloadUtils.getLogoImageUrl(new StateToken("{shortName}"))
                        + SearcherConstants.CONTENT_TEMPLATE_TEXT_SUFFIX, SearcherConstants.CONTENT_DATA_PROXY_URL,
                new Listener<LinkDTO>() {
                    public void onEvent(final LinkDTO link) {
                        href = stateTokenUtils.getPublicUrl(new StateToken(link.getLink()));
                    }
                });
        cb.setLabel(i18n.t("Local content"));
        cb.setHideLabel(false);
        cb.setAllowBlank(false);
        cb.setWidth(220);
        hrefField.setVisible(false);
        hrefField.disable();
        super.insert(0, cb);
    }

    @Override
    public String getHref() {
        return href;
    }
}
