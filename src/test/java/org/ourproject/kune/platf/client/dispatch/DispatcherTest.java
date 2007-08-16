package org.ourproject.kune.platf.client.dispatch;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

public class DispatcherTest {
    private Dispatcher dispatcher;

    @Before
    public void create() {
	dispatcher = new DefaultDispatcher();
    }

    @Test
    public void testSubscribe() {
	Object value = new Object();
	String eventName = "eventName";
	Action action1 = dispatcher.subscribe(EasyMock.createNiceMock(Action.class));
	Action action2 = dispatcher.subscribe(EasyMock.createNiceMock(Action.class));
	Action action3 = dispatcher.subscribe(EasyMock.createNiceMock(Action.class));

	action1.execute(value, null);
	action2.execute(value, null);
	action3.execute(value, null);
	EasyMock.replay(action1, action2, action3);

	dispatcher.fire(eventName, value);
	EasyMock.verify(action1, action2, action3);
    }

}
