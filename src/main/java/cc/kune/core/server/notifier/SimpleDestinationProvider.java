package cc.kune.core.server.notifier;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import cc.kune.domain.User;

public class SimpleDestinationProvider implements DestinationProvider {
  private final List<User> list;

  /**
   * Instantiates a new simple destination provider (used for single
   * notifications like add/remove participant emails).
   * 
   * @param user
   *          the user to send the email or other type of notification
   */
  public SimpleDestinationProvider(final User user) {
    list = Arrays.asList(user);
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final SimpleDestinationProvider other = (SimpleDestinationProvider) obj;
    if (list == null) {
      if (other.list != null) {
        return false;
      }
    } else if (!list.equals(other.list)) {
      return false;
    }
    return true;
  }

  @Override
  public Collection<User> getDest() {
    return list;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((list == null) ? 0 : list.hashCode());
    return result;
  }

}
