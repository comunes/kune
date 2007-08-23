package org.ourproject.kune.platf.integration.content;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.integration.IntegrationTestHelper;
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
	// Dani: este test falla por culpa del char[] en Data.java, creo.
	// solo he metido un tamanyo de texto mas grande; creo que hay que usar Lob en Data.java
	// @Lob
	// http://openjpa.apache.org/docs/latest/manual/manual.html#jpa_overview_mapping_lob
	String text = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Curabitur quis metus. Etiam sit amet sapien a pede pharetra cursus. Cras blandit, erat a adipiscing semper, mi dolor dictum urna, quis tempus massa urna id ipsum. Integer fermentum ultrices diam. Suspendisse elit leo, fermentum facilisis, semper et, ultricies nec, nisl. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Pellentesque turpis felis, commodo a, lobortis ac, pulvinar id, ante. Phasellus tempor. Ut quis ipsum. Vestibulum ornare pharetra nisl. Vivamus iaculis purus et mi. Vestibulum turpis. Lorem ipsum dolor sit amet, consectetuer adipiscing elit. In feugiat, mi id mattis commodo, ligula augue fringilla elit, id egestas augue erat eget libero. Integer faucibus.<br>Donec a justo quis elit ultricies hendrerit. Ut ultrices. Pellentesque mattis massa non urna. In hac habitasse platea dictumst. Pellentesque convallis nisi pretium tortor. Vivamus nunc nunc, tempor et, mollis vitae, dapibus dapibus, magna. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Suspendisse porttitor. Sed condimentum. Donec ornare lobortis purus. Pellentesque ipsum magna, vehicula et, iaculis id, blandit eu, justo. Donec convallis gravida mauris. In hac habitasse platea dictumst. Duis interdum adipiscing est. Duis diam. Praesent dictum cursus mauris. Cras vitae nisl. Aliquam ultricies metus non risus. Etiam suscipit, lacus sodales sollicitudin pulvinar, nisi tortor mollis tellus, vitae ullamcorper felis erat in risus";
	StateDTO defaultContent = getDefaultContent();
	int version = defaultContent.getVersion();
	int currentVersion = contentService.save(getHash(), defaultContent.getDocumentId(), text);
	assertEquals(version + 1, currentVersion);
	StateDTO again = contentService.getContent(getHash(), defaultContent.getState());
	assertEquals(text, again.getContent());
    }
}
