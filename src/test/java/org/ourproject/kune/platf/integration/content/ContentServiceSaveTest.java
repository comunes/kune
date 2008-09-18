package org.ourproject.kune.platf.integration.content;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.integration.IntegrationTestHelper;
import org.ourproject.kune.platf.server.TestDomainHelper;

public class ContentServiceSaveTest extends ContentServiceIntegrationTest {

    private StateDTO defaultContent;

    @Before
    public void init() throws Exception {
	new IntegrationTestHelper(this);

	defaultContent = getDefaultContent();
	doLogin();
    }

    @Test
    public void testSaveAndRetrieve() throws Exception {
	final String text = "Lorem ipsum dolor sit amet";
	final int version = defaultContent.getVersion();
	final int currentVersion = contentService.save(getHash(), defaultContent.getStateToken(), text);
	assertEquals(version + 2, currentVersion);
	final StateDTO again = contentService.getContent(getHash(), defaultContent.getStateToken());
	assertEquals(text, again.getContent());
	assertEquals(0, (int) again.getRateByUsers());
	assertEquals(new Double(0), again.getRate());
    }

    @Test
    public void testSaveAndRetrieveBig() throws Exception {
	final String text = TestDomainHelper.createBigText();
	;
	final int version = defaultContent.getVersion();
	final int currentVersion = contentService.save(getHash(), defaultContent.getStateToken(), text);
	assertEquals(version + 2, currentVersion);
	final StateDTO again = contentService.getContent(getHash(), defaultContent.getStateToken());
	assertEquals(text, again.getContent());
    }

}
