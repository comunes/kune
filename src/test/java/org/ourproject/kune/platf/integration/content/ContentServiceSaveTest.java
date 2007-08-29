package org.ourproject.kune.platf.integration.content;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.integration.IntegrationTestHelper;
import org.ourproject.kune.platf.server.TestDomainHelper;
import org.ourproject.kune.workspace.client.dto.StateDTO;

import com.google.gwt.user.client.rpc.SerializableException;

public class ContentServiceSaveTest extends ContentServiceIntegrationTest {

    @Before
    public void init() throws SerializableException {
	new IntegrationTestHelper(this);
	doLogin();
    }

    @Test
    public void testSaveAndRetrieve() throws SerializableException {
	String text = "Lorem ipsum dolor sit amet";
	StateDTO defaultContent = getDefaultContent();
	int version = defaultContent.getVersion();
	int currentVersion = contentService.save(getHash(), defaultContent.getDocumentId(), text);
	assertEquals(version + 1, currentVersion);
	StateDTO again = contentService.getContent(getHash(), defaultContent.getState());
	assertEquals(text, again.getContent());
    }

    @Test
    public void testSaveAndRetrieveBig() throws SerializableException {
	String text = TestDomainHelper.createBigText();
	StateDTO defaultContent = getDefaultContent();
	int version = defaultContent.getVersion();
	int currentVersion = contentService.save(getHash(), defaultContent.getDocumentId(), text);
	assertEquals(version + 1, currentVersion);
	StateDTO again = contentService.getContent(getHash(), defaultContent.getState());
	assertEquals(text, again.getContent());
    }

}
