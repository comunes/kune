
package org.ourproject.kune.platf.client.rpc;

import org.ourproject.kune.platf.client.dto.InitDataDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class SiteServiceMocked extends MockedService implements SiteServiceAsync {

    public void getInitData(final String userHash, final AsyncCallback<InitDataDTO> callback) {
    }

}
