package org.ourproject.kune.platf.integration.content;

import org.ourproject.kune.platf.integration.IntegrationTest;

import cc.kune.core.client.rpcservices.ContentService;
import cc.kune.core.client.rpcservices.UserService;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.StateContentDTO;

import com.google.inject.Inject;

public abstract class ContentServiceIntegrationTest extends IntegrationTest {
    @Inject
    protected ContentService contentService;

    @Inject
    protected UserService userService;

    protected StateContentDTO getSiteDefaultContent() throws Exception {
        final StateToken stateToken = new StateToken(getDefSiteGroupName());
        final StateContentDTO content = (StateContentDTO) contentService.getContent(session.getHash(), stateToken);
        return content;
    }

}
