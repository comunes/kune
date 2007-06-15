package org.ourproject.kune.client.rpc;

/**
 * NOTA: mirar la nota de ServiceXmppMucServiceManager
 * en cualquier caso, esta clase lo único que hace es duplicar
 * el funcionamiento de AsyncCallback, sin aportar ninguna
 * ventaja... creo que debería desaparecer
 */
public interface ServiceXmppMucIResponse {
	void accept(Object result);
	
	void failed(Throwable caught);
}
