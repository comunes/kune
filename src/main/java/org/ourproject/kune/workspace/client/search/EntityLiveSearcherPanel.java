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
package org.ourproject.kune.workspace.client.search;

import org.ourproject.kune.platf.client.services.SearcherConstants;
import org.ourproject.kune.platf.client.ui.dialogs.BasicDialog;
import org.ourproject.kune.platf.client.ui.download.FileDownloadUtils;

import cc.kune.core.shared.dto.LinkDTO;
import cc.kune.core.shared.dto.StateToken;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.calclab.suco.client.events.Listener;

public class EntityLiveSearcherPanel implements EntityLiveSearcherView {

    public static final String ENT_LIVE_SEARCH_DIALOG = "k-elivesp-dialog";
    private final EntityLiveSearcherPresenter presenter;
    private BasicDialog dialog;
    private final EntityLiveSearcherType searchType;
    private final I18nTranslationService i18n;
    private AbstractLiveSearcherPanel liveSearcher;
    private final FileDownloadUtils downloadUtils;

    public EntityLiveSearcherPanel(final EntityLiveSearcherPresenter presenter,
            final EntityLiveSearcherType searchType, final I18nTranslationService i18n,
            final FileDownloadUtils downloadUtils) {
        this.presenter = presenter;
        this.searchType = searchType;
        this.i18n = i18n;
        this.downloadUtils = downloadUtils;
    }

    public void center() {
        dialog.center();
    }

    public void hide() {
        dialog.hide();
        liveSearcher.reset();
    }

    public void show() {
        if (dialog == null) {
            createGroupSearchDialog(searchType);
        }
        dialog.show();
        dialog.center();
    }

    private void createGroupSearchDialog(final EntityLiveSearcherType searchType) {
        String title;
        if (searchType.equals(EntityLiveSearcherType.groups)) {
            title = i18n.t("Search existing users and groups");
        } else {
            title = i18n.t("Search existing users");
        }
        dialog = new BasicDialog(ENT_LIVE_SEARCH_DIALOG, title, true, false, 285, 55);
        dialog.setClosable(true);
        dialog.setCollapsible(false);

        String dataProxyUrl = "";
        switch (searchType) {
        case groups:
            dataProxyUrl = SearcherConstants.GROUP_DATA_PROXY_URL;
            break;
        case users:
            dataProxyUrl = SearcherConstants.USER_DATA_PROXY_URL;
            break;
        default:
            break;
        }

        String templateText = SearcherConstants.CONTENT_TEMPLATE_TEXT_PREFIX
                + downloadUtils.getLogoImageUrl(new StateToken("{shortName}"))
                + SearcherConstants.CONTENT_TEMPLATE_TEXT_SUFFIX;

        liveSearcher = new AbstractLiveSearcherPanel(i18n, templateText, dataProxyUrl, new Listener<LinkDTO>() {
            public void onEvent(final LinkDTO link) {
                presenter.onSelection(link);
            }
        });

        dialog.add(liveSearcher);
    }
}
