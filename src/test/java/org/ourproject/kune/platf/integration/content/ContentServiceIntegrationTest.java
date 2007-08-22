package org.ourproject.kune.platf.integration.content;

import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.errors.ContentNotFoundException;
import org.ourproject.kune.platf.client.rpc.ContentService;
import org.ourproject.kune.platf.integration.IntegrationTest;
import org.ourproject.kune.workspace.client.dto.StateDTO;

import com.google.gwt.user.client.rpc.SerializableException;
import com.google.inject.Inject;

public abstract class ContentServiceIntegrationTest extends IntegrationTest {
    @Inject
    protected ContentService contentService;

    protected StateDTO getDefaultContent() throws SerializableException, ContentNotFoundException {
	StateDTO content = contentService.getContent(session.getHash(), new StateToken());
	return content;
    }

}
