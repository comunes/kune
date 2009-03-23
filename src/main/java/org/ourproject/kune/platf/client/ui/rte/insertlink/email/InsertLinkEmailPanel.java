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
package org.ourproject.kune.platf.client.ui.rte.insertlink.email;

import org.ourproject.kune.platf.client.i18n.I18nTranslationService;
import org.ourproject.kune.platf.client.ui.rte.insertlink.LinkInfo;
import org.ourproject.kune.platf.client.ui.rte.insertlink.abstractlink.InsertLinkAbstractPanel;

import com.gwtext.client.widgets.form.VType;

public class InsertLinkEmailPanel extends InsertLinkAbstractPanel implements InsertLinkEmailView {

    public InsertLinkEmailPanel(final InsertLinkEmailPresenter presenter, I18nTranslationService i18n) {
        super(i18n.t("Email link"), presenter);

        hrefField.setFieldLabel(i18n.t("Email"));
        hrefField.setVtype(VType.EMAIL);

        sameWindow.setVisible(false);
    }

    public void clear() {
        super.reset();
    }

    @Override
    protected void updateValues(LinkInfo linkInfo) {
        super.updateValues(linkInfo);
        if (!linkInfo.getHref().startsWith("mailto")) {
            hrefField.reset();
        }
    }
}
