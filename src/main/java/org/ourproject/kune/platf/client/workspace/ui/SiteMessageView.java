package org.ourproject.kune.platf.client.workspace.ui;

import com.google.gwt.user.client.ui.AbstractImagePrototype;

public interface SiteMessageView {

    public void setMessage(String message);

    public void setMessage(String message, AbstractImagePrototype type, String oldStyle, String newStyle);

    public void adjustWidth(int windowWidth);

    public void show();

    public void hide();

    public void reset();

}
