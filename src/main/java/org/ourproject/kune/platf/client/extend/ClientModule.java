package org.ourproject.kune.platf.client.extend;

import org.ourproject.kune.platf.client.dispatch.Dispatcher;

public interface ClientModule {
    public void configure(Register register);
	public void registerActions(Dispatcher dispatcher);
}
