package org.ourproject.kune.platf.client.dispatch;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

public class DispatcherTest {
	private Dispatcher dispatcher;

	@Before
	public void create() {
		Injector injector = EasyMock.createNiceMock(Injector.class);
		dispatcher = new DefaultDispatcher(injector);
	}

	@Test
	public void testSubscribe() {
		Object value = new Object();
		String eventName = "eventName";
		Action action1 = dispatcher.subscribe(eventName , EasyMock.createStrictMock(Action.class));
		Action action2 = dispatcher.subscribe(eventName , EasyMock.createStrictMock(Action.class));
		Action action3 = dispatcher.subscribe(eventName , EasyMock.createStrictMock(Action.class));

		action1.execute(value);
		action2.execute(value);
		action3.execute(value);
		EasyMock.replay(action1, action2, action3);

		dispatcher.fire(eventName, value);
		EasyMock.verify(action1, action2, action3);
	}

}
