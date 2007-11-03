package org.ourproject.kune.platf.integration.content;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.integration.IntegrationTestHelper;
import org.ourproject.kune.workspace.client.dto.StateDTO;

import com.google.gwt.user.client.rpc.SerializableException;

public class ContentServiceRateTest extends ContentServiceIntegrationTest {

    @Before
    public void init() throws SerializableException {
        new IntegrationTestHelper(this);
        doLogin();
    }

    @Test
    public void contentRateAndRetrieve() throws SerializableException {
        final StateDTO defaultContent = getDefaultContent();
        contentService.rateContent(getHash(), defaultContent.getState().getDocument(), 4.5);
        final StateDTO again = contentService.getContent(getHash(), defaultContent.getState());
        assertEquals(true, again.isRateable());
        assertEquals(4.5, again.getCurrentUserRate());
        assertEquals(4.5, again.getRate());
        assertEquals(1, again.getRateByUsers());
    }

}
