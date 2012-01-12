package cc.kune.core.server.notifier;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class SimpleDestinationProviderTest extends AbstractPendingNotificationTest {

  @Test
  public void shouldCompareWell() {
    assertTrue(someUserProvider.equals(someSimilarUserProvider));
    assertTrue(someUserProvider.equals(someSimilarUserProvider2));
    assertTrue(someSimilarUserProvider.equals(someSimilarUserProvider2));
    assertFalse(someUserProvider.equals(diferentProvider));
  }

}
