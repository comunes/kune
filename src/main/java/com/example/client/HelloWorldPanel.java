package com.example.client;

import cc.kune.common.client.noti.NotifyUser;

import com.example.client.HelloWorldPresenter.HelloWorldView;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;

public class HelloWorldPanel extends ViewImpl implements HasText, HelloWorldView {

    interface HelloWorldPanelUiBinder extends UiBinder<Widget, HelloWorldPanel> {
    }

    private static HelloWorldPanelUiBinder uiBinder = GWT.create(HelloWorldPanelUiBinder.class);

    @UiField
    Button button;

    private final Widget widget;

    public HelloWorldPanel() {
        widget = uiBinder.createAndBindUi(this);
    }

    public HelloWorldPanel(final String firstName) {
        widget = uiBinder.createAndBindUi(this);
        button.setText(firstName);
    }

    @Override
    public Widget asWidget() {
        return widget;
    }

    @Override
    public String getText() {
        return button.getText();
    }

    @UiHandler("button")
    void onClick(final ClickEvent e) {
        NotifyUser.info("Hello world!");
    }

    @Override
    public void setText(final String text) {
        button.setText(text);
    }

}
