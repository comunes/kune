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
package org.ourproject.kune.platf.client.ui.dialogs;

import org.ourproject.kune.platf.client.ui.KuneUiUtils;

import com.gwtext.client.widgets.ToolTip;
import com.gwtext.client.widgets.form.FieldSet;
import com.gwtext.client.widgets.form.Radio;

public class DefaultFormUtils {

    public static String br() {
        return "<br/>";
    }

    public static String brbr() {
        return "<br/><br/>";
    }

    public static Radio createRadio(final FieldSet fieldSet, final String radioLabel, final String radioFieldName,
            final String radioTip, final String id) {
        Radio radio = new Radio();
        radio.setName(radioFieldName);
        radio.setAutoCreate(true);
        radio.setHideLabel(true);
        radio.setId(id);
        fieldSet.add(radio);

        if (radioTip != null) {
            radio.setBoxLabel(KuneUiUtils.genQuickTipLabel(radioLabel, null, radioTip));
            ToolTip tooltip = new ToolTip();
            tooltip.setHtml(radioTip);
            tooltip.setWidth(250);
            tooltip.applyTo(radio);
        } else {
            radio.setBoxLabel(radioLabel);
        }
        return radio;
    }
}
