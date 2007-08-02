package org.ourproject.kune.docs.client;

import static org.ourproject.kune.docs.client.DocumentViewFactory.documentView;
import static org.ourproject.kune.docs.client.DocumentViewFactory.navigationView;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.app.client.MockServer;
import org.ourproject.kune.platf.client.workspace.navigation.NavigationView;
public class DocumentToolTest {

    private DocumentTool tool;

    @Before
    public void create() {
	MockServer.start(MockServer.TEST);
	documentView = EasyMock.createMock(DocumentView.class);
	navigationView = EasyMock.createNiceMock(NavigationView.class);
	tool = new DocumentTool();
    }

    @Test
    public void testEncode() {
	navigationView.clear();
//	EasyMock.expectLastCall().times(3);
	EasyMock.replay(documentView, navigationView);
	tool.setEncodedState("ctx.doc");
	EasyMock.verify(documentView, navigationView);

    }
}
