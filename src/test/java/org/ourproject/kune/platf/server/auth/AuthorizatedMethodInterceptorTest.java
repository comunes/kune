package org.ourproject.kune.platf.server.auth;

import java.lang.reflect.AccessibleObject;

import org.aopalliance.intercept.MethodInvocation;
import org.junit.Before;
import org.mockito.Mockito;
import org.ourproject.kune.platf.integration.IntegrationTest;
import org.ourproject.kune.platf.integration.IntegrationTestHelper;

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
	// AccessType accessType = authoAnnotation.accessTypeRequired();
	// boolean checkContent = authoAnnotation.checkContent();
    }

}
