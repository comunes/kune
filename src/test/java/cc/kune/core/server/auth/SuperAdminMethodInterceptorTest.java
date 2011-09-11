package cc.kune.core.server.auth;

import java.lang.reflect.AccessibleObject;

import org.aopalliance.intercept.MethodInvocation;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import cc.kune.core.client.errors.AccessViolationException;
import cc.kune.core.server.integration.IntegrationTest;
import cc.kune.core.server.integration.IntegrationTestHelper;
import cc.kune.core.shared.domain.AccessRol;

import com.google.inject.persist.Transactional;

public class SuperAdminMethodInterceptorTest extends IntegrationTest {

  private MethodInvocation invocation;
  private SuperAdmin superAdminAnnotation;
  private SuperAdminMethodInterceptor superAdminInterceptor;

  @Transactional
  @Before
  public void before() {
    superAdminInterceptor = new SuperAdminMethodInterceptor();
    new IntegrationTestHelper(this, superAdminInterceptor);
    invocation = Mockito.mock(MethodInvocation.class);
    final AccessibleObject accessibleObject = Mockito.mock(AccessibleObject.class);
    Mockito.when(invocation.getMethod()).thenReturn(this.getClass().getMethods()[0]);
    Mockito.when(invocation.getStaticPart()).thenReturn(accessibleObject);
    superAdminAnnotation = Mockito.mock(SuperAdmin.class);
    Mockito.when(accessibleObject.getAnnotation(SuperAdmin.class)).thenReturn(superAdminAnnotation);
  }

  private void invoke() throws Throwable {
    Mockito.when(superAdminAnnotation.rol()).thenReturn(AccessRol.Administrator);
    final Object[] arguments = { getHash() };
    Mockito.when(invocation.getArguments()).thenReturn(arguments);
    superAdminInterceptor.invoke(invocation);
  }

  @Test(expected = AccessViolationException.class)
  public void notMemberThrowsExcep() throws Throwable {
    doLoginWithDummyUser();
    invoke();
  }

  @Test
  public void superAdminDoTheJob() throws Throwable {
    doLogin();
    invoke();
  }
}
