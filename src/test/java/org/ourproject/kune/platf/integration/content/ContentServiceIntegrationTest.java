package org.ourproject.kune.platf.integration.content;

import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.rpc.ContentService;
import org.ourproject.kune.platf.integration.IntegrationTest;

import com.google.inject.Inject;

public abstract class ContentServiceIntegrationTest extends IntegrationTest {
    @Inject
    protected ContentService contentService;

    protected StateDTO getDefaultContent() throws Exception {
        final StateToken stateToken = new StateToken(getDefSiteGroupName());
        final StateDTO content = contentService.getContent(session.getHash(), stateToken);
        return content;
    }

}
