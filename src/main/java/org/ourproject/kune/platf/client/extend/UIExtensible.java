package org.ourproject.kune.platf.client.extend;

import com.google.gwt.user.client.ui.Widget;

public interface UIExtensible {

    public void attach(String id, Widget widget);

    public void dettach(String id, Widget widget);

}
