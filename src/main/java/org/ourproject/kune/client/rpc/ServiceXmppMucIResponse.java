package org.ourproject.kune.client.rpc;

public interface ServiceXmppMucIResponse {
	void accept(Object result);
	
	void failed(Throwable caught);
}
