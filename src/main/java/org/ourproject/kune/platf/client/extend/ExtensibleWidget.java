package org.ourproject.kune.platf.client.extend;

import com.google.gwt.user.client.ui.Widget;

public interface ExtensibleWidget {

    public void attach(String id, Widget widget);

    public void detach(String id, Widget widget);

    public void detachAll(String id);

}
