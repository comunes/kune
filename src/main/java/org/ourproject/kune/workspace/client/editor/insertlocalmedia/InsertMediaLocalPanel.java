/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
package org.ourproject.kune.workspace.client.editor.insertlocalmedia;

import org.ourproject.kune.platf.client.services.SearcherConstants;
import org.ourproject.kune.platf.client.ui.download.FileDownloadUtils;
import org.ourproject.kune.platf.client.ui.rte.insertmedia.abstractmedia.InsertMediaAbstractPanel;
import org.ourproject.kune.workspace.client.search.AbstractLiveSearcherField;

import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.BasicMimeTypeDTO;
import cc.kune.core.shared.dto.LinkDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.calclab.suco.client.events.Listener;
import com.gwtext.client.core.UrlParam;
import com.gwtext.client.widgets.form.Field;
import com.gwtext.client.widgets.form.event.FieldListenerAdapter;

public class InsertMediaLocalPanel extends InsertMediaAbstractPanel implements InsertMediaLocalView {

    protected String src;

    public InsertMediaLocalPanel(final InsertMediaLocalPresenter presenter, final I18nTranslationService i18n,
            final FileDownloadUtils downloadUtils) {
        super(i18n.t("Local"), presenter);

        final AbstractLiveSearcherField cb = new AbstractLiveSearcherField(i18n,
                SearcherConstants.CONTENT_TEMPLATE_TEXT_PREFIX
                        + downloadUtils.getLogoImageUrl(new StateToken("{shortName}"))
                        + SearcherConstants.CONTENT_TEMPLATE_TEXT_SUFFIX, SearcherConstants.CONTENT_DATA_PROXY_URL,
                new Listener<LinkDTO>() {
                    public void onEvent(final LinkDTO link) {
                        src = link.getLink();
                    }
                });
        cb.setLabel(i18n.t("Local media"));
        cb.setHideLabel(false);
        cb.setAllowBlank(false);
        cb.setWidth(220);
        cb.addListener(new FieldListenerAdapter() {
            @Override
            public void onFocus(final Field field) {
                cb.setStoreBaseParams(new UrlParam[] {
                        new UrlParam(SearcherConstants.GROUP_PARAM, presenter.getCurrentGroupName()),
                        new UrlParam(SearcherConstants.MIMETYPE_PARAM, BasicMimeTypeDTO.AUDIO),
                        new UrlParam(SearcherConstants.MIMETYPE2_PARAM, BasicMimeTypeDTO.VIDEO) });
            }
        });

        hrefField.setVisible(false);
        hrefField.disable();

        super.insert(0, cb);
    }

    @Override
    public String getSrc() {
        return src;
    }
}
