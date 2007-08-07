package org.ourproject.kune.platf.client.dispatch;

import static org.junit.Assert.*;

import org.junit.Test;

public class HistoryTokenTest {

    @Test
    public void testNoParams() {
	HistoryTokenOld token = new HistoryTokenOld("eventName");
	assertEquals("eventName", token.eventName);
	assertEquals(null, token.value);
    }

    @Test
    public void testValue() {
	HistoryTokenOld token = new HistoryTokenOld("event.some.values.here");
	assertEquals("event", token.eventName);
	assertEquals("some.values.here", token.value);
    }

    @Test
    public void testParameters() {
	HistoryTokenOld token = new HistoryTokenOld("event.0.1.2.3.4");
	for (int index = 0; index < 5; index++) {
	    assertEquals("" + index, token.getParam(index));
	}
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testNotValidParamIndex() {
	HistoryTokenOld token = new HistoryTokenOld("event.0.1.2.3.4");
	token.getParam(5);
    }

    @Test
    public void testReencode() {
	HistoryTokenOld token = new HistoryTokenOld("event.0.1.2.3.4");
	assertEquals("0.1.2.3.4", token.reencodeFrom(0));
	assertEquals("1.2.3.4", token.reencodeFrom(1));
	assertEquals("4", token.reencodeFrom(4));
    }

}
