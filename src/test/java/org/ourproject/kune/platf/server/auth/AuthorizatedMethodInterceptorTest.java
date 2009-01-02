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
        Mockito.when(invocation.getMethod()).thenReturn(this.getClass().getMethods()[0]);
        Mockito.when(invocation.getStaticPart()).thenReturn(accessibleObject);
        authAnnotation = Mockito.mock(Authorizated.class);
        Mockito.when(accessibleObject.getAnnotation(Authorizated.class)).thenReturn(authAnnotation);

    }

    @Test(expected = ContentNotFoundException.class)
    public void tesHashNullContainer() throws Throwable {
        // Mockito.when(authAnnotation.accessRolRequired()).thenReturn(AccessRol.Administrator);
        Mockito.when(authAnnotation.actionLevel()).thenReturn(ActionLevel.content);
        final Object[] arguments = { getHash(), new StateToken("group.docs") };
        Mockito.when(invocation.getArguments()).thenReturn(arguments);
        auth.invoke(invocation);
    }

    @Test(expected = ContentNotFoundException.class)
    public void testHashNullContent() throws Throwable {
        Mockito.when(authAnnotation.actionLevel()).thenReturn(ActionLevel.content);
        final Object[] arguments = { getHash(), new StateToken("group.docs.1") };
        Mockito.when(invocation.getArguments()).thenReturn(arguments);
        auth.invoke(invocation);
    }

    @Test(expected = AccessViolationException.class)
    public void wrongGroupNameThrowsExcep() throws Throwable {
        doLogin();
        Mockito.when(authAnnotation.accessRolRequired()).thenReturn(AccessRol.Administrator);
        Mockito.when(authAnnotation.actionLevel()).thenReturn(ActionLevel.content);
        final Object[] arguments = { getHash(), new StateToken("groupWRONG.docs.1.1") };
        Mockito.when(invocation.getArguments()).thenReturn(arguments);
        auth.invoke(invocation);
    }

}
