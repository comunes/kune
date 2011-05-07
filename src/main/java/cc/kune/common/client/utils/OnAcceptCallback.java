package cc.kune.common.client.utils;

import cc.kune.common.client.log.Log;

public abstract class OnAcceptCallback implements SimpleResponseCallback {

    @Override
    public void onCancel() {
        // Do nothing
        Log.debug("On cancel callback, do nothing");
    }
}
