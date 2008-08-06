package org.ourproject.kune.platf.server.auth;

import java.lang.reflect.AccessibleObject;

import org.aopalliance.intercept.MethodInvocation;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.ourproject.kune.platf.client.errors.SessionExpiredException;
import org.ourproject.kune.platf.client.errors.UserMustBeLoggedException;
import org.ourproject.kune.platf.integration.IntegrationTest;
import org.ourproject.kune.platf.integration.IntegrationTestHelper;

public class AuthenticatedMethodInterceptorTest extends IntegrationTest {

    private AuthenticatedMethodInterceptor auth;
    private MethodInvocation invocation;
    private Authenticated authAnnotation;

    @Before
    public void before() {
	auth = new AuthenticatedMethodInterceptor();
	new IntegrationTestHelper(auth, this);
	invocation = Mockito.mock(MethodInvocation.class);
	final AccessibleObject accessibleObject = Mockito.mock(AccessibleObject.class);
	Mockito.stub(invocation.getMethod()).toReturn(this.getClass().getMethods()[0]);
	Mockito.stub(invocation.getStaticPart()).toReturn(accessibleObject);
	authAnnotation = Mockito.mock(Authenticated.class);
	Mockito.stub(accessibleObject.getAnnotation(Authenticated.class)).toReturn(authAnnotation);
    }

    @Test(expected = UserMustBeLoggedException.class)
    public void hashNullAndMandatoryMustDoNothing() throws Throwable {
	Mockito.stub(authAnnotation.mandatory()).toReturn(true);
	final Object[] result = { null };
	Mockito.stub(invocation.getArguments()).toReturn(result);
	auth.invoke(invocation);
    }

    @Test
    public void hashNullAndNotMandatoryMustDoNothing() throws Throwable {
	Mockito.stub(authAnnotation.mandatory()).toReturn(false);
	final Object[] result = { null };
	Mockito.stub(invocation.getArguments()).toReturn(result);
	auth.invoke(invocation);
    }

    @Test
    public void hashNullAsStringAndNotMandatoryMustDoNothing() throws Throwable {
	Mockito.stub(authAnnotation.mandatory()).toReturn(false);
	final Object[] result = { "null" };
	Mockito.stub(invocation.getArguments()).toReturn(result);
	auth.invoke(invocation);
    }

    @Test(expected = SessionExpiredException.class)
    public void otherHashAndMandatoryAndLoggedMustSessionExp() throws Throwable {
	doLogin();
	Mockito.stub(authAnnotation.mandatory()).toReturn(true);
	final Object[] result = { "other-hash" };
	Mockito.stub(invocation.getArguments()).toReturn(result);
	auth.invoke(invocation);
    }

    @Test
    public void sameHashAndMandatoryAndLoggedMustSessionExp() throws Throwable {
	doLogin();
	Mockito.stub(authAnnotation.mandatory()).toReturn(true);
	final Object[] result = { getHash() };
	Mockito.stub(invocation.getArguments()).toReturn(result);
	auth.invoke(invocation);
    }

    @Test(expected = SessionExpiredException.class)
    public void someHashAndMandatoryAndNotLoggedMustSessionExp() throws Throwable {
	Mockito.stub(authAnnotation.mandatory()).toReturn(true);
	final Object[] result = { "some-hash" };
	Mockito.stub(invocation.getArguments()).toReturn(result);
	auth.invoke(invocation);
    }
}
