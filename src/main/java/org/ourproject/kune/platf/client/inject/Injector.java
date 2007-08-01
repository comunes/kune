package org.ourproject.kune.platf.client.inject;

import org.ourproject.kune.platf.client.dispatch.Dispatcher;


public interface Injector {
	void inject(Object object);
	void setDispatcher(Dispatcher dispatcher);
}
