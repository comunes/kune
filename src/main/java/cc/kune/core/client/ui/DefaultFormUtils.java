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
package cc.kune.core.client.ui;

import cc.kune.common.client.tooltip.Tooltip;

import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.form.Radio;

public class DefaultFormUtils {

    public static Radio createRadio(final FieldSet fieldSet, final String radioLabel, final String radioFieldName,
            final String radioTip, final String id) {
        final Radio radio = new Radio();
        radio.setName(radioFieldName);
        // radio.setAutoCreate(true);
        radio.setHideLabel(true);
        radio.setId(id);
        fieldSet.add(radio);

        if (radioTip != null) {
            Tooltip.to(radio, radioTip);
            // radio.setTitle(radioTip);
            radio.setBoxLabel(radioLabel);
            // radio.setBoxLabel(KuneUiUtils.genQuickTipLabel(radioLabel, null,
            // radioTip));
            // ToolTip tooltip = new ToolTip();
            // tooltip.setHtml(radioTip);
            // tooltip.setWidth(250);
            // tooltip.applyTo(radio);
        } else {
            radio.setBoxLabel(radioLabel);
        }
        return radio;
    }
}
