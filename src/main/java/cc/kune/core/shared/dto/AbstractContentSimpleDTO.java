package cc.kune.core.shared.dto;

import cc.kune.core.shared.domain.utils.StateToken;

import com.google.gwt.user.client.rpc.IsSerializable;

public abstract class AbstractContentSimpleDTO implements IsSerializable {

  protected Long id;
  private StateToken stateToken;
  private String typeId;

  public Long getId() {
    return id;
  }

  public abstract String getName();

  public StateToken getStateToken() {
    return stateToken;
  }

  public String getTypeId() {
    return typeId;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public abstract void setName(final String name);

  public void setStateToken(final StateToken stateToken) {
    this.stateToken = stateToken;
  }

  public void setTypeId(final String typeId) {
    this.typeId = typeId;
  }

}
