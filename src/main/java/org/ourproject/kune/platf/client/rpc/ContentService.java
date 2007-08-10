package org.ourproject.kune.platf.client.rpc;

import org.ourproject.kune.platf.client.errors.ContentNotFoundException;
import org.ourproject.kune.workspace.client.dto.ContentDataDTO;

import com.google.gwt.user.client.rpc.RemoteService;

public interface ContentService extends RemoteService {
    ContentDataDTO getContent(String userHash, String groupName, String toolName, String folderRef, String contentRef)
	    throws ContentNotFoundException;
}
