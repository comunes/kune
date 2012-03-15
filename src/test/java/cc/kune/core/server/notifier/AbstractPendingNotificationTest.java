package cc.kune.core.server.notifier;

import org.junit.Before;

import cc.kune.domain.User;

public abstract class AbstractPendingNotificationTest {

  protected SimpleDestinationProvider diferentProvider;
  protected User otherDiferentUser;
  protected User sameUser;
  protected SimpleDestinationProvider someSimilarUserProvider;
  protected SimpleDestinationProvider someSimilarUserProvider2;
  protected SimpleDestinationProvider someUserProvider;
  protected User user;

  @Before
  public void before() {
    user = new User("test1", "test1 name", "falseEmail@", "somediggest".getBytes(),
        "some salt".getBytes(), null, null, null);
    sameUser = new User("test1", "test1 name", "falseEmail@", "somediggest".getBytes(),
        "some salt".getBytes(), null, null, null);
    otherDiferentUser = new User("test2", "test2 name", "falseEmail@", "somediggest".getBytes(),
        "some salt".getBytes(), null, null, null);
    someUserProvider = new SimpleDestinationProvider(user);
    someSimilarUserProvider = new SimpleDestinationProvider(user);
    someSimilarUserProvider2 = new SimpleDestinationProvider(sameUser);
    diferentProvider = new SimpleDestinationProvider(otherDiferentUser);
  }

}
