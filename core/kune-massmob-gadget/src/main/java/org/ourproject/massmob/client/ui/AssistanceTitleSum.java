package org.ourproject.massmob.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class AssistanceTitleSum extends Composite {

    interface AssistanceTitleSumUiBinder extends UiBinder<Widget, AssistanceTitleSum> {
    }

    private static AssistanceTitleSumUiBinder uiBinder = GWT.create(AssistanceTitleSumUiBinder.class);

    @UiField
    Label yesl;
    @UiField
    Label nol;
    @UiField
    Label maybel;
    @UiField
    Label noyetl;
    @UiField
    Label totall;

    public AssistanceTitleSum(final AssistanceTitleSumData data) {
        initWidget(uiBinder.createAndBindUi(this));
        set(data);
    }

    public void set(final AssistanceTitleSumData data) {
        yesl.setText(String.valueOf(data.yes));
        nol.setText(String.valueOf(data.no));
        maybel.setText(String.valueOf(data.maybe));
        noyetl.setText(String.valueOf(data.noyet));
        totall.setText(String.valueOf(data.total));
    }

}
