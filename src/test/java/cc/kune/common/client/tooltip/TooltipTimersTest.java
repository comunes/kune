package cc.kune.common.client.tooltip;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import cc.kune.common.client.utils.TimerWrapper;

public class TooltipTimersTest {
    private TimerWrapper hideTimer;
    protected boolean hideTimerScheduled = false;
    private TimerWrapper securityTimer;
    private TimerWrapper showTimer;
    protected boolean showTimerScheduled = false;
    private TooltipTimers timers;

    @Before
    public void before() {
        showTimer = Mockito.mock(TimerWrapper.class);
        hideTimer = Mockito.mock(TimerWrapper.class);
        securityTimer = Mockito.mock(TimerWrapper.class);
        timers = new TooltipTimers(showTimer, hideTimer, securityTimer);
        Mockito.doAnswer(new Answer<Object>() {
            @Override
            public Object answer(final InvocationOnMock invocation) throws Throwable {
                showTimerScheduled = true;
                return null;
            }
        }).when(showTimer).schedule(Mockito.anyInt());
        Mockito.doAnswer(new Answer<Object>() {

            @Override
            public Object answer(final InvocationOnMock invocation) throws Throwable {
                return showTimerScheduled;
            }
        }).when(showTimer).isScheduled();
        Mockito.doAnswer(new Answer<Object>() {

            @Override
            public Object answer(final InvocationOnMock invocation) throws Throwable {
                showTimerScheduled = false;
                return null;
            }
        }).when(showTimer).cancel();
        Mockito.doAnswer(new Answer<Object>() {
            @Override
            public Object answer(final InvocationOnMock invocation) throws Throwable {
                hideTimerScheduled = true;
                return null;
            }
        }).when(hideTimer).schedule(Mockito.anyInt());
        Mockito.doAnswer(new Answer<Object>() {

            @Override
            public Object answer(final InvocationOnMock invocation) throws Throwable {
                return hideTimerScheduled;
            }
        }).when(hideTimer).isScheduled();
        Mockito.doAnswer(new Answer<Object>() {

            @Override
            public Object answer(final InvocationOnMock invocation) throws Throwable {
                hideTimerScheduled = false;
                return null;
            }
        }).when(hideTimer).cancel();
    }

    @Test
    public void testSeveralOverAndOutsOnlyOneTimerEach() {
        timers.onOver();
        timers.onOver();
        timers.onOver();
        timers.onOut();
        timers.onOut();
        Mockito.verify(showTimer, Mockito.times(1)).schedule(Mockito.anyInt());
        Mockito.verify(showTimer, Mockito.times(1)).cancel();
        Mockito.verify(hideTimer, Mockito.times(1)).schedule(Mockito.anyInt());
    }

    @Test
    public void testSeveralOverOnlyOneTimer() {
        timers.onOver();
        timers.onOver();
        Mockito.verify(showTimer, Mockito.times(1)).schedule(Mockito.anyInt());
        Mockito.verify(hideTimer, Mockito.times(0)).cancel();
    }

    @Test
    public void testSeveralOverOutsAndOverOnlyOneTimerEach() {
        timers.onOver();
        timers.onOver();
        timers.onOver();
        timers.onOut();
        timers.onOut();
        timers.onOver();
        timers.onOver();
        timers.onOver();
        Mockito.verify(showTimer, Mockito.times(2)).schedule(Mockito.anyInt());
        Mockito.verify(showTimer, Mockito.times(1)).cancel();
        Mockito.verify(hideTimer, Mockito.times(1)).schedule(Mockito.anyInt());
        Mockito.verify(hideTimer, Mockito.times(1)).cancel();
    }
}
