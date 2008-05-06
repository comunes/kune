package org.ourproject.kune.platf.client.extend;


public interface ExtensibleWidget {

    public void attach(ExtensibleWidgetChild child);

    public void detach(ExtensibleWidgetChild child);

    public void detachAll(String id);

}
