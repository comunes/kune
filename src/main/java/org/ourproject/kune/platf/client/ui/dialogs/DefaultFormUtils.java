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
