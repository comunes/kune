package org.ourproject.kune.platf.client.rpc;

import org.ourproject.kune.platf.client.dto.GroupDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class KuneServiceMocked extends MockedService implements KuneServiceAsync {
    public void getDefaultGroup(String userHash, final AsyncCallback callback) {
	delay(new Delayer() {
	    public void run() {
		callback.onSuccess(new GroupDTO());
	    }
	});
    }
}
