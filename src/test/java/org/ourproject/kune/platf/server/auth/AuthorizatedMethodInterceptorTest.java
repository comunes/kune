package org.ourproject.kune.platf.server.auth;

import java.lang.reflect.AccessibleObject;

import org.aopalliance.intercept.MethodInvocation;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.errors.AccessViolationException;
import org.ourproject.kune.platf.client.errors.ContentNotFoundException;
import org.ourproject.kune.platf.integration.IntegrationTest;
import org.ourproject.kune.platf.integration.IntegrationTestHelper;
import org.ourproject.kune.platf.server.access.AccessRol;

public class AuthorizatedMethodInterceptorTest extends IntegrationTest {

    private AuthorizatedMethodInterceptor auth;
    private MethodInvocation invocation;
    private Authorizated authAnnotation;

    @Before
    public void before() {
	auth = new AuthorizatedMethodInterceptor();
	new IntegrationTestHelper(auth, this);
	invocation = Mockito.mock(MethodInvocation.class);
	final AccessibleObject accessibleObject = Mockito.mock(AccessibleObject.class);
	Mockito.stub(invocation.getMethod()).toReturn(this.getClass().getMethods()[0]);
	Mockito.stub(invocation.getStaticPart()).toReturn(accessibleObject);
	authAnnotation = Mockito.mock(Authorizated.class);
	Mockito.stub(accessibleObject.getAnnotation(Authorizated.class)).toReturn(authAnnotation);

    }

    @Test(expected = ContentNotFoundException.class)
    public void tesHashNullContainer() throws Throwable {
	// Mockito.stub(authAnnotation.accessRolRequired()).toReturn(AccessRol.Administrator);
	Mockito.stub(authAnnotation.actionLevel()).toReturn(ActionLevel.content);
	final Object[] arguments = { getHash(), new StateToken("group.docs") };
	Mockito.stub(invocation.getArguments()).toReturn(arguments);
	auth.invoke(invocation);
    }

    @Test(expected = ContentNotFoundException.class)
    public void testHashNullContent() throws Throwable {
	Mockito.stub(authAnnotation.actionLevel()).toReturn(ActionLevel.content);
	final Object[] arguments = { getHash(), new StateToken("group.docs.1") };
	Mockito.stub(invocation.getArguments()).toReturn(arguments);
	auth.invoke(invocation);
    }

    @Test(expected = AccessViolationException.class)
    public void wrongGroupNameThrowsExcep() throws Throwable {
	doLogin();
	Mockito.stub(authAnnotation.accessRolRequired()).toReturn(AccessRol.Administrator);
	Mockito.stub(authAnnotation.actionLevel()).toReturn(ActionLevel.content);
	final Object[] arguments = { getHash(), new StateToken("groupWRONG.docs.1.1") };
	Mockito.stub(invocation.getArguments()).toReturn(arguments);
	auth.invoke(invocation);
    }

}
