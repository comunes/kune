package com.example.client;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

public class HelloWorldPresenter extends
        Presenter<HelloWorldPresenter.HelloWorldView, HelloWorldPresenter.HelloWorldProxy> {

    @ProxyCodeSplit
    public interface HelloWorldProxy extends Proxy<HelloWorldPresenter> {
    }

    public interface HelloWorldView extends View {
    }

    @Inject
    public HelloWorldPresenter(final EventBus eventBus, final HelloWorldView view, final HelloWorldProxy proxy) {
        super(eventBus, view, proxy);
    }

    @Override
    protected void revealInParent() {
        RevealRootContentEvent.fire(this, this);
    }
}