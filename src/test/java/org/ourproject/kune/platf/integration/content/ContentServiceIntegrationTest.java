package org.ourproject.kune.platf.integration.content;

import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.rpc.ContentService;
import org.ourproject.kune.platf.integration.IntegrationTest;

import com.google.gwt.user.client.rpc.SerializableException;
import com.google.inject.Inject;

public abstract class ContentServiceIntegrationTest extends IntegrationTest {
    @Inject
    protected ContentService contentService;

    protected StateDTO getDefaultContent() throws SerializableException {
        StateToken stateToken = new StateToken(getDefSiteGroupName());
        StateDTO content = contentService.getContent(session.getHash(), getDefSiteGroupName(), stateToken);
        return content;
    }

}
