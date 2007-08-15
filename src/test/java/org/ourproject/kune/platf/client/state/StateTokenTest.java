package org.ourproject.kune.platf.client.state;

import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.ourproject.kune.platf.client.dto.StateToken;

public class StateTokenTest {

    @Test
    public void checkAllEmpty() {
	StateToken token = new StateToken("");
	assertNull(token.getGroup());
	assertNull(token.getTool());
	assertNull(token.getFolder());
	assertNull(token.getDocument());
    }
}
