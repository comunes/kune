package org.ourproject.kune.docs.client;

import static org.junit.Assert.assertEquals;
import static org.ourproject.kune.docs.client.DocumentViewFactory.documentView;
import static org.ourproject.kune.docs.client.DocumentViewFactory.navigationView;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.docs.client.rpc.DocumentService;
import org.ourproject.kune.docs.client.rpc.DocumentServiceAsync;
import org.ourproject.kune.platf.client.stubs.NiceState;
import org.ourproject.kune.platf.client.workspace.dto.ContentDataDTO;
import org.ourproject.kune.platf.client.workspace.dto.ContextDataDTO;
import org.ourproject.kune.platf.client.workspace.dto.ContextItemDTO;
import org.ourproject.kune.platf.client.workspace.navigation.NavigationView;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class DocumentToolTest {
    private DocumentTool tool;
    private int getContentCallCount;
    private int getContextCallCount;

    @Before public void create() {
        getContentCallCount = 0;
        getContextCallCount = 0;

        DocumentService.App.setMock(new DocumentServiceAsync() {
            public void getContent(String userHash, String ctxRef, String docRef, AsyncCallback callback) {
                getContentCallCount++;
                callback.onSuccess(new ContentDataDTO(docRef, "title", "content"));
            }

            public void getContext(String userHash, String contextRef, AsyncCallback async) {
                getContextCallCount++;
                ContextDataDTO ctx = new ContextDataDTO(contextRef);
                ctx.add(new ContextItemDTO("name", "type", "child"));
                async.onSuccess(ctx);
            }
        });
        documentView = EasyMock.createStrictMock(DocumentView.class);
        navigationView = EasyMock.createStrictMock(NavigationView.class);
        tool = new DocumentTool();
        tool.setState(new NiceState());
    }

    @Test public void testCalls() {
        tool.setEncodedState("ctx.doc");
        assertEquals(1, getContentCallCount);
        assertEquals(1, getContextCallCount);
    }

    @Test public void testEncode() {
        navigationView.clear();
        navigationView.add("name", "type", "group.groupShortName.docs.ctxReference.child");
        navigationView.selectItem(0);

        documentView.setContentName("title");
        documentView.setContent("content");

        EasyMock.replay(documentView, navigationView);
        tool.setEncodedState("ctxReference.root");
        EasyMock.verify(documentView, navigationView);

    }
}
