package org.ourproject.kune.platf.integration.content;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.client.dto.StateContentDTO;
import org.ourproject.kune.platf.integration.IntegrationTestHelper;
import org.ourproject.kune.platf.server.TestDomainHelper;

public class ContentServiceSaveTest extends ContentServiceIntegrationTest {

    private StateContentDTO defaultContent;

    @Before
    public void init() throws Exception {
        new IntegrationTestHelper(this);
        defaultContent = getSiteDefaultContent();
        doLogin();
    }

    @Test
    public void testSaveAndRetrieve() throws Exception {
        final String text = "Lorem ipsum dolor sit amet";
        final int version = defaultContent.getVersion();
        contentService.save(getHash(), defaultContent.getStateToken(), text);
        final StateContentDTO again = (StateContentDTO) contentService.getContent(getHash(),
                defaultContent.getStateToken());
        assertEquals(text, again.getContent());
        assertEquals(version + 1, again.getVersion());
        assertEquals(0, again.getRateByUsers().intValue());
        assertEquals(new Double(0), again.getRate());
    }

    @Test
    public void testSaveAndRetrieveBig() throws Exception {
        final String text = TestDomainHelper.createBigText();
        final int version = defaultContent.getVersion();
        contentService.save(getHash(), defaultContent.getStateToken(), text);
        final StateContentDTO again = (StateContentDTO) contentService.getContent(getHash(),
                defaultContent.getStateToken());
        assertEquals(version + 1, again.getVersion());
        assertEquals(text, again.getContent());
    }

}
