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

import org.ourproject.kune.platf.client.dto.LinkDTO;
import org.ourproject.kune.platf.client.i18n.I18nTranslationService;

import com.calclab.suco.client.events.Listener;
import com.gwtext.client.widgets.form.FormPanel;

public class AbstractLiveSearcherPanel extends FormPanel {

    public AbstractLiveSearcherPanel(final I18nTranslationService i18n, final String templateText,
            final String dataProxyUrl, final Listener<LinkDTO> listener) {
        super.setBorder(false);
        super.setWidth(275);
        super.setHideLabels(true);
        super.add(new AbstractLiveSearcherField(i18n, templateText, dataProxyUrl, listener));
    }

    public void reset() {
        super.getForm().reset();
    }

}
