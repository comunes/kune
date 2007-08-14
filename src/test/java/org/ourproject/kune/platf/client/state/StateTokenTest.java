package org.ourproject.kune.platf.client.state;

import static org.junit.Assert.assertNull;

import org.junit.Test;

public class StateTokenTest {

    @Test
    public void checkAllEmpty() {
	StateToken token = new StateToken("");
	assertNull(token.group);
	assertNull(token.tool);
	assertNull(token.folder);
	assertNull(token.document);
    }
}
