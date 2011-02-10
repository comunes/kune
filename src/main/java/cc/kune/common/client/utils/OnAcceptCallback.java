package cc.kune.common.client.utils;

import com.google.gwt.core.client.GWT;

public abstract class OnAcceptCallback implements SimpleCallback {

    @Override
    public void onCancel() {
        // Do nothing
        GWT.log("On cancel callback");
    }
}
