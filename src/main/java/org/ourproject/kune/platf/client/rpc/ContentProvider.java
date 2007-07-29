package org.ourproject.kune.platf.client.rpc;

import org.ourproject.kune.platf.client.dto.ContentTreeDTO;

import com.google.gwt.user.client.rpc.RemoteService;

public interface ContentProvider extends RemoteService {
    public ContentTreeDTO getContentTree(String userHash);

}
