package cc.kune.core.shared.dto;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class HomeStatsDTO implements IsSerializable {

  private List<ContentSimpleDTO> lastContentsOfMyGroups;
  private List<GroupDTO> lastGroups;
  private List<ContentSimpleDTO> lastPublishedContents;
  private Long totalGroups;
  private Long totalUsers;

  public HomeStatsDTO() {
  }

  public List<ContentSimpleDTO> getLastContentsOfMyGroups() {
    return lastContentsOfMyGroups;
  }

  public List<GroupDTO> getLastGroups() {
    return lastGroups;
  }

  public List<ContentSimpleDTO> getLastPublishedContents() {
    return lastPublishedContents;
  }

  public Long getTotalGroups() {
    return totalGroups;
  }

  public Long getTotalUsers() {
    return totalUsers;
  }

  public void setLastContentsOfMyGroups(final List<ContentSimpleDTO> lastContentsOfMyGroups) {
    this.lastContentsOfMyGroups = lastContentsOfMyGroups;
  }

  public void setLastGroups(final List<GroupDTO> lastGroups) {
    this.lastGroups = lastGroups;
  }

  public void setLastPublishedContents(final List<ContentSimpleDTO> lastPublishedContents) {
    this.lastPublishedContents = lastPublishedContents;
  }

  public void setTotalGroups(final Long totalGroups) {
    this.totalGroups = totalGroups;
  }

  public void setTotalUsers(final Long totalUsers) {
    this.totalUsers = totalUsers;
  }

}
