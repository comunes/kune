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
        Action[] actions = new Action[3];
        for (int index = 0; index < actions.length; index++) {
            Action action = EasyMock.createNiceMock(Action.class);
            action.execute(value, null);
            EasyMock.expectLastCall();
            actions[index] = action;
        }
        for (int index = 0; index < actions.length; index++) {
            EasyMock.replay(actions[index]);
        }
        for (int index = 0; index < actions.length; index++) {
            dispatcher.subscribe(eventName, actions[index]);
        }

        dispatcher.fire(eventName, value, null);

        for (int index = 0; index < actions.length; index++) {
            EasyMock.verify(actions[index]);
        }
    }

}
