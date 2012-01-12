package cc.kune.core.server.notifier;

import java.util.Collection;

import cc.kune.domain.User;

/**
 * The Interface DestinationProvider is used to provide a way to get a list of
 * Users (for instance to send notifications to them)
 */
public interface DestinationProvider {

  /**
   * Gets the destination list
   * 
   * @return the destination
   */
  Collection<User> getDest();
}
