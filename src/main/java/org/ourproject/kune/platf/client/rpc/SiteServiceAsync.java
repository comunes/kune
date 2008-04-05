
package org.ourproject.kune.platf.client.rpc;

import org.ourproject.kune.platf.client.dto.InitDataDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SiteServiceAsync {

    void getInitData(String userHash, AsyncCallback<InitDataDTO> callback);

}
