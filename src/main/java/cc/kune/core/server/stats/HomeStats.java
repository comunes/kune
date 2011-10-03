package cc.kune.core.server.stats;

import java.util.List;

import cc.kune.domain.Content;
import cc.kune.domain.Group;

public class HomeStats {

  private List<Content> lastContentsOfMyGroups;
  private List<Group> lastGroups;
  private List<Content> lastPublishedContents;
  private Long totalGroups;
  private Long totalUsers;

  public HomeStats() {
  }

  public List<Content> getLastContentsOfMyGroups() {
    return lastContentsOfMyGroups;
  }

  public List<Group> getLastGroups() {
    return lastGroups;
  }

  public List<Content> getLastPublishedContents() {
    return lastPublishedContents;
  }

  public Long getTotalGroups() {
    return totalGroups;
  }

  public Long getTotalUsers() {
    return totalUsers;
  }

  public void setLastContentsOfMyGroups(final List<Content> lastContentsOfMyGroups) {
    this.lastContentsOfMyGroups = lastContentsOfMyGroups;
  }

  public void setLastGroups(final List<Group> lastGroups) {
    this.lastGroups = lastGroups;
  }

  public void setLastPublishedContents(final List<Content> lastPublishedContents) {
    this.lastPublishedContents = lastPublishedContents;
  }

  public void setTotalGroups(final Long totalGroups) {
    this.totalGroups = totalGroups;
  }

  public void setTotalUsers(final Long totalUsers) {
    this.totalUsers = totalUsers;
  }

}
