package org.ourproject.kune.platf.client.extend;

import org.ourproject.kune.platf.client.dto.ContentTreeDTO;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ContentProviderMocked implements ContentProvider {
    public void getContentTree(String userHash, final AsyncCallback callback) {
        Timer timer = new Timer() {
            public void run() {
                ContentTreeDTO contentTree = new ContentTreeDTO();
                callback.onSuccess(contentTree);
            }
        };
        timer.schedule(1000);
    }
}
