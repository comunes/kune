package org.ourproject.kune.platf.client.dispatch;

import static org.junit.Assert.*;

import org.junit.Test;

public class HistoryTokenTest {

    @Test
    public void testNoParams() {
	HistoryToken token = new HistoryToken("eventName");
	assertEquals("eventName", token.eventName);
	assertEquals(null, token.value);
    }

    @Test
    public void testValue() {
	HistoryToken token = new HistoryToken("event.some.values.here");
	assertEquals("event", token.eventName);
	assertEquals("some.values.here", token.value);
    }

    @Test
    public void testParameters() {
	HistoryToken token = new HistoryToken("event.0.1.2.3.4");
	for (int index = 0; index < 5; index++) {
	    assertEquals("" + index, token.getParam(index));
	}
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testNotValidParamIndex() {
	HistoryToken token = new HistoryToken("event.0.1.2.3.4");
	token.getParam(5);
    }

    @Test
    public void testReencode() {
	HistoryToken token = new HistoryToken("event.0.1.2.3.4");
	assertEquals("0.1.2.3.4", token.reencodeFrom(0));
	assertEquals("1.2.3.4", token.reencodeFrom(1));
	assertEquals("4", token.reencodeFrom(4));
    }

}
