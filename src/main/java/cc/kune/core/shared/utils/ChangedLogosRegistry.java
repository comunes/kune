package cc.kune.core.shared.utils;

/**
 * This class is used to register the users/groups that recently have changed
 * theirs logos, so we have to take into account when generating image urls to
 * prevent caching
 *
 * @author vjrj
 *
 */
public interface ChangedLogosRegistry {

  void add(String shortname);

  boolean isRecentlyChanged(String shortname);

}
