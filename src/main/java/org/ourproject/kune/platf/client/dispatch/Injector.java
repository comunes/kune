package org.ourproject.kune.platf.client.dispatch;



public interface Injector {
	void inject(Object object);
	void setDispatcher(Dispatcher dispatcher);
}
