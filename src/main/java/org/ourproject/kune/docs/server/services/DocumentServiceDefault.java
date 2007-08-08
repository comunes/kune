package org.ourproject.kune.docs.server.services;

import org.ourproject.kune.docs.client.rpc.DocumentService;
import org.ourproject.kune.workspace.client.dto.ContentDataDTO;
import org.ourproject.kune.workspace.client.dto.ContextDataDTO;

public class DocumentServiceDefault implements DocumentService {
    private static final long serialVersionUID = 1L;

    // private ContentManager contentManager;
    // private AccessManager accessManager;
    // private UserSession userSession;

    public ContextDataDTO getContext(final String userHash, final String contextRef) {
	// ContextData context;
	//
	// Folder folder = folderManager.getFolder(contextRef);
	// if (accessManager.isAllowed(userSession.getUser(),
	// folder.getAccess(), Access.VIEW)) {
	//
	// }
	return null;
    }

    public ContentDataDTO getContent(final String userHash, final String ctxRef, final String docRef) {

	// Content content = contentManager.getContent(docRef);
	// if (accessManager.isAllowed(userSession.getUser(),
	// content.getAccess(), Access.VIEW)) {
	// content.setSimplifiedAcess(accessManager.getSimplified(userSession.getUser(),
	// content.getAccess())));
	// return mapper.map(content);
	// } else {
	// throw new AccessException();
	// }
	return null;
    }

    public String saveContent(final String userHash, final ContentDataDTO contentData) {
	// TODO Auto-generated method stub
	return null;
    }
}
