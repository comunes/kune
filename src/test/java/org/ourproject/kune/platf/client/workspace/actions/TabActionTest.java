package org.ourproject.kune.platf.client.workspace.actions;

import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.client.Tool;

public class TabActionTest {
    private ActionTestHelper helper;

    @Before
    public void createAction() {
	helper = new ActionTestHelper(new TabAction(), ActionTestHelper.NICE);
    }

    @Test
    public void testAction() {
	Tool tool = createStrictMock(Tool.class);

	expect(tool.getEncodedState()).andReturn("");

	expect(helper.app.getTool("doc")).andReturn(tool);
	replay(tool, helper.app);

	helper.execute("doc");
	verify(tool);
    }
}
